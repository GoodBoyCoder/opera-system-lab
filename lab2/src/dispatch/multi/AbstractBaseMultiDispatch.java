package dispatch.multi;

import constant.ProcessConstant;
import dispatch.Dispatch;
import dispatch.DispatchResult;
import job.Jcb;
import view.ProcessMenu;

import java.util.*;

/**
 * @author GoodBoy
 * @date 2021-05-27
 */
public abstract class AbstractBaseMultiDispatch implements Dispatch {
    /**
     * 系统资源数
     */
    private Map<String, Integer> resource;
    /**
     * 并行最大任务量
     */
    private Integer maxJob;

    public AbstractBaseMultiDispatch(Map<String, Integer> resource, Integer maxJob) {
        if (Objects.isNull(resource) || maxJob <= 0) {
            throw new IllegalArgumentException("参数错误");
        }
        this.resource = resource;
        this.maxJob = maxJob;
    }

    public Map<String, Integer> getResource() {
        return resource;
    }

    public void setResource(Map<String, Integer> resource) {
        this.resource = resource;
    }

    public Integer getMaxJob() {
        return maxJob;
    }

    public void setMaxJob(Integer maxJob) {
        this.maxJob = maxJob;
    }

    /**
     * 检查资源是否满足
     *
     * @param jcb jcb
     * @return 是否满足
     */
    protected boolean checkResource(Jcb jcb) {
        if (Objects.isNull(jcb)) {
            return false;
        }

        boolean result = true;
        Map<String, Integer> needResources = jcb.getNeedResources();
        Map<String, Integer> resource = getResource();
        for (String key : needResources.keySet()) {
            Integer total = resource.get(key);
            if (Objects.isNull(total) || total < needResources.get(key)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 资源归还
     *
     * @param jcb 作业控制块
     */
    protected void freeResource(Jcb jcb) {
        if (Objects.isNull(jcb)) {
            return;
        }

        Map<String, Integer> needResources = jcb.getNeedResources();
        Map<String, Integer> resource = getResource();
        for (Map.Entry<String, Integer> entry : needResources.entrySet()) {
            Integer total = resource.get(entry.getKey());
            if (!Objects.isNull(total)) {
                resource.put(entry.getKey(), total + entry.getValue());
            }
        }
    }

    /**
     * 资源就绪分配
     *
     * @param jcb 作业控制块
     */
    protected void allocation(Jcb jcb) {
        if (Objects.isNull(jcb)) {
            return;
        }

        Map<String, Integer> needResources = jcb.getNeedResources();
        Map<String, Integer> resource = getResource();
        for (Map.Entry<String, Integer> entry : needResources.entrySet()) {
            Integer total = resource.get(entry.getKey());
            if (!Objects.isNull(total)) {
                resource.put(entry.getKey(), total - entry.getValue());
            } else {
                //如果发生，应该有一个资源回滚
                throw new IllegalArgumentException("资源不存在");
            }
        }
    }

    /**
     * 获取下一个符合要求的作业
     *
     * @param jcbList     作业控制块列表
     * @param currentTime 当前时间
     * @return Jcb
     */
    public abstract Jcb getNextJcb(List<Jcb> jcbList, int currentTime);

    @Override
    public DispatchResult run(List<Jcb> jcbList) {
        int currentTime;

        List<Jcb> copyList = new LinkedList<>(jcbList);
        List<Jcb> jobList = new LinkedList<>();
        Queue<Jcb> jcbQueue = new LinkedList<>();

        if (jcbList.size() != 0) {
            currentTime = jcbList.get(0).getSubmitTime();
            //第一个来的必定执行
            Jcb firstJcb = copyList.get(0);
            if (checkResource(firstJcb)) {
                copyList.remove(0);
                allocation(firstJcb);
                firstJcb.setStatus(ProcessConstant.READY);
                jcbQueue.offer(firstJcb);
            }
            while (!copyList.isEmpty() || !jcbQueue.isEmpty()) {
                //调入内存队列
                boolean isBeak = false;
                while (getMaxJob() > jcbQueue.size() && !copyList.isEmpty()) {
                    //获取下一个满足的作业
                    Jcb nextJcb = getNextJcb(copyList, currentTime);
                    if (Objects.nonNull(nextJcb)) {
                        nextJcb.setStatus(ProcessConstant.READY);
                        //进行资源分配
                        allocation(nextJcb);
                        //进内存队列
                        jcbQueue.offer(nextJcb);
                    } else {
                        if (jcbQueue.isEmpty() && copyList.get(0).getSubmitTime() < currentTime) {
                            //没有资源能够满足并且没有资源可以释放
                            isBeak = true;
                        }
                        break;
                    }
                }
                if (isBeak) {
                    break;
                }

                //调度执行,每一个循环一个时间片
                if (!jcbQueue.isEmpty()) {
                    Jcb processing = jcbQueue.peek();
                    if (ProcessConstant.READY.equals(processing.getStatus())) {
                        //首次执行
                        processing.setStartTime(currentTime);
                    }
                    processing.setStatus(ProcessConstant.RUNNING);
                    if (processing.getNeedTime() <= currentTime - processing.getStartTime()) {
                        //该任务已经执行完成
                        processing.setFinishTime(currentTime);
                        processing.setStatus(ProcessConstant.FINISHED);
                        processing.setTurnaroundTime(currentTime - processing.getSubmitTime());
                        processing.setTurnaroundTimeWithRight((double) processing.getTurnaroundTime() / processing.getNeedTime());
                        //移出当前执行队列
                        jcbQueue.poll();
                        jobList.add(processing);
                        //资源释放
                        freeResource(processing);
                    }
                }
                System.out.println("-------------当前时间：" + currentTime + "-------------------------");
                //打印执行过程
                ProcessMenu.printJcbStatus(jcbList);
                //打印系统资源情况
                System.out.println("当起系统资源情况：" + getResource());
                currentTime++;
            }
        }

        //根据到达时间排一下序
        jobList.sort(Comparator.comparing(Jcb::getSubmitTime));

        //计算平均周转时间
        double turnaroundTimeAvg = jobList.stream().mapToDouble(Jcb::getTurnaroundTime).average().orElse(0);
        //平均带权周转时间
        double turnaroundTimeWithRightAvg = jobList.stream().mapToDouble(Jcb::getTurnaroundTimeWithRight).average().orElse(0);
        return new DispatchResult(jobList, turnaroundTimeAvg, turnaroundTimeWithRightAvg);
    }
}
