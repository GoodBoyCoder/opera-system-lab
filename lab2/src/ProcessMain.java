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
//        //初始化作业列表
//        int jobNumber = ProcessMenu.getJobNumber();
//        List<Jcb> jcbList = ProcessMenu.initJob(jobNumber);
//
//        //按提交顺序生成对应的jcb控制块
//        jcbList.sort(Comparator.comparing(Jcb::getSubmitTime));
//
        List<Jcb> jcbList = new LinkedList<>();
        jcbList.add(new Jcb("A", 0, 3));
        jcbList.add(new Jcb("B", 1, 1));
        jcbList.add(new Jcb("C", 2, 2));
        jcbList.add(new Jcb("D", 3, 1));
//        jcbList.add(new Jcb("E", 4, 4));
//
//        //执行作业调度
//        Dispatch dispatch = new FCFSDispatch();
//        DispatchResult result = dispatch.run(jcbList);
//
//        //打印调度结果
//        ProcessMenu.printResult(result);
//
        //执行SJF调度
        System.out.println("最短作业调度");
        Dispatch sjfDispatch = new SJFDispatch();
        ProcessMenu.printResult(sjfDispatch.run(jcbList));
//
//        //执行HRN调度
//        System.out.println("高响应比优先调度");
//        Dispatch hrnDispatch = new HRNDispatch();
//        ProcessMenu.printResult(hrnDispatch.run(jcbList));


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
