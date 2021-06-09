package allocation;

import constant.PartitionStatusEnum;
import constant.PartitionTypeConstant;
import entity.Partition;
import entity.PartitionList;

import java.util.Objects;

/**
 * @author GoodBoy
 * @date 2021-06-05
 */
public class MemorySystem {
    /**
     * 可用的内存总空间（KB）
     */
    private final long totalSize;
    /**
     * 用户可用空间
     */
    private final long userSize;
    /**
     * 系统可用空间
     */
    private final long systemSize;

    /**
     * 最小/不可分割分区大小（4KB）
     */
    public static final long MINIMIZE = 4;

    /**
     * 用户分区
     */
    private PartitionList userPart;
    /**
     * 操作系统分区
     */
    private PartitionList systemPart;

    /**
     * 已分配分区
     */
    private final PartitionList busyPart = new PartitionList();

    /**
     * 分区操作处理器
     */
    private final Allocation allocationHandler;

    public MemorySystem(long totalSize, long systemSize) {
        this(totalSize, systemSize, new FirstFitAllocateHandler());
    }


    public MemorySystem(long totalSize, long systemSize, Allocation allocationHandler) {
        if (totalSize < MINIMIZE || systemSize < MINIMIZE || totalSize - systemSize < MINIMIZE) {
            throw new IllegalArgumentException("分区大小参数不合法");
        }
        this.totalSize = totalSize;
        this.userSize = totalSize - systemSize;
        this.systemSize = systemSize;

        //初始化分区（小到大）
        if (!(initUserPart() && initSystemPart())) {
            throw new RuntimeException("分区初始化失败");
        }

        //设置处理器
        this.allocationHandler = Objects.isNull(allocationHandler) ? new FirstFitAllocateHandler() : allocationHandler;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public long getUserSize() {
        return userSize;
    }

    public long getSystemSize() {
        return systemSize;
    }

    /**
     * 初始化用户分区
     *
     * @return 初始化结果
     */
    private boolean initUserPart() {
        if (userSize <= MINIMIZE) {
            return false;
        }
        userPart = initPartition(userSize, PartitionTypeConstant.USER);
        return true;
    }

    /**
     * 初始化系统分区
     *
     * @return 初始化结果
     */
    private boolean initSystemPart() {
        if (systemSize <= MINIMIZE) {
            return false;
        }

        systemPart = initPartition(systemSize, PartitionTypeConstant.SYSTEM);

        return true;
    }

    private PartitionList initPartition(long size, Byte type) {
        //以最小分区开始2次幂增长
        long unitSize = MINIMIZE;
        long firstAddress = 0;
        PartitionList partitionList = new PartitionList();

        while (size > 0) {
            if (size - unitSize - unitSize * 2 < 0) {
                unitSize = size;
            }
            Partition partition = new Partition(unitSize, firstAddress, PartitionStatusEnum.FREE, type);
            partitionList.lastAdd(partition);
            firstAddress += unitSize;
            size = size - unitSize;
            unitSize *= 2;
        }
        return partitionList;
    }

    /**
     * 用户分配空间
     *
     * @param size 空间大小
     * @return 分配结果
     */
    public Partition allocateUser(long size) {
        return allocate(userPart, size);
    }

    /**
     * 系统分配空间
     *
     * @param size 空间大小
     * @return 分配结果
     */
    public Partition allocateSystem(long size) {
        return allocate(systemPart, size);
    }

    private Partition allocate(PartitionList list, long size) {
        if (Objects.isNull(list) || size <= 0) {
            return null;
        }

        //选择分配算法
        Partition allocate = allocationHandler.allocate(list, size);
        if (null != allocate) {
            //添加进已分配队列
            allocate.setStatus(PartitionStatusEnum.BUSY);
            busyPart.lastAdd(allocate);
        }
        print();
        return allocate;
    }

    /**
     * 用户释放分区
     *
     * @param partition 被释放分区
     * @return 释放结果
     */
    private boolean userFree(Partition partition) {
        if (allocationHandler.free(userPart, partition)) {
            Partition remove = busyPart.remove(partition);
            remove.setStatus(PartitionStatusEnum.FREE);
            print();
            return true;
        }
        return false;
    }

    /**
     * 系统分区
     *
     * @param partition 被释放分区
     * @return 释放结果
     */
    private boolean systemFree(Partition partition) {
        if (allocationHandler.free(systemPart, partition)) {
            Partition remove = busyPart.remove(partition);
            remove.setStatus(PartitionStatusEnum.FREE);
            print();
            return true;
        }
        return false;
    }

    public boolean free(Partition partition) {
        boolean result;
        switch (partition.getType()) {
            case PartitionTypeConstant.SYSTEM:
                result = systemFree(partition);
                break;
            case PartitionTypeConstant.USER:
                result = userFree(partition);
                break;
            default:
                throw new IllegalStateException("不支持的类型");
        }
        return result;
    }

    private void print() {
        System.out.println("用户分区：\n" + userPart);
        System.out.println("系统分区：\n" + systemPart);
        System.out.println("已分配分区：\n" + busyPart);
    }
}
