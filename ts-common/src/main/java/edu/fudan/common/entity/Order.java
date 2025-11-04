package edu.fudan.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.fudan.common.entity.SeatClass;
import edu.fudan.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String id;

    private String boughtDate;

    private String travelDate;

    private String travelTime;

    /**
     * Which Account Bought it
     */
    private String accountId;

    /**
     * Tickets bought for whom....
     */
    private String contactsName;

    private int documentType;

    private String contactsDocumentNumber;

    private String trainNumber;

    private int coachNumber;

    private int seatClass;

    private String seatNumber;

    private String from;

    private String to;

    private int status;

    private String price;

    private String differenceMoney;

    public void initOrder(){
        boughtDate = StringUtils.Date2String(new Date(System.currentTimeMillis()));
        travelDate = StringUtils.Date2String(new Date(123456789));
        trainNumber = "G1235";
        coachNumber = 5;
        seatClass = SeatClass.FIRSTCLASS.getCode();
        seatNumber = "5A";
        from = "shanghai";
        to = "taiyuan";
        status = OrderStatus.PAID.getCode();
        price = "0.0";
        differenceMoney ="0.0";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Order other = (Order) obj;
        return boughtDate.equals(other.boughtDate)
                && travelDate.equals(other.travelDate)
                && travelTime.equals(other.travelTime)
                && accountId .equals( other.accountId )
                && contactsName.equals(other.contactsName)
                && contactsDocumentNumber.equals(other.contactsDocumentNumber)
                && documentType == other.documentType
                && trainNumber.equals(other.trainNumber)
                && coachNumber == other.coachNumber
                && seatClass == other.seatClass
                && seatNumber .equals(other.seatNumber)
                && from.equals(other.from)
                && to.equals(other.to)
                && status == other.status
                && price.equals(other.price);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (id == null ? 0 : id.hashCode());
        return result;
    }

}
