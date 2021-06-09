package handler;

import entity.Memory;
import entity.Page;
import handler.intface.MissPageHandler;

import java.util.List;

/**
 * @author GoodBoy
 * @date 2021-06-08
 */
public class FIFOPageHandler implements MissPageHandler {

    @Override
    public void missPageHandle(Page page, Memory memory) {
        if (null == page || null == memory) {
            throw new RuntimeException("page | memory 不能为空 in FIFOPageHandler");
        }

        List<Page> pageArrayList = memory.pageArrayList;
        if (pageArrayList.size() >= memory.MAX_PAGE) {
            //进行页面置换
            Page remove = memory.pageArrayList.remove(0);
            remove.setExist(Boolean.FALSE);
            //归还被分配到的块
            memory.unAllocateNumber.add(remove.getRealAddress());
        }
        page.setRealAddress(memory.unAllocateNumber.remove(0));
        pageArrayList.add(page);
        page.setExist(Boolean.TRUE);
    }
}
