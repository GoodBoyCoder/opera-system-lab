package allocation;

import constant.PartitionStatusEnum;
import entity.Partition;
import entity.PartitionList;

import java.util.Objects;

/**
 * @author GoodBoy
 * @date 2021-06-05
 */
public interface Allocation {
    /**
     * 区块分配
     *
     * @param list 空闲分区集合
     * @param size 分配大小
     * @return 分配的分区
     */
    Partition allocate(PartitionList list, long size);

    /**
     * 分区释放
     *
     * @param list      空闲分区
     * @param partition 待释放分区
     * @return 释放结果
     */
    boolean free(PartitionList list, Partition partition);
}
