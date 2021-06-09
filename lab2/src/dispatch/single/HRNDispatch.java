package dispatch.single;

import dispatch.Dispatch;
import dispatch.DispatchResult;
import job.Jcb;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 单道响应比高者优先
 *
 * @author GoodBoy
 * @date 2021-05-27
 */
public class HRNDispatch implements Dispatch {
    @Override
    public DispatchResult run(List<Jcb> jcbList) {
        List<JcbWithPriority> jcbWithPriorityList = jcbList.stream()
                .sorted(Comparator.comparing(Jcb::getSubmitTime))
                .map(jcb -> new JcbWithPriority(jcb, 0))
                .collect(Collectors.toList());

        int currentTime = 0;
        if (jcbList.size() != 0) {
            currentTime = jcbWithPriorityList.get(0).getJcb().getSubmitTime();
        }
        List<Jcb> newJcbList = new LinkedList<>();

        while (jcbWithPriorityList.size() > 0) {
            Jcb jcb = jcbWithPriorityList.get(0).getJcb();

            jcb.setStartTime(currentTime);
            jcb.setFinishTime(currentTime += jcb.getNeedTime());
            jcb.setTurnaroundTime(currentTime - jcb.getSubmitTime());
            jcb.setTurnaroundTimeWithRight((double) jcb.getTurnaroundTime() / jcb.getNeedTime());
            newJcbList.add(jcb);

            //删除已经完成的作业并刷新优先级
            jcbWithPriorityList.remove(0);
            refreshPriority(jcbWithPriorityList, currentTime);
        }

        //根据到达时间排一下序
        newJcbList.sort(Comparator.comparing(Jcb::getSubmitTime));

        //计算平均周转时间
        double turnaroundTimeAvg = newJcbList.stream().mapToDouble(Jcb::getTurnaroundTime).average().orElse(0);
        //平均带权周转时间
        double turnaroundTimeWithRightAvg = newJcbList.stream().mapToDouble(Jcb::getTurnaroundTimeWithRight).average().orElse(0);

        return new DispatchResult(newJcbList, turnaroundTimeAvg, turnaroundTimeWithRightAvg);
    }

    /**
     * 优先级刷新
     *
     * @param jcbWithPriorityList 带优先级Jcb
     * @param currentTime 当前时间
     */
    private void refreshPriority(List<JcbWithPriority> jcbWithPriorityList, int currentTime) {
        jcbWithPriorityList.forEach(jcb -> {
            double priority = ((double) currentTime - jcb.getJcb().getSubmitTime()) / jcb.getJcb().getNeedTime() + 1;
            jcb.setPriority(priority);
        });

        //按优先级排序
        jcbWithPriorityList.sort((v1, v2) -> -Double.compare(v1.getPriority(), v2.getPriority()));
    }

    /**
     * 优先级加权
     */
    static class JcbWithPriority{
        private Jcb jcb;
        private double priority;

        public JcbWithPriority(Jcb jcb, double priority) {
            this.jcb = jcb;
            this.priority = priority;
        }

        public Jcb getJcb() {
            return jcb;
        }

        public void setJcb(Jcb jcb) {
            this.jcb = jcb;
        }

        public double getPriority() {
            return priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }
    }
}
