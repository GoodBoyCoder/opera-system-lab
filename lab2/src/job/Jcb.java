package job;

import constant.ProcessConstant;

import java.util.Map;

/**
 * @author GoodBoy
 * @date 2021-05-26
 */
public class Jcb {
    private String jobName;
    /**
     * 作业提交时间
     */
    private Integer submitTime;
    /**
     * 作业服务时间
     */
    private Integer needTime;
    /**
     * 作业需要资源
     */
    private Map<String, Integer> needResources;
    /**
     * 作业开始服务时间
     */
    private Integer startTime;
    /**
     * 作业完成时间
     */
    private Integer finishTime;
    /**
     * 周转时间
     */
    private Integer turnaroundTime;
    /**
     * 加权周转时间
     */
    private Double turnaroundTimeWithRight;
    /**
     * 作业执行状态
     */
    private Byte status = ProcessConstant.WAIT;

    public Jcb() {
    }

    public Jcb(String jobName, Integer submitTime, Integer needTime) {
        this.jobName = jobName;
        this.submitTime = submitTime;
        this.needTime = needTime;
    }

    public Jcb(String jobName, Integer submitTime, Integer needTime, Map<String, Integer> needResources) {
        this.jobName = jobName;
        this.submitTime = submitTime;
        this.needTime = needTime;
        this.needResources = needResources;
    }

    public Integer getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Integer submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getNeedTime() {
        return needTime;
    }

    public void setNeedTime(Integer needTime) {
        this.needTime = needTime;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Integer finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(Integer turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public Double getTurnaroundTimeWithRight() {
        return turnaroundTimeWithRight;
    }

    public void setTurnaroundTimeWithRight(Double turnaroundTimeWithRight) {
        this.turnaroundTimeWithRight = turnaroundTimeWithRight;
    }

    public Map<String, Integer> getNeedResources() {
        return needResources;
    }

    public void setNeedResources(Map<String, Integer> needResources) {
        this.needResources = needResources;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Jcb{" +
                "jobName='" + jobName + '\'' +
                ", submitTime=" + submitTime +
                ", needTime=" + needTime +
                ", needResources=" + needResources +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", turnaroundTime=" + turnaroundTime +
                ", turnaroundTimeWithRight=" + turnaroundTimeWithRight +
                ", status=" + status +
                '}';
    }
}
