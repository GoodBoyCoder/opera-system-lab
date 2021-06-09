package dispatch.single;

import dispatch.Dispatch;
import dispatch.DispatchResult;
import job.Jcb;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * FCFS调度
 *
 * @author GoodBoy
 * @date 2021-05-26
 */

public class FCFSDispatch implements Dispatch {

    @Override
    public DispatchResult run(List<Jcb> jcbList) {
        int currentTime = 0;

        if (jcbList.size() != 0) {
            currentTime = jcbList.get(0).getSubmitTime();
        }

        List<Jcb> jobList = new LinkedList<>();
        for (Jcb jcb : jcbList) {
            jcb.setStartTime(currentTime);
            jcb.setFinishTime(currentTime += jcb.getNeedTime());
            jcb.setTurnaroundTime(currentTime - jcb.getSubmitTime());
            jcb.setTurnaroundTimeWithRight((double) jcb.getTurnaroundTime() / jcb.getNeedTime());
            jobList.add(jcb);
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
