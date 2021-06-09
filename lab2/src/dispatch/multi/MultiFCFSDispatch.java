package dispatch.multi;

import constant.ProcessConstant;
import dispatch.DispatchResult;
import job.Jcb;
import view.ProcessMenu;

import java.util.*;

/**
 * @author GoodBoy
 * @date 2021-05-27
 */
public class MultiFCFSDispatch extends AbstractBaseMultiDispatch {

    public MultiFCFSDispatch(Map<String, Integer> resource, Integer maxJob) {
        super(resource, maxJob);
    }

    @Override
    public Jcb getNextJcb(List<Jcb> jcbList, int currentTime) {
        if (Objects.isNull(jcbList) || jcbList.size() <= 0) {
            return null;
        }

        Iterator<Jcb> iterator = jcbList.iterator();
        while (iterator.hasNext()) {
            Jcb jcb = iterator.next();
            if (currentTime < jcb.getSubmitTime()) {
                break;
            }
            //检查资源
            if (checkResource(jcb)) {
                iterator.remove();
                return jcb;
            }
        }
        return null;
    }
}
