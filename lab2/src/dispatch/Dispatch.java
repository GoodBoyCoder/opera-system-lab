package dispatch;

import job.Jcb;

import java.util.List;

/**
 * @author GoodBoy
 * @date 2021-05-26
 */
public interface Dispatch {

    /**
     * 调度执行
     *
     * @param jcbList Jcb集合
     * @return 调度结果
     */
    DispatchResult run(List<Jcb> jcbList);
}
