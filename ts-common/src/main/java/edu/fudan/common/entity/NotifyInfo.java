package edu.fudan.common.entity;

import lombok.Data;

/**
 * @author fdse
 */
@Data
public class NotifyInfo {

    public NotifyInfo(){
        //Default Constructor
    }

    private String email;
    private String orderNumber;
    private String username;
    private String startPlace;
    private String endPlace;
    private String startTime;
    private String date;
    private String seatClass;
    private String seatNumber;
    private String price;

}
