package travelto.entity;

/**
 * SeatClass class
 *
 * @author fdu
 * @date 2019/11/10
 */
public enum SeatClass {
    /**
     * NONE 无座
     */
    NONE(0, "NoSeat"),
    /**
     * BUSINESS 商务座
     */
    BUSINESS(1, "GreenSeat"),
    /**
     * FIRSTCLASS 一等座
     */
    FIRSTCLASS(2, "FirstClassSeat"),
    /**
     * SECONDCLASS 二等座
     */
    SECONDCLASS(3, "SecondClassSeat"),
    /**
     * HARDSEAT 硬座
     */
    HARDSEAT(4, "HardSeat"),
    /**
     * SOFTSEAT 软座
     */
    SOFTSEAT(5, "SoftSeat"),
    /**
     * HARDBED 硬卧
     */
    HARDBED(6, "HardBed"),
    /**
     * SOFTBED 软卧
     */
    SOFTBED(7, "SoftBed"),
    /**
     * HIGHSOFTBED 高级软卧
     */
    HIGHSOFTBED(8, "HighSoftSeat");

    private int code;
    private String name;

    SeatClass(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(int code) {
        SeatClass[] seatClassSet = SeatClass.values();
        for (SeatClass seatClass : seatClassSet) {
            if (seatClass.getCode() == code) {
                return seatClass.getName();
            }
        }
        return seatClassSet[0].getName();
    }
}
