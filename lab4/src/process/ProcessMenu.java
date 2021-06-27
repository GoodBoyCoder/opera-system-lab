package process;

import constant.CommandConstant;
import entity.File;
import entity.FileDirectory;
import entity.Memory;

import java.util.*;

/**
 * @author GoodBoy
 * @date 2021-06-09
 */
public class ProcessMenu {
    public static FileDirectory start(Memory memory) {
        if (null == memory) {
            throw new RuntimeException("系统初始化失败");
        }
        memory.getAfdList().clear();
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
                        fileDirectory = new FileDirectory(userName, userName);
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

    public static void showDirectory(FileDirectory fileDirectory, List<File> shareFile) {
        if (null == fileDirectory) {
            return;
        }
        if (0 == fileDirectory.getSize()) {
            System.out.println("该目录下无文件！");
            return;
        }
        List<File> fileList = fileDirectory.getFileList();
        System.out.println("文件名\t\t文件保护码\t\t文件长度\t\t文件打开状态\t\t文件共享状态");
        for (File file : fileList) {
            System.out.println(file.getFileName() + "\t\t" +
                    Arrays.toString(file.getAccessCode()) + "\t\t" +
                    file.getLength() + "\t\t" +
                    (file.isStatus() ? "打开" : "未打开") + "\t\t" +
                    (file.isShare() ? "共享" : "未共享"));
        }

        if (null != shareFile && !shareFile.isEmpty()) {
            System.out.println("共享文件");
            System.out.println("文件名\t\t文件保护码\t\t文件长度\t\t文件打开状态\t\t文件共享状态");
            for (File file : shareFile) {
                System.out.println(file.getOrderName() + "." + file.getFileName() + "\t\t" +
                        Arrays.toString(file.getAccessCode()) + "\t\t" +
                        file.getLength() + "\t\t" +
                        (file.isStatus() ? "打开" : "未打开") + "\t\t" +
                        (file.isShare() ? "共享" : "未共享"));
            }
        }
    }

    public static void doCommand(FileDirectory directory, Memory memory, String userName) {
        int creatFile = memory.getMaxCreatCount();
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("当前目录文件");
            showDirectory(directory, memory.getSharedFileList());
            System.out.println("输入操作指令：");
            String command = scanner.nextLine();
            if ("exit".equals(command.toLowerCase(Locale.ROOT))) {
                break;
            }
            String[] split = command.split("\\s+");
            if (split.length < 2) {
                System.out.println("命令格式错误！");
            } else {
                String commandReal = split[0].toLowerCase(Locale.ROOT);
                String fileName = split[1];
                //匹配对应操作
                switch (commandReal) {
                    case CommandConstant.CREAT:
                        if (creatFile > 0) {
                            //创建文件
                            if (creat(directory, fileName, userName)) {
                                System.out.println("文件创建成功");
                            } else {
                                System.out.println("文件创建失败");
                            }
                            creatFile--;
                        } else {
                            System.out.println("创建失败，已达到最大创建文件数量");
                        }
                        break;
                    case CommandConstant.CLOSE:
                        //关闭文件
                        if (close(fileName, memory.getAfdList())) {
                            System.out.println("文件关闭成功");
                        } else {
                            System.out.println("文件关闭失败");
                        }
                        break;
                    case CommandConstant.OPEN:
                        if (memory.getAfdList().size() < memory.getMaxRunningFile()) {
                            //打开文件
                            if (open(directory, fileName, memory.getAfdList())) {
                                System.out.println("文件打开成功");
                            } else {
                                System.out.println("文件打开失败");
                            }
                        } else {
                            System.out.println("文件打开失败，已达到最大同时运行文件数");
                        }
                        break;
                    case CommandConstant.DELETE:
                        //删除文件
                        if (delete(directory, fileName, memory.getAfdList())) {
                            System.out.println("文件删除成功");
                        } else {
                            System.out.println("文件删除失败");
                        }
                        break;
                    case CommandConstant.READ:
                        //读取文件
                        read(fileName, memory.getAfdList(), userName);
                        break;
                    case CommandConstant.WRITE:
                        //文件写入
                        write(fileName, memory.getAfdList(), userName);
                        break;
                    case CommandConstant.RENAME:
                        if (split.length == 3 && rename(directory, split[1], split[2])) {
                            System.out.println("文件重命名成功");
                        } else if (split.length != 3) {
                            System.out.println("命令格式错误[command oldName newName]");
                        } else {
                            System.out.println("文件重命名失败");
                        }
                        break;
                    default:
                        break;
                }
            }
        } while (true);
    }

    public static boolean creat(FileDirectory fileDirectory, String fileName, String userName) {
        if (null == fileDirectory || null == fileName) {
            System.out.println("文件目录为空");
            return false;
        }
        return fileDirectory.saveFile(new File(fileName, userName));
    }

