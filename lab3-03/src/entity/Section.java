package entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author GoodBoy
 * @date 2021-06-08
 */
public class Section {
    /**
     * 段空间大小
     */
    private long size;
    /**
     * 页表大小
     */
    private long pageSize;
    /**
     * 页表始址（该段页表在内存中的起始地址）
     */
    private long baseAddress;

    /**
     * 页表
     */
    private List<Page> pageList;
    /**
     * 存取方式
     */
    private Byte access;
    /**
     * 访问字段（记录访问次数）
     */
    private int readCount = 0;
    /**
     * 是否修改
     */
    private Boolean change = Boolean.FALSE;
    /**
     * 是否调入内存
     */
    private Boolean exist = Boolean.FALSE;
    /**
     * 在外存的起始地址
     */
    private long startAddressOutside;

    public Section(long size) {
        this.size = size * 1024;

        this.pageSize = this.size / Page.PAGE_SIZE;
        if (this.size % Page.PAGE_SIZE != 0) {
            this.pageSize ++;
        }
        List<Page> pages = new LinkedList<>();
        for (int i = 0; i < this.pageSize; i++) {
            pages.add(new Page(false));
        }
        this.setPageList(pages);
        this.setPageSize(pages.size());
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(long baseAddress) {
        this.baseAddress = baseAddress;
    }

    public Byte getAccess() {
        return access;
    }

    public void setAccess(Byte access) {
        this.access = access;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public Boolean getChange() {
        return change;
    }

    public void setChange(Boolean change) {
        this.change = change;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    public long getStartAddressOutside() {
        return startAddressOutside;
    }

    public void setStartAddressOutside(long startAddressOutside) {
        this.startAddressOutside = startAddressOutside;
    }

    public long getSize() {
        return size;
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Section)) {
            return false;
        }
        Section section = (Section) o;
        return getSize() == section.getSize() && getPageSize() == section.getPageSize() && getReadCount() == section.getReadCount() && getStartAddressOutside() == section.getStartAddressOutside() && Objects.equals(getBaseAddress(), section.getBaseAddress()) && Objects.equals(getAccess(), section.getAccess()) && Objects.equals(getChange(), section.getChange()) && getExist().equals(section.getExist());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSize(), getPageSize(), getBaseAddress(), getAccess(), getReadCount(), getChange(), getExist(), getStartAddressOutside());
    }

    @Override
    public String toString() {
        return "Section{" +
                "size=" + size +
                ", pageSize=" + pageSize +
                ", baseAddress=" + baseAddress +
                ", exist=" + exist +
                '}';
    }
}
