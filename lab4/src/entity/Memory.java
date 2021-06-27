package entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author GoodBoy
 * @date 2021-06-09
 */
public class Memory {
    private final int maxUser;
    private final int maxRunningFile;
    private final int maxCreatCount;

    /**
     * MFD主目录
     */
    private Map<String, FileDirectory> mfdMap;
    /**
     * AFD运行文件目录
     */
    private List<File> afdList;

    /**
     * 共享文件目录
     */
    private List<File> sharedFileList;

    private long totalSize;
    private long availableSize;

    public Memory(int maxUser, int maxRunningFile, int maxCreatCount, long totalSize) {
        this.maxUser = maxUser;
        this.maxRunningFile = maxRunningFile;
        this.maxCreatCount = maxCreatCount;
        this.totalSize = totalSize;
        mfdMap = new HashMap<>();
        afdList = new LinkedList<>();
        availableSize = totalSize;
        sharedFileList = new LinkedList<>();
    }

    public long getAvailableSize() {
        return availableSize;
    }

    public void setAvailableSize(long availableSize) {
        this.availableSize = availableSize;
    }

    public int getMaxUser() {
        return maxUser;
    }

    public int getMaxRunningFile() {
        return maxRunningFile;
    }

    public Map<String, FileDirectory> getMfdMap() {
        return mfdMap;
    }

    public List<File> getAfdList() {
        return afdList;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public int getMaxCreatCount() {
        return maxCreatCount;
    }

    public List<File> getSharedFileList() {
        return sharedFileList;
    }
}
