package entity;

import constant.PartitionStatusEnum;

import java.util.Objects;

/**
 * 分区
 *
 * @author GoodBoy
 * @date 2021-06-05
 */
public class Partition {
    /**
     * 分区大小
     */
    private long size;
    /**
     * 分区状态
     */
    private PartitionStatusEnum status;
    /**
     * 前向指针
     */
    private Partition prePartition;
    /**
     * 后向指针
     */
    private Partition nextPartition;

    /**
     * 分区类型{@link constant.PartitionTypeConstant}
     */
    private byte type;
    /**
     * 可分配空间首地址
     */
    private long firstAddress;

    public Partition() {
    }

    public Partition(long size, long firstAddress, PartitionStatusEnum status, Byte type) {
        if (size <= 0 || firstAddress < 0) {
            throw new IllegalArgumentException("分区大小||首地址需要是大于0的整数");
        }
        this.size = size;
        this.firstAddress = firstAddress;
        this.status = status;
        this.type = type;
    }


    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public PartitionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PartitionStatusEnum status) {
        this.status = status;
    }

    public Partition getPrePartition() {
        return prePartition;
    }

    public void setPrePartition(Partition prePartition) {
        this.prePartition = prePartition;
    }

    public Partition getNextPartition() {
        return nextPartition;
    }

    public void setNextPartition(Partition nextPartition) {
        this.nextPartition = nextPartition;
    }

    public long getFirstAddress() {
        return firstAddress;
    }

    public void setFirstAddress(long firstAddress) {
        this.firstAddress = firstAddress;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partition)) {
            return false;
        }
        Partition partition = (Partition) o;
        return getSize() == partition.getSize() && getType() == partition.getType() && getFirstAddress() == partition.getFirstAddress() && getStatus() == partition.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSize(), getStatus(), getType(), getFirstAddress());
    }

    @Override
    public String toString() {
        return "Partition{" +
                "size=" + size +
                ", status=" + status +
                ", type=" + type +
                ", firstAddress=" + firstAddress +
                '}';
    }
}
