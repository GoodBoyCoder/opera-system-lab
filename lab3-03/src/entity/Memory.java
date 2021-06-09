package entity;

import java.util.*;

/**
 * 内存模拟
 *
 * @author GoodBoy
 * @date 2021-06-08
 */
public class Memory {
    public final int MAX_SECTION;
    public final int MAX_PAGE;

    /**
     * 在内存中的页表
     */
    public final List<Section> sectionPage;
    /**
     * 在内存中的页
     */
    public final List<Page> pageArrayList;

    public final List<Long> unAllocateNumber;

    public Memory(int maxSection, int maxPage) {
        if (maxPage <= 0 || maxSection <= 0) {
            throw new RuntimeException("参数不合法 In Memory Constructor");
        }
        this.MAX_SECTION = maxSection;
        this.MAX_PAGE = maxPage;
        sectionPage = new ArrayList<>(MAX_SECTION);
        pageArrayList = new ArrayList<>(MAX_PAGE);

        unAllocateNumber = new LinkedList<>();
        for (long i = 0; i < maxPage + maxSection; i++) {
            unAllocateNumber.add(i);
        }
    }
}
