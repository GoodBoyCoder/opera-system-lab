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
        MemorySystem memorySystem = new MemorySystem(600, 100, new BestFitAllocationHandler());
        Partition partition = memorySystem.allocateUser(10);
        Partition partition1 = memorySystem.allocateUser(50);
//        Partition partition2 = memorySystem.allocateUser(25);
        memorySystem.free(partition);
        memorySystem.free(partition1);
//        memorySystem.free(partition2);
    }
}
