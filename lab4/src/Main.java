import entity.FileDirectory;
import entity.Memory;
import process.ProcessMenu;

/**
 * @author GoodBoy
 * @date 2021-06-09
 */
public class Main {
    public static void main(String[] args) {
        Memory memory = new Memory(10, 5, 10, 500);

        do {
            FileDirectory directory = ProcessMenu.start(memory);
            if (null == directory) {
                System.out.println("感谢使用！！");
                System.exit(0);
            }
            //输入操作命令
            ProcessMenu.doCommand(directory, memory);
        } while (true);

    }
}
