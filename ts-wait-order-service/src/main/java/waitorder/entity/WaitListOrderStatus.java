package waitorder.entity;

import edu.fudan.common.entity.OrderStatus;

public enum WaitListOrderStatus {
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
     * cancel
     */
    CANCEL    (3,"Cancel"),
    /**
     * refunded
     */
    REFUNDS   (4,"Refunded"),
    /**
     * expired
     */
    EXPIRED   (5, "Expired");



    private int code;
    private String name;

    WaitListOrderStatus(int code, String name){
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
