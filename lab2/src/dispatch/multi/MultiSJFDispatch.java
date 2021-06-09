package dispatch.multi;

import constant.ProcessConstant;
import dispatch.DispatchResult;
import job.Jcb;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 多道最短作业优先
 *
 * @author GoodBoy
 * @date 2021-06-04
 */
public class MultiSJFDispatch extends AbstractBaseMultiDispatch {
    public MultiSJFDispatch(Map<String, Integer> resource, Integer maxJob) {
        super(resource, maxJob);
    }

    @Override
    public Jcb getNextJcb(List<Jcb> jcbList, int currentTime) {
        if (Objects.isNull(jcbList) || jcbList.size() <= 0) {
            return null;
        }

        //根据作业时间的长短排序
        List<Jcb> sortedList = jcbList.stream().sorted(Comparator.comparing(Jcb::getNeedTime)).collect(Collectors.toList());

        for (Jcb next : sortedList) {
            if (next.getSubmitTime() > currentTime) {
                //当前作业味道大
                continue;
            }

            if (checkResource(next)) {
                jcbList.remove(next);
                return next;
            }
        }
        return null;
    }

}
