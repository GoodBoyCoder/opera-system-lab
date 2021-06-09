package allocation;

import constant.PartitionStatusEnum;
import entity.Partition;
import entity.PartitionList;

import java.util.Objects;

/**
 * @author GoodBoy
 * @date 2021-06-07
 */
public abstract class AbstractAllocation implements Allocation{

    @Override
    public boolean free(PartitionList list, Partition partition) {
        if (Objects.isNull(list) || Objects.isNull(partition)) {
            return false;
        }
        Partition curPartition = list.getHead();
        Partition copyPartition = new Partition(partition.getSize(), partition.getFirstAddress(), partition.getStatus(), partition.getType());
        while (Objects.nonNull(curPartition)) {
            if (curPartition.getFirstAddress() > copyPartition.getFirstAddress()) {
                Partition prePartition = curPartition.getPrePartition();

                if (checkAdjoin(copyPartition, curPartition) && checkAdjoin(prePartition, copyPartition)) {
                    //前后都相邻
                    prePartition.setSize(prePartition.getSize() + copyPartition.getSize() + curPartition.getSize());
                    return list.remove(curPartition) != null;
                } else if (checkAdjoin(copyPartition, curPartition)) {
                    //后相连
                    curPartition.setFirstAddress(copyPartition.getFirstAddress());
                    curPartition.setSize(copyPartition.getSize() + curPartition.getSize());
                    return true;
                } else if (checkAdjoin(prePartition, copyPartition)) {
                    //前相连
                    prePartition.setSize(prePartition.getSize() + copyPartition.getSize());
                    return true;
                } else {
                    //都不相邻
                    copyPartition.setStatus(PartitionStatusEnum.FREE);
                    return list.beforeAdd(copyPartition, curPartition);
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


    /**
     * 判断两分区是否相邻
     * @param p1 分区1
     * @param p2 分区2
     * @return 是否相邻
     */
    protected boolean checkAdjoin(Partition p1, Partition p2) {
        if (Objects.isNull(p1) || Objects.isNull(p2)) {
            return false;
        }

        //左相邻
        boolean left = p1.getFirstAddress() + p1.getSize() == p2.getFirstAddress();
        //右相邻
        boolean right = p2.getFirstAddress() + p2.getSize() == p1.getFirstAddress();

        return left || right;
    }
}
