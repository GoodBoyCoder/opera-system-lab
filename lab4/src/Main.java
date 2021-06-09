import constant.CommandConstant;
import entity.FileDirectory;
import entity.Memory;
import process.ProcessMenu;

import java.util.Scanner;

/**
 * @author GoodBoy
 * @date 2021-06-09
 */
public class Main {
    public static void main(String[] args) {
        Memory memory = new Memory(10, 5, 500);

        FileDirectory directory = ProcessMenu.start(memory);
        if (null == directory) {
            System.out.println("感谢使用！！");
            System.exit(0);
        }
        //显示该目录下的所有文件
        ProcessMenu.showDirectory(directory);
        //输入操作命令

        //获取操作命令
        String command = "";
        //根据命令执行对应操作
        switch (command) {
            case CommandConstant.CREAT:
            case CommandConstant.CLOSE:
            case CommandConstant.OPEN:
            case CommandConstant.DELETE:
            case CommandConstant.READ:
            case CommandConstant.WRITE:
            default:
                break;
        }
    }
}
