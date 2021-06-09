package process;

import entity.File;
import entity.FileDirectory;
import entity.Memory;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author GoodBoy
 * @date 2021-06-09
 */
public class ProcessMenu {
    public static FileDirectory start(Memory memory) {
        if (null == memory) {
            throw new RuntimeException("系统初始化失败");
        }

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("请输入用户名(输入exit退出系统)：");
            String userName = scanner.nextLine();
            if ("exit".equals(userName.toLowerCase(Locale.ROOT))) {
                break;
            }
            //比对获取目录
            FileDirectory fileDirectory = memory.getMfdMap().get(userName);
            if (null == fileDirectory) {
                System.out.println("无此用户文件！是否现在创建？y/n：");
                //获取选择
                if (getBooleanChoose()) {
                    //创建用户目录
                    if (memory.getMfdMap().size() >= memory.getMaxUser()) {
                        System.out.println("当前可用用户已满！");
                        continue;
                    } else {
                        fileDirectory = new FileDirectory(userName);
                        memory.getMfdMap().put(userName, fileDirectory);
                    }
                } else {
                    continue;
                }
            }
            return fileDirectory;
        } while (true);
        return null;
    }

    public static boolean getBooleanChoose() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            choice = scanner.nextLine().toUpperCase(Locale.ROOT);
        } while (!"Y".equals(choice) && !"N".equals(choice));

        return "Y".equals(choice);
    }

    public static void showDirectory(FileDirectory fileDirectory) {
        if (null == fileDirectory) {
            return;
        }
        if (0 == fileDirectory.getSize()) {
            System.out.println("该目录下无文件！");
            return;
        }
        List<File> fileList = fileDirectory.getFileList();
        System.out.println("文件名\t\t文件保护码\t\t文件长度");
        for (File file : fileList) {
            System.out.println(file.getFileName() + "\t\t" + Arrays.toString(file.getAccessCode()) + "\t\t" + file.getLength());
        }
    }

    public static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("输入操作指令：");
            String command = scanner.nextLine();

        } while (true);
    }
}
