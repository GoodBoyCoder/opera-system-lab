package entity;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author GoodBoy
 * @date 2021-06-09
 */
public class File {
    public static final int READ_ACCESS = 0;
    public static final int WRITE_ACCESS = 1;
    public static final int EXE_ACCESS = 2;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 保护码 读-写-执行
     */
    private boolean[] accessCode;
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
    /**
     * 文件打开状态 true-打开
     */
    private boolean status;
    /**
     * 文件所有者
     */
    private String orderName;

    /**
     * 是否是共享文件
     */
    private boolean share;

    public File(String fileName, boolean[] accessCode, String userName) {
        this.fileName = fileName;
        this.accessCode = accessCode;
        this.length = 0;
        this.readPoint = 0;
        this.writePoint = this.length;
        this.status = false;
        this.orderName = userName;
        this.share = false;
    }

    public File(String fileName, String userName) {
        //默认可读可写可执行
        this(fileName, new boolean[]{true, true, true}, userName);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean[] getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(boolean[] accessCode) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isShare() {
        return share;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", accessCode=" + Arrays.toString(accessCode) +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof File)) {
            return false;
        }
        File file = (File) o;
        return isStatus() == file.isStatus() && isShare() == file.isShare() && getFileName().equals(file.getFileName()) && Arrays.equals(getAccessCode(), file.getAccessCode()) && getOrderName().equals(file.getOrderName());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getFileName(), isStatus(), getOrderName(), isShare());
        result = 31 * result + Arrays.hashCode(getAccessCode());
        return result;
    }
}
