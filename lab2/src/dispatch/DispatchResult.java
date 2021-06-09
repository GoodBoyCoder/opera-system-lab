package dispatch;

import job.Jcb;

import java.util.List;

/**
 * @author GoodBoy
 * @date 2021-05-26
 */
public class DispatchResult {
    private List<Jcb> jcbList;
    private Double avgTime;
    private Double avgTimeWithRight;

    public DispatchResult() {
    }

    public DispatchResult(List<Jcb> jcbList, Double avgTime, Double avgTimeWithRight) {
        this.jcbList = jcbList;
        this.avgTime = avgTime;
        this.avgTimeWithRight = avgTimeWithRight;
    }

    public List<Jcb> getJobList() {
        return jcbList;
    }

    public void setJobList(List<Jcb> jcbList) {
        this.jcbList = jcbList;
    }

    public Double getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(Double avgTime) {
        this.avgTime = avgTime;
    }

    public Double getAvgTimeWithRight() {
        return avgTimeWithRight;
    }

    public void setAvgTimeWithRight(Double avgTimeWithRight) {
        this.avgTimeWithRight = avgTimeWithRight;
    }
}
