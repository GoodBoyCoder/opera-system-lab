package handler;

import entity.Memory;
import entity.Page;
import entity.Section;
import handler.intface.MissSectionHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * FIFO算法对换段处理
 *
 * @author GoodBoy
 * @date 2021-06-08
 */
public class FIFOSectionHandler implements MissSectionHandler {
    @Override
    public void missSectionHandle(Section section, Memory memory) {
        if (null == section || null == memory) {
            throw new RuntimeException("section | memory 不能为空 in FIFOSectionHandler");
        }
        List<Section> sectionPage = memory.sectionPage;
        if (sectionPage.size() >= memory.MAX_SECTION) {
            //执行置换算法,将最先调入的调出
            Section removePage = sectionPage.remove(0);
            //修改被调出的段的状态
            removePage.setExist(Boolean.FALSE);
            //归还分区
            memory.unAllocateNumber.add(removePage.getBaseAddress());
        }

        if (null == section.getPageList()) {
            //构建页表
            long size = section.getSize() / Page.PAGE_SIZE;
            if (section.getSize() % Page.PAGE_SIZE != 0) {
                size++;
            }
            List<Page> pages = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                pages.add(new Page(false));
            }
            section.setPageList(pages);
            section.setPageSize(pages.size());
        }

        section.setBaseAddress(memory.unAllocateNumber.get(0));
        sectionPage.add(section);

        //修改状态
        section.setExist(Boolean.TRUE);
    }
}
