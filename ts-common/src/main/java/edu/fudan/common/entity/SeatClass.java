package edu.fudan.common.entity;

/**
 * @author fdse
 */
public enum SeatClass {

    /**
     * no seat
     */
    NONE        (0,"NoSeat"),
    /**
     * green seat
     */
    BUSINESS    (1,"GreenSeat"),
    /**
     * first class seat
     */
    FIRSTCLASS  (2,"FirstClassSeat"),
    /**
     * second class seat
     */
    SECONDCLASS (3,"SecondClassSeat"),
    /**
     * hard seat
     */
    HARDSEAT    (4,"HardSeat"),
    /**
     * soft seat
     */
    SOFTSEAT    (5,"SoftSeat"),
    /**
     * hard bed
     */
    HARDBED     (6,"HardBed"),
    /**
     * soft bed
     */
    SOFTBED     (7,"SoftBed"),
    /**
     * high soft seat
     */
    HIGHSOFTBED (8,"HighSoftSeat");

    private int code;
    private String name;

    SeatClass(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode(){
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(int code){
        SeatClass[] seatClassSet = SeatClass.values();
        for(SeatClass seatClass : seatClassSet){
            if(seatClass.getCode() == code){
                return seatClass.getName();
            }
        }
        return seatClassSet[0].getName();
    }
}
