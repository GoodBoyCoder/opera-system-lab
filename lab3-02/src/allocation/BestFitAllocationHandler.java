package allocation;

import constant.PartitionStatusEnum;
import entity.Partition;
import entity.PartitionList;

import java.util.Objects;

/**
 * @author GoodBoy
 * @date 2021-06-07
 */
public class BestFitAllocationHandler extends AbstractAllocation {
    @Override
    public Partition allocate(PartitionList list, long size) {
        if (Objects.isNull(list) || list.getSize() <= 0 || size <= 0) {
            return null;
        }

        Partition curPartition = list.getHead();
        while (null != curPartition) {
            //按分区大小依次查询
            if (curPartition.getSize() > size) {
                if (curPartition.getSize() - size <= MemorySystem.MINIMIZE) {
                    //两者差距小于最小分区大小，直接分配
                    return list.remove(curPartition);
                }
                //将划分后剩下的放入空闲链
                curPartition.setSize(curPartition.getSize() - size);
                long firstAddress = curPartition.getFirstAddress();
                curPartition.setFirstAddress(firstAddress + size);
                if (!(list.remove(curPartition) != null && list.orderAdd(curPartition))) {
                    // todo 目前无法还原现场
                    throw new IllegalStateException("分配错误");
                }
                //从分区划出size大小的分区
                return new Partition(size, firstAddress, PartitionStatusEnum.BUSY, curPartition.getType());
            }

            curPartition = curPartition.getNextPartition();
        }
        return null;
    }

    @Override
    public boolean free(PartitionList list, Partition partition) {
        if (Objects.isNull(list) || Objects.isNull(partition)) {
            return false;
        }
        Partition curPartition = list.getHead();
        Partition copyPartition = new Partition(partition.getSize(), partition.getFirstAddress(), partition.getStatus(), partition.getType());
        while (Objects.nonNull(curPartition)) {
            if (curPartition.getSize() > copyPartition.getSize()) {
                Partition prePartition = curPartition.getPrePartition();

                if (checkAdjoin(copyPartition, curPartition) && checkAdjoin(prePartition, copyPartition)) {
                    //前后都相邻
                    prePartition.setSize(prePartition.getSize() + copyPartition.getSize() + curPartition.getSize());
                    //remove调大小变化的，重新插入
                    return list.remove(curPartition) != null && list.remove(prePartition) != null && list.orderAdd(prePartition);
                } else if (checkAdjoin(copyPartition, curPartition)) {
                    //后相连
                    curPartition.setFirstAddress(copyPartition.getFirstAddress());
                    curPartition.setSize(copyPartition.getSize() + curPartition.getSize());
                    return list.remove(curPartition) != null && list.orderAdd(curPartition);
                } else if (checkAdjoin(prePartition, copyPartition)) {
                    //前相连
                    prePartition.setSize(prePartition.getSize() + copyPartition.getSize());
                    return list.remove(prePartition) != null && list.orderAdd(prePartition);
                } else {
                    //都不相邻
                    copyPartition.setStatus(PartitionStatusEnum.FREE);
                    return list.orderAdd(copyPartition);
                }
            }
            curPartition = curPartition.getNextPartition();
        }

        //添加在尾部
        if (checkAdjoin(list.getTail(), copyPartition)) {
            list.getTail().setSize(list.getTail().getSize() + copyPartition.getSize());
        } else {
            copyPartition.setStatus(PartitionStatusEnum.FREE);
            return list.lastAdd(copyPartition);
        }
        return true;
    }
}
