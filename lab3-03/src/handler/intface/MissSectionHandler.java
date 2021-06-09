package handler.intface;

import entity.Memory;
import entity.Section;

/**
 * @author GoodBoy
 * @date 2021-06-08
 */
public interface MissSectionHandler {
    /**
     * 缺段中断处理
     *
     * @param section 段
     * @param memory  程序的内存空间
     */
    void missSectionHandle(Section section, Memory memory);
}
