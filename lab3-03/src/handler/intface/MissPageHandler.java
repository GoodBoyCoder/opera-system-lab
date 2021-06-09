package handler.intface;

import entity.Memory;
import entity.Page;

/**
 * @author GoodBoy
 * @date 2021-06-08
 */
public interface MissPageHandler {
    /**
     * 缺页中断处理
     * @param page 页
     * @param memory 内存空间
     */
    void missPageHandle(Page page, Memory memory);
}
