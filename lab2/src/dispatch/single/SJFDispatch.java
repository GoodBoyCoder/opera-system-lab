package dispatch.single;

import constant.ProcessConstant;
import dispatch.Dispatch;
import dispatch.DispatchResult;
import job.Jcb;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 单道最短作业优先
 *
 * @author GoodBoy
 * @date 2021-05-26
 */
public class SJFDispatch implements Dispatch {
    @Override
    public DispatchResult run(List<Jcb> jcbList) {
        DispatchResult dispatchResult = new DispatchResult();

        if (jcbList.size() != 0) {
            int currentTime = jcbList.get(0).getSubmitTime();
            //先根据作业服务时间长短进行排序
            List<Jcb> sortedJcbList = jcbList.stream()
                    //稳定排序
                    .sorted(Comparator.comparing(Jcb::getNeedTime))
                    .collect(Collectors.toList());
            //复制源作业集合
            List<Jcb> jcbListForCopy = new LinkedList<>(jcbList);

            //最先到达的必定首先执行，下一个要执行的是已经到达的最短执行的作业
            List<Jcb> jobList = new LinkedList<>();
            Jcb jcb = jcbList.get(0);
            while (sortedJcbList.size() > 0) {
                jcb.setStatus(ProcessConstant.RUNNING);
                //移除当前正在执行的作业
                sortedJcbList.remove(jcb);
                jcbListForCopy.remove(jcb);

                //开始时间
                jcb.setStartTime(currentTime);
                //结束时间
                jcb.setFinishTime(currentTime += jcb.getNeedTime());
                jcb.setTurnaroundTime(jcb.getFinishTime() - jcb.getSubmitTime());
                jcb.setTurnaroundTimeWithRight((double) jcb.getTurnaroundTime() / jcb.getNeedTime());
                jobList.add(jcb);

                //作业完成
                jcb.setStatus(ProcessConstant.FINISHED);
                //找下一个作业
                boolean flag = false;
                for (Jcb sortedJcb : sortedJcbList) {
                    if (sortedJcb.getSubmitTime() <= currentTime) {
                        jcb = sortedJcb;
                        flag = true;
                        break;
                    }
                }
                //如果遍历完还是没有,则取最先到达的
                if (!flag && jcbListForCopy.size() > 0) {
                    jcb = jcbListForCopy.get(0);
                    currentTime = jcb.getSubmitTime();
                }
            }

            //根据到达时间排一下序
            jobList.sort(Comparator.comparing(Jcb::getSubmitTime));

            //计算平均周转时间
            double turnaroundTimeAvg = jobList.stream().mapToDouble(Jcb::getTurnaroundTime).average().orElse(0);
            //平均带权周转时间
            double turnaroundTimeWithRightAvg = jobList.stream().mapToDouble(Jcb::getTurnaroundTimeWithRight).average().orElse(0);

            dispatchResult.setJobList(jobList);
            dispatchResult.setAvgTime(turnaroundTimeAvg);
            dispatchResult.setAvgTimeWithRight(turnaroundTimeWithRightAvg);
        }

        return dispatchResult;
    }
}