    public static boolean close(String fileName, List<File> afd) {
        if (null == fileName || null == afd) {
            System.out.println("系统错误");
            return false;
        }
        //查找是否存在
        Iterator<File> iterator = afd.iterator();
        while (iterator.hasNext()) {
            File file = iterator.next();
            if (file.getFileName().equals(fileName)) {
                file.setStatus(false);
                iterator.remove();
                return true;
            }
        }
        System.out.println("无当前打开的文件");
        return false;
    }

    public static boolean open(FileDirectory directory, String fileName, List<File> afd) {
        if (null == directory || null == fileName || null == afd) {
            System.out.println("系统错误");
            return false;
        }
        //查找有无该文件
        File file = directory.exit(fileName);
        if (file != null) {
            //检查是否重复打开
            if (file.isStatus()) {
                System.out.println("该文件已经被打开");
                return false;
            }
            file.setStatus(true);
            return afd.add(file);
        }
        System.out.println("当前目录该文件不存在");
        return false;
    }

    public static boolean delete(FileDirectory fileDirectory, String fileName, List<File> afd) {
        if (null == fileDirectory || null == fileName || null == afd) {
            System.out.println("文件目录为空");
            return false;
        }
        File file = fileDirectory.exit(fileName);
        //查找是否存在
        if (null == file) {
            System.out.println("该文件不存在！!!");
            return false;
        } else {
            if (file.isStatus()) {
                close(fileName, afd);
            }
            return fileDirectory.deleteFile(fileName);
        }
    }

    public static boolean read(String fileName, List<File> afd, String userName) {
        if (null == fileName || null == afd || null == userName) {
            System.out.println("系统错误");
            return false;
        }

        for (File file : afd) {
            if (file.getFileName().equals(fileName)) {
                Scanner scanner = new Scanner(System.in);
                file.setWritePoint(file.getLength());
                file.setReadPoint(0);
                //检查读权限
                boolean[] accessCode = file.getAccessCode();
                if (file.getOrderName().equals(userName) || accessCode[File.READ_ACCESS]) {
                    do {
                        System.out.println("当前读指针：" + file.getReadPoint() + " 文件大小：" + file.getLength());
                        System.out.println("输入要读的位置(输入exit退出)：");
                        String input = scanner.nextLine();
                        if (!"exit".equals(input.toLowerCase(Locale.ROOT))) {
                            long index;
                            try {
                                index = Long.parseLong(input);
                            } catch (NumberFormatException e) {
                                System.out.println("输入格式错误！");
                                continue;
                            }
                            if (index >= file.getLength()) {
                                System.out.println("超出文件大小");
                            } else {
                                file.setReadPoint(index);
                            }
                        } else {
                            break;
                        }
                    } while (true);
                } else {
                    System.out.println("你无该文件的读权限");
                }
                return true;
            }
        }
        System.out.println("当前无正在运行的该文件");
        return false;
    }

    public static boolean write(String fileName, List<File> afd, String userName) {
        if (null == fileName || null == afd) {
            System.out.println("系统错误");
            return false;
        }

        for (File file : afd) {
            if (file.getFileName().equals(fileName)) {
                Scanner scanner = new Scanner(System.in);
                //检查写权限
                boolean[] accessCode = file.getAccessCode();
                file.setWritePoint(file.getLength());
                file.setReadPoint(0);
                if (file.getOrderName().equals(userName) || accessCode[File.WRITE_ACCESS]) {
                    do {
                        System.out.println("当前写指针：" + file.getWritePoint());
                        System.out.println("写点什么(输入exit退出)：");
                        String input = scanner.nextLine();
                        if (!"exit".equals(input.toLowerCase(Locale.ROOT))) {
                            file.setWritePoint(file.getWritePoint() + input.length());
                        } else {
                            file.setLength(file.getWritePoint());
                            break;
                        }
                    } while (true);
                } else {
                    System.out.println("你无该文件的读权限");
                }
                return true;
            }
        }
        System.out.println("当前无正在运行的该文件");
        return false;
    }

    public static boolean rename(FileDirectory directory, String fileName, String newFileName) {
        if (null == directory || null == fileName || null == newFileName) {
            System.out.println("参数错误");
            return false;
        }
        return directory.renameFile(fileName, newFileName);
    }

    public static void shared(FileDirectory directory, String fileName, List<File> shareList, boolean[] accessCode) {
        if (null == directory || null == fileName || accessCode.length != 3) {
            System.out.println("参数错误");
            return;
        }
        File file = directory.exit(fileName);
        if (null == file) {
            System.out.println("当前目录文件不存在");
            return;
        }
        file.setShare(Boolean.TRUE);
        file.setAccessCode(accessCode);
        shareList.add(file);
    }

    public static void dismiss(FileDirectory directory, String fileName, List<File> shareMap) {
        if (null == directory || null == fileName || null == shareMap) {
            System.out.println("参数错误");
            return;
        }
        File file = directory.exit(fileName);
        if (null == file) {
            System.out.println("当前目录文件不存在");
            return;
        }
        shareMap.remove(file);
        file.setShare(Boolean.FALSE);
        file.setAccessCode(new boolean[]{true, true, true});
    }
}
