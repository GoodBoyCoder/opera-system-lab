import allocation.BestFitAllocationHandler;
import allocation.MemorySystem;
import allocation.NextFitAllocateHandler;
import entity.Partition;

/**
 * @author GoodBoy
 * @date 2021-06-05
 */
public class Main {
    public static void main(String[] args) {
        MemorySystem memorySystem = new MemorySystem(640, 40, new BestFitAllocationHandler());
        Partition partition1 = memorySystem.allocateUser(130);
        Partition partition2 = memorySystem.allocateUser(60);
        Partition partition3 = memorySystem.allocateUser(100);
        memorySystem.free(partition2);
        Partition partition4 = memorySystem.allocateUser(200);
        memorySystem.free(partition3);
        memorySystem.free(partition1);
        Partition partition5 = memorySystem.allocateUser(140);
        Partition partition6 = memorySystem.allocateUser(60);
        Partition partition7 = memorySystem.allocateUser(50);
    }
}
