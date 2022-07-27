package edu.fudan.common.entity;

/**
 * @author fdse
 */
public enum OrderStatus {

    /**
     * not paid
     */
    NOTPAID   (0,"Not Paid"),
    /**
     * paid and not collected
     */
    PAID      (1,"Paid & Not Collected"),
    /**
     * collected
     */
    COLLECTED (2,"Collected"),
    /**
     * cancel and rebook
     */
    CHANGE    (3,"Cancel & Rebook"),
    /**
     * cancel
     */
    CANCEL    (4,"Cancel"),
    /**
     * refunded
     */
    REFUNDS   (5,"Refunded"),
    /**
     * used
     */
    USED      (6,"Used");

    private int code;
    private String name;

    OrderStatus(int code, String name){
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
        OrderStatus[] orderStatusSet = OrderStatus.values();
        for(OrderStatus orderStatus : orderStatusSet){
            if(orderStatus.getCode() == code){
                return orderStatus.getName();
            }
        }
        return orderStatusSet[0].getName();
    }

}
