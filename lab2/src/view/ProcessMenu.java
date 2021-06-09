package view;

import constant.ProcessConstant;
import dispatch.DispatchResult;
import job.Jcb;

import java.util.*;

/**
 * @author GoodBoy
 * @date 2021-05-27
 */
public class ProcessMenu {
    public static int getJobNumber() {
        return getInt("请输入作业数：");
    }

    public static List<Jcb> initJob(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("参数错误");
        }
        List<Jcb> jcbList = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            System.out.printf("作业序号%d%n", i);
            System.out.println("输入作业名：");
            Jcb jcb = new Jcb();
            String name = scanner.nextLine();
            jcb.setJobName(name);
            int submitTime = getInt("输入作业到达时间：");
            jcb.setSubmitTime(submitTime);
            int needTime = getInt("输入服务时间：");
            jcb.setNeedTime(needTime);
            jcb.setStatus(ProcessConstant.WAIT);
            jcb.setSubmitTime(i);
            jcbList.add(jcb);
        }
        scanner.close();
        return jcbList;
    }

    public static int getInt(String showMessage) {
        int number = 0;
        Scanner scanner = new Scanner(System.in);
        while (number <= 0) {
            System.out.print(showMessage);
            try {
                number = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ne) {
                System.out.println("输入格式错误，请输入一个正整数!!");
                continue;
            }
            if (number <= 0) {
                System.out.println("输入需为正整数！");
            }
        }
        return number;
    }

    public static void printResult(DispatchResult result) {
        System.out.println("进程名\t 到达时间\t 服务时间\t 完成时间\t 周转时间\t 带权周转时间");
        result.getJobList().forEach(job ->
                System.out.println(job.getJobName() + "   \t\t" +
                job.getSubmitTime() + "   \t\t" +
                job.getNeedTime() + "   \t\t" +
                job.getFinishTime() + "   \t\t" +
                job.getTurnaroundTime() + "   \t\t" +
                String.format("%.2f", job.getTurnaroundTimeWithRight())));
        System.out.println("平均周转时间：" + String.format("%.2f", result.getAvgTime()));
        System.out.println("平均带权周转时间：" + String.format("%.2f", result.getAvgTimeWithRight()));
    }

    public static void printJcbStatus(List<Jcb> jcbList) {
        System.out.println("进程名\t\t 状态\t 到达时间\t 服务时间\t 完成时间\t 所需资源");
        jcbList.forEach(job ->
                System.out.println(job.getJobName() + "   \t\t" +
                        ProcessConstant.convert(job.getStatus()) + "   \t\t" +
                        job.getSubmitTime() + "   \t\t" +
                        job.getNeedTime() + "   \t\t" +
                        Optional.ofNullable(job.getFinishTime()).orElse(-1) + "   \t\t" +
                        job.getNeedResources()));
    }
}
