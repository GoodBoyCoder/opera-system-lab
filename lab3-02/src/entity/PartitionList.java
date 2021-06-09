package entity;

import java.util.Objects;

/**
 * @author GoodBoy
 * @date 2021-06-05
 */
public class PartitionList {
    private long size = 0;
    private Partition head;
    private Partition tail;
    private Partition cur;

    public long getSize() {
        return size;
    }

    public Partition getHead() {
        return head;
    }

    public void setHead(Partition head) {
        this.head = head;
    }

    public Partition getTail() {
        return tail;
    }

    public void setTail(Partition tail) {
        this.tail = tail;
    }

    public Partition getCur() {
        return cur;
    }

    /**
     * 头插入
     *
     * @param partition 分区
     * @return 添加结果
     */
    public boolean firstAdd(Partition partition) {
        if (Objects.isNull(partition)) {
            return false;
        }

        partition.setPrePartition(null);
        if (Objects.isNull(head)) {
            //第一次插入
            head = partition;
            tail = head;
            partition.setNextPartition(null);
        } else {
            partition.setNextPartition(head);
            head.setPrePartition(partition);
            head = partition;
        }

        size++;
        return true;
    }

    /**
     * 尾插入
     *
     * @param partition 分区
     * @return 添加结果
     */
    public boolean lastAdd(Partition partition) {
        if (Objects.isNull(partition)) {
            return false;
        }

        partition.setNextPartition(null);
        if (Objects.isNull(head)) {
            //第一次插入
            head = partition;
            tail = head;
            partition.setPrePartition(null);
        } else {
            partition.setPrePartition(tail);
            tail.setNextPartition(partition);
            tail = partition;
        }

        size++;
        return true;
    }

    /**
     * 按照分区大小(小->大)顺序插入
     *
     * @param partition 分区
     * @return 操作结果
     */
    public boolean orderAdd(Partition partition) {
        if (Objects.isNull(partition)) {
            return false;
        }

        if (Objects.isNull(head)) {
            //首次插入
            return firstAdd(partition);
        }

        Partition cur = head;
        while (cur != null) {
            if (cur.getSize() > partition.getSize()) {
                //当前大于被插入，应该插入在当前节点的前面
                Partition prePartition = cur.getPrePartition();
                if (Objects.isNull(prePartition)) {
                    //头插入
                    return firstAdd(partition);
                } else {
                    prePartition.setNextPartition(partition);
                    partition.setPrePartition(prePartition);
                    partition.setNextPartition(cur);
                    cur.setPrePartition(partition);
                    size++;
                    return true;
                }
            }
            cur = cur.getNextPartition();
        }
        return lastAdd(partition);
    }

    /**
     * 根据首地址顺序插入
     *
     * @param partition 被插入分区
     * @return 添加结果
     */
    public boolean orderAddByFirstAddress(Partition partition) {
        if (Objects.isNull(partition)) {
            return false;
        }

        if (Objects.isNull(head)) {
            //首次插入
            return firstAdd(partition);
        }

        Partition cur = head;
        while (cur != null) {
            if (cur.getFirstAddress() > partition.getFirstAddress()) {
                //当前大于被插入，应该插入在当前节点的前面
                Partition prePartition = cur.getPrePartition();
                if (Objects.isNull(prePartition)) {
                    //头插入
                    return firstAdd(partition);
                } else {
                    prePartition.setNextPartition(partition);
                    partition.setPrePartition(prePartition);
                    partition.setNextPartition(cur);
                    cur.setPrePartition(partition);
                    size++;
                    return true;
                }
            }
            cur = cur.getNextPartition();
        }
        return lastAdd(partition);
    }

    public boolean beforeAdd(Partition addPartition, Partition beforePartition) {
        if (Objects.isNull(addPartition) || Objects.isNull(beforePartition)) {
            return false;
        }

        Partition cur = head;
        while (Objects.nonNull(cur)) {
            if (cur.equals(beforePartition)) {
                //在cur前插入
                Partition prePartition = cur.getPrePartition();
                if (Objects.isNull(prePartition)) {
                    return firstAdd(addPartition);
                } else {
                    prePartition.setNextPartition(addPartition);
                    addPartition.setPrePartition(prePartition);
                    cur.setPrePartition(addPartition);
                    addPartition.setNextPartition(cur);
                    size++;
                    return true;
                }
            }
            cur = cur.getNextPartition();
        }
        return false;
    }

    /**
     * 头删除
     *
     * @return 删除结果
     */
    public Partition firstRemove() {
        if (Objects.isNull(head)) {
            return null;
        }

        Partition firstHead = head;
        head = head.getNextPartition();
        if (null != head) {
            head.setPrePartition(null);
        } else {
            tail = null;
        }
        firstHead.setNextPartition(null);

        size--;
        return firstHead;
    }

    /**
     * 尾删除
     *
     * @return 删除结果
     */
    public Partition lastRemove() {
        if (Objects.isNull(head)) {
            return null;
        }
        Partition preTail = tail;
        tail = tail.getPrePartition();
        if (null != tail) {
            tail.setNextPartition(null);
        } else {
            head = null;
        }
        preTail.setPrePartition(null);

        size--;
        return preTail;
    }

    public Partition remove(Partition partition) {
        Partition cur = head;
        while (cur != null) {
            if (cur.equals(partition)) {
                Partition prePartition = cur.getPrePartition();
                Partition nextPartition = cur.getNextPartition();
                if (Objects.isNull(prePartition)) {
                    //头删除
                    return firstRemove();
                } else if (Objects.isNull(nextPartition)) {
                    //尾删除
                    return lastRemove();
                } else {
                    prePartition.setNextPartition(nextPartition);
                    nextPartition.setPrePartition(prePartition);

                    partition.setNextPartition(null);
                    partition.setPrePartition(null);
                    size--;
                    return partition;
                }
            }
            cur = cur.getNextPartition();
        }
        return null;
    }

    /**
     * 循环获取下一个
     *
     * @return 下一个分区
     */
    public Partition getRouteNext() {
        if (0 == size) {
            return null;
        }

        if (null == cur) {
            cur = head;
        }
        Partition result = cur;
        cur = cur.getNextPartition();
        return result;
    }

    //根据大小重新排序
    public void sortBySize() {
        if (0 == size) {
            return;
        }


    }

    @Override
    public String toString() {
        if (size == 0) {
            return "当前无分区";
        } else {
            Partition cur = head;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("分区大小 \t 分区状态 \t 分区类型 \t 首地址\n");
            while (cur != null) {
                stringBuffer.append(cur.getSize())
                        .append(" \t\t\t ")
                        .append(cur.getStatus())
                        .append(" \t\t\t ")
                        .append(cur.getType())
                        .append(" \t\t\t ")
                        .append(cur.getFirstAddress())
                        .append("\n");
                cur = cur.getNextPartition();
            }
            return stringBuffer.toString();
        }
    }
}
