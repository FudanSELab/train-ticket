package edu.fudan.common.entity;

import edu.fudan.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author fdse
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderTicketsInfo {
    private String accountId;
    private String contactsId;

    private String tripId;

    private int seatType;

    private String loginToken;

    private String date;

    private String from;

    private String to;
    private int assurance;

    private int foodType = 0;

    private String stationName;

    private String storeName;

    private String foodName;

    private double foodPrice;


    private String handleDate;

    private String consigneeName;

    private String consigneePhone;

    private double consigneeWeight;

    private boolean isWithin;

    public String getFrom() {
        return StringUtils.String2Lower(this.from);
    }

    public String getTo() {
        return StringUtils.String2Lower(this.to);
    }

}
