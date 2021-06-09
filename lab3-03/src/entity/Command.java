package entity;

/**
 * @author GoodBoy
 * @date 2021-06-08
 */
public class Command {
    private final long sectionId;
    private final long pageId;
    private final long offset;

    public Command(long sectionId, long pageId, long offset) {
        this.sectionId = sectionId;
        this.pageId = pageId;
        this.offset = offset;
    }

    public long getSectionId() {
        return sectionId;
    }

    public long getPageId() {
        return pageId;
    }

    public long getOffset() {
        return offset;
    }
}
