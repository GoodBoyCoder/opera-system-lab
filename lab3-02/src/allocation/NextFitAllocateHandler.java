package allocation;

import constant.PartitionStatusEnum;
import entity.Partition;
import entity.PartitionList;

import java.util.Objects;

/**
 * @author GoodBoy
 * @date 2021-06-07
 */
public class NextFitAllocateHandler extends AbstractAllocation{
    @Override
    public Partition allocate(PartitionList list, long size) {
        if (Objects.isNull(list) || list.getSize() <= 0 || size <= 0) {
            return null;
        }

        Partition curPartition = list.getRouteNext();
        long listSize = list.getSize();
        //由于是循环获取，则终止循环的条件应该是
        while (listSize > 0) {
            if (size < curPartition.getSize()) {
                if (curPartition.getSize() - size <= MemorySystem.MINIMIZE) {
                    //两者差距小于最小分区大小，直接分配
                    return list.remove(curPartition);
                }
                //将划分后剩下的放入空闲链
                curPartition.setSize(curPartition.getSize() - size);
                long firstAddress = curPartition.getFirstAddress();
                curPartition.setFirstAddress(firstAddress + size);
                //从分区划出size大小的分区
                return new Partition(size, firstAddress, PartitionStatusEnum.BUSY, curPartition.getType());
            }
            curPartition = list.getRouteNext();
            listSize--;
        }
        return null;
    }
}
