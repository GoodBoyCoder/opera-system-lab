package entity;

/**
 * @author GoodBoy
 * @date 2021-06-08
 */
public class Page {
    /**
     * 页大小
     */
    public static final int PAGE_SIZE = 4 * 1000;

    /**
     * 是否调入内存
     */
    private Boolean exist;
    /**
     * 存储块地址
     */
    private long realAddress = -1;

    public Page(Boolean exist) {
        this.exist = exist;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    public long getRealAddress() {
        return realAddress;
    }

    public void setRealAddress(long realAddress) {
        this.realAddress = realAddress;
    }

    @Override
    public String toString() {
        return "Page{" +
                "exist=" + exist +
                ", realAddress=" + realAddress +
                '}';
    }
}
