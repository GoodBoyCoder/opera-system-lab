package entity;

import java.util.Arrays;

/**
 * @author GoodBoy
 * @date 2021-06-09
 */
public class File {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 保护码 读-写-执行
     */
    private int[] accessCode;
    /**
     * 文件长度
     */
    private long length;

    /**
     * 读指针
     */
    private long readPoint;
    /**
     * 写指针
     */
    private long writePoint;

    public File(String fileName, int[] accessCode) {
        this.fileName = fileName;
        this.accessCode = accessCode;
        this.length = 0;
        this.readPoint = 0;
        this.writePoint = this.length;
    }

    public File(String fileName) {
        //默认可读可写可执行
        this(fileName, new int[]{1, 1, 1});
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int[] getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(int[] accessCode) {
        this.accessCode = accessCode;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getReadPoint() {
        return readPoint;
    }

    public void setReadPoint(long readPoint) {
        this.readPoint = readPoint;
    }

    public long getWritePoint() {
        return writePoint;
    }

    public void setWritePoint(long writePoint) {
        this.writePoint = writePoint;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", accessCode=" + Arrays.toString(accessCode) +
                ", length=" + length +
                '}';
    }
}
