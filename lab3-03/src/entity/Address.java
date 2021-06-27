package entity;

/**
 * @author GoodBoy
 * @date 2021-06-08
 */
public class Address {
    /**
     * 段号
     */
    private final long sectionId;

    /**
     * 偏移量
     */
    private final long offset;

    public Address(long sectionId, long offset) {
        this.sectionId = sectionId;
        this.offset = offset;
    }

    public long getSectionId() {
        return sectionId;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "Address{" +
                "sectionId=" + sectionId +
                ", offset=" + offset +
                '}';
    }
}
