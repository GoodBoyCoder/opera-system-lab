package handler;

import entity.Address;
import entity.Memory;
import entity.Page;
import entity.Section;
import handler.intface.MissPageHandler;
import handler.intface.MissSectionHandler;

import java.util.List;

/**
 * @author GoodBoy
 * @date 2021-06-08
 */
public class AddressTranslation {
    public static final Memory MEMORY = new Memory(2, 3);
    /**
     * 缺页中断处理器
     */
    private final MissPageHandler pageHandler;
    /**
     * 缺段中断处理器
     */
    private final MissSectionHandler sectionHandler;

    /**
     * 段表
     */
    private List<Section> sectionList;

    public AddressTranslation(List<Section> sectionList, MissPageHandler pageHandler, MissSectionHandler sectionHandler) {
        this.pageHandler = pageHandler;
        this.sectionHandler = sectionHandler;
        this.sectionList = sectionList;
    }

    /**
     * 逻辑地址转换
     *
     * @param address 逻辑地址（段号加段内地址）
     * @return 物理地址
     */
    public long translation(Address address) {
        //段地址检查
        long sectionId = address.getSectionId();
        if (sectionId > sectionList.size()) {
            throw new RuntimeException("段越界中断");
        }

        //将段内地址拆分为页号和页内地址
        long offset = address.getOffset();
        long pageId = offset / Page.PAGE_SIZE;
        long offsetInPage = offset % Page.PAGE_SIZE;

        //检查页号是否越界
        Section section = sectionList.get((int) sectionId);
        if (section.getPageSize() < pageId + 1) {
            throw new RuntimeException("页越界中断");
        }

        //检查页表是否在内存
        if (!section.getExist()) {
            //发生缺段中断
            System.out.println("缺段中断处理");
            sectionHandler.missSectionHandle(section, MEMORY);
        }

        //内存获取页表
        List<Page> pages = section.getPageList();
        //获取对应的页
        Page page = pages.get((int) pageId);
        //检查页是否在内存
        if (!page.getExist()) {
            //发生缺页中断
            System.out.println("缺页中断处理");
            pageHandler.missPageHandle(page, MEMORY);
        }

        //打印当前段表/页表
        System.out.println("-------------当前段信息-----------------");
        printSection(sectionList);
        System.out.println("当前地址位于第" + sectionId + "段, 第" + pageId + "页，业内偏移为" + offsetInPage);
        //形成物理地址返回
        return page.getRealAddress() * Page.PAGE_SIZE + offsetInPage;
    }

    private void printSection(List<Section> sectionList) {
        if (null == sectionList || sectionList.size() == 0) {
            return;
        }
        System.out.println("段号\t段大小\t页表大小\t是否调入内存\t页表块号（内存地址）");
        int index = 0;
        for (Section section : sectionList) {
            System.out.println(index++ + "\t" +
                    section.getSize() + "\t  " +
                    section.getPageSize() + "  \t\t" +
                    (section.getExist() ? "是" : "否") + "\t\t" +
                    (section.getExist() ? section.getBaseAddress() : ""));
            System.out.println("对应的页表状态：");
            printPage(section.getPageList());
            System.out.println("--->");
        }
    }

    private void printPage(List<Page> pages) {
        if (null == pages || pages.isEmpty()) {
            return;
        }
        System.out.println("页号\t是否调入内存\t块号");
        int index = 0;
        for (Page page : pages) {
            System.out.println(index++ + "\t" +
                    (page.getExist() ? "是" : "否") + "\t" +
                    (page.getExist() ? page.getRealAddress() : ""));
        }
    }
}
