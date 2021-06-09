package constant;

/**
 * @author GoodBoy
 * @date 2021-06-05
 */
public enum PartitionStatusEnum {
    /**
     * 分区状态枚举
     */
    FREE("空闲", 1), BUSY("已分配", 2);

    private final String intro;
    private final Integer value;

    PartitionStatusEnum(String intro, Integer value) {
        this.intro = intro;
        this.value = value;
    }

    public String getIntro() {
        return intro;
    }

    public Integer getValue() {
        return value;
    }
}
