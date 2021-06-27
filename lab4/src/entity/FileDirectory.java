package entity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author GoodBoy
 * @date 2021-06-09
 */
public class FileDirectory {
    private String directoryName;
    private String userName;
    private List<File> fileList;
    private int size;

    public FileDirectory(String directoryName, String userName) {
        this.directoryName = directoryName;
        this.userName = userName;
        this.size = 0;
        fileList = new LinkedList<>();
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public int getSize() {
        return size;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * 添加文件
     *
     * @param file 文件项
     * @return 添加结果
     */
    public boolean saveFile(File file) {
        if (null == file) {
            return false;
        }
        //检查是否有重名
        if (!checkName(file.getFileName())) {
            System.out.println("文件名重复！！");
            return false;
        }
        size++;
        return fileList.add(file);
    }

    /**
     * 根据文件名删除文件
     *
     * @param fileName 文件名
     * @return 删除结果
     */
    public boolean deleteFile(String fileName) {
        if (null == fileName || "".equals(fileName)) {
            System.out.println("文件名不能为空");
            return false;
        }

        Iterator<File> iterator = fileList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getFileName().equals(fileName)) {
                iterator.remove();
                return true;
            }
        }
        System.out.println("查无此文件");
        return false;
    }

    public File exit(String fileName) {
        if (null == fileName || "".equals(fileName)) {
            return null;
        }

        for (File file : fileList) {
            if (file.getFileName().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    public boolean renameFile(String oldFileName, String newFileName) {
        File file = exit(oldFileName);
        if (null == file) {
            System.out.println("文件不存在！！！");
            return false;
        }

        if (checkName(newFileName)) {
            file.setFileName(newFileName);
            return true;
        } else {
            System.out.println("新文件名重复！！！");
            return false;
        }
    }

    public boolean checkName(String fileName) {
        //检查是否有重名
        for (File f : fileList) {
            if (f.getFileName().equals(fileName)) {
                return false;
            }
        }
        return true;
    }
}
