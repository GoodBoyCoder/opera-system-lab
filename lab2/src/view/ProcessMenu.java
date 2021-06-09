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
//            int submitTime = getInt("输入作业到达时间：");
//            while (submitTime < 0) {
//                System.out.println("到达时间不能为负数！！");
//                submitTime = getInt("输入作业到达时间：");
//            }
            jcb.setSubmitTime(i);
            int needTime = getInt("输入服务时间：");
            while (needTime < 0) {
                System.out.println("服务时间不能为负数！！");
                needTime = getInt("输入服务时间：");
            }
            jcb.setNeedTime(needTime);
            jcb.setStatus(ProcessConstant.WAIT);
            jcb.setSubmitTime(i);
            jcbList.add(jcb);
        }
        return jcbList;
    }

    public static List<Jcb> initMultiJob(int n) {
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
            while (submitTime < 0) {
                System.out.println("到达时间不能为负数！！");
                submitTime = getInt("输入作业到达时间：");
            }
            jcb.setSubmitTime(submitTime);
            int needTime = getInt("输入服务时间：");
            while (needTime < 0) {
                System.out.println("服务时间不能为负数！！");
                needTime = getInt("输入服务时间：");
            }
            jcb.setNeedTime(needTime);
            //输入资源需求
            jcb.setNeedResources(initMultiResource("-------------输入作业需要资源---------------"));
            jcb.setStatus(ProcessConstant.WAIT);
            jcbList.add(jcb);
        }
        return jcbList;
    }

    public static int getInt(String showMessage) {
        int number;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(showMessage);
            try {
                number = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ne) {
                System.out.println("输入格式错误，请输入一个整数!!");
                continue;
            }
            break;
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

    public static int getSystemType() {
        int type = getInt("(输入-1退出)进入单道（0）/多道系统（1）：");
        while (type < -1 || type > 1) {
            System.out.println("命令错误！！");
            type = getInt("(输入-1退出)进入单道（0）/多道系统（1）：");
        }
        return type;
    }

    public static int getDispatchType() {
        int type = getInt("选择调度算法——1.先来先服务（FCFS）2.最短作业优先（SJF）3.响应比高者优先（HRN）");
        while (type < 1 || type > 3) {
            System.out.println("命令错误！！");
            type = getInt("选择调度算法——1.先来先服务（FCFS）2.最短作业优先（SJF）3.响应比高者优先（HRN）");
        }
        return type;
    }

    public static Map<String, Integer> initMultiResource(String info) {
        System.out.println(info);
        int index;
        do {
            index = getInt("输入总资源数：");
        } while (index <= 0);

        Map<String, Integer> resource = new HashMap<>(index);
        Scanner scanner = new Scanner(System.in);
        while (index > 0){
            System.out.println("输入具体资源[资源名 资源数]");
            String s = scanner.nextLine();
            String[] split = s.split("\\s+");
            if (split.length != 2) {
                System.out.println("输入格式错误！！！");
                continue;
            }
            if (resource.get(split[0]) != null) {
                System.out.println("资源名重复！！！");
                continue;
            }

            try {
                int count = Integer.parseInt(split[1]);
                resource.put(split[0], count);
            } catch (NumberFormatException e) {
                System.out.println("资源数格式错误！！！");
                continue;
            }
            index--;
        }
        return resource;
    }
}
