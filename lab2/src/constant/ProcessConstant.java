package constant;

/**
 * @author GoodBoy
 * @date 2021-05-26
 */
public class ProcessConstant {
    /**
     * 等待状态
     */
    public static final Byte WAIT = 0;
    /**
     * 执行状态
     */
    public static final Byte RUNNING = 1;
    /**
     * 完成状态
     */
    public static final Byte FINISHED = 2;
    /**
     * 就绪状态
     */
    public static final Byte READY = 3;

    public static String convert(Byte status) {
        switch (status) {
            case 0:
                return "等待中";
            case 1:
                return "执行中";
            case 2:
                return "已完成";
            case 3:
                return "已就绪";
            default:
                throw new IllegalArgumentException("参数不合法");
        }
    }
}
