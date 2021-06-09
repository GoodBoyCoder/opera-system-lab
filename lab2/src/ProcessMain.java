import constant.DispatchTypeConstant;
import dispatch.Dispatch;
import dispatch.DispatchResult;
import dispatch.multi.MultiFCFSDispatch;
import dispatch.multi.MultiSJFDispatch;
import dispatch.single.FCFSDispatch;
import dispatch.single.HRNDispatch;
import dispatch.single.SJFDispatch;
import job.Jcb;
import view.ProcessMenu;

import java.util.*;

/**
 * @author GoodBoy
 * @date 2021-05-26
 */
public class ProcessMain {
    public static void main(String[] args) {
        int systemType;
        do {
            systemType = ProcessMenu.getSystemType();
            if (0 == systemType) {
                //单道
                //初始化作业列表
                int jobNumber = ProcessMenu.getJobNumber();
                List<Jcb> jcbList = ProcessMenu.initJob(jobNumber);
                //按提交顺序生成对应的jcb控制块
                jcbList.sort(Comparator.comparing(Jcb::getSubmitTime));
                //选择调度算法
                int dispatchType = ProcessMenu.getDispatchType();
                switch (dispatchType) {
                    case DispatchTypeConstant.FCFS:
                        System.out.println("先来先服务调度");
                        Dispatch fcfsDispatch = new FCFSDispatch();
                        ProcessMenu.printResult(fcfsDispatch.run(jcbList));
                        break;
                    case DispatchTypeConstant.SJF:
                        System.out.println("最短作业调度");
                        Dispatch sjfDispatch = new SJFDispatch();
                        ProcessMenu.printResult(sjfDispatch.run(jcbList));
                        break;
                    case DispatchTypeConstant.HRN:
                        System.out.println("高响应比优先调度");
                        Dispatch hrnDispatch = new HRNDispatch();
                        ProcessMenu.printResult(hrnDispatch.run(jcbList));
                        break;
                    default:
                        break;
                }
            } else if (1 == systemType) {
                //多道
                int maxJob = ProcessMenu.getInt("该系统为几道系统：");
                while (maxJob <= 0) {
                    System.out.println("非法输入！！！");
                    maxJob = ProcessMenu.getInt("该系统为几道系统：");
                }
                //初始化系统
                Map<String, Integer> multiResource = ProcessMenu.initMultiResource("-----------------输入该系统拥有的资源---------------");
                //初始化作业列表
                int jobNumber = ProcessMenu.getJobNumber();
                List<Jcb> jcbList = ProcessMenu.initMultiJob(jobNumber);
                //按提交顺序生成对应的jcb控制块
                jcbList.sort(Comparator.comparing(Jcb::getSubmitTime));
                //选择调度算法
                int dispatchType = ProcessMenu.getDispatchType();
                switch (dispatchType) {
                    case DispatchTypeConstant.FCFS:
                        System.out.println("先来先服务调度");
                        Dispatch fcfsDispatch = new MultiFCFSDispatch(multiResource, maxJob);
                        ProcessMenu.printResult(fcfsDispatch.run(jcbList));
                        break;
                    case DispatchTypeConstant.SJF:
                        System.out.println("最短作业调度");
                        Dispatch sjfDispatch = new MultiSJFDispatch(multiResource, maxJob);
                        ProcessMenu.printResult(sjfDispatch.run(jcbList));
                        break;
                    case DispatchTypeConstant.HRN:
                        System.out.println("高响应比优先调度暂不支持！");
                        break;
                    default:
                        break;
                }
            }
        } while (systemType != -1);

//        List<Jcb> jcbListForMulti = new LinkedList<>();
//        Map<String, Integer> needResourceMap = new HashMap<>(1);
//        needResourceMap.put("A", 6);
//        jcbListForMulti.add(new Jcb("A", 0, 6, needResourceMap));
//        Map<String, Integer> needResourceMap2 = new HashMap<>(2);
//        needResourceMap2.put("A", 2);
//        needResourceMap2.put("B", 2);
//        jcbListForMulti.add(new Jcb("B", 1, 3, needResourceMap2));
//        Map<String, Integer> needResourceMap3 = new HashMap<>(2);
//        needResourceMap3.put("A", 1);
//        needResourceMap3.put("B", 4);
//        jcbListForMulti.add(new Jcb("C", 2, 5, needResourceMap3));
//        Map<String, Integer> needResourceMap4 = new HashMap<>(1);
//        needResourceMap4.put("B", 2);
//        jcbListForMulti.add(new Jcb("D", 3, 2, needResourceMap4));
//        Map<String, Integer> needResourceMap5 = new HashMap<>(1);
//        needResourceMap5.put("A", 4);
//        jcbListForMulti.add(new Jcb("E", 4, 4, needResourceMap5));
//
//        //执行多道批处理FCFS调度
//        System.out.println("（多道程序系统）先来先到调度");
//        Map<String, Integer> resourceMap = new HashMap<>(2);
//        resourceMap.put("A", 10);
//        resourceMap.put("B", 5);
//        Dispatch multiFCFSDispatch = new MultiFCFSDispatch(resourceMap, 2);
//        ProcessMenu.printResult(multiFCFSDispatch.run(jcbListForMulti));
//
//        //执行多道批处理FCFS调度
//        System.out.println("（多道程序系统）最短作业优先调度");
//        Map<String, Integer> resourceMap2 = new HashMap<>(2);
//        resourceMap2.put("A", 5);
//        resourceMap2.put("B", 5);
//        Dispatch multiSJFDispatch = new MultiSJFDispatch(resourceMap2, 2);
//        ProcessMenu.printResult(multiSJFDispatch.run(jcbListForMulti));
    }
}
