package waitorder.entity;

import edu.fudan.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;


@Data
@AllArgsConstructor
@Entity
@GenericGenerator(name = "jpa-uuid", strategy ="uuid")
public class WaitListOrder {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
    private String id;

//    private String travelDate;
    private String travelTime;

    @Column(length = 36)
    private String accountId;
    private String contactsId;
    private String contactsName;
    private int contactsDocumentType;
    private String contactsDocumentNumber;
    private String trainNumber;
    private int seatType;

    @Column(name = "from_station")
    private String from;
    @Column(name = "to_station")
    private String to;

    private String price;
    private String waitUtilTime;
    private String createdTime;
    private int status;


    public WaitListOrder(){
        createdTime = StringUtils.Date2String(new Date(System.currentTimeMillis()));
//        trainNumber = "G1235";
//        seatType = SeatClass.FIRSTCLASS.getCode();
//        from = "shanghai";
//        to = "taiyuan";
//        price = "0.0";

        //wait until 24 hours later
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        c.add(Calendar.DAY_OF_MONTH,1);
        waitUtilTime = StringUtils.Date2String(c.getTime());
        travelTime=StringUtils.Date2String(c.getTime());
        status= WaitListOrderStatus.NOTPAID.getCode();
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        WaitListOrder that = (WaitListOrder) o;
//        return contactsDocumentType == that.contactsDocumentType
//                && coachNumber == that.coachNumber
//                && seatClass == that.seatClass
//                && id.equals(that.id)
//                && Objects.equals(travelTime, that.travelTime)
//                && Objects.equals(accountId, that.accountId)
//                && Objects.equals(contactsName, that.contactsName)
//                && Objects.equals(contactsDocumentNumber, that.contactsDocumentNumber)
//                && Objects.equals(trainNumber, that.trainNumber)
//                && Objects.equals(seatNumber, that.seatNumber)
//                && Objects.equals(fromStation, that.fromStation)
//                && Objects.equals(toStation, that.toStation)
//                && Objects.equals(price, that.price);
//    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (id == null ? 0 : id.hashCode());
        return result;
    }

    public Date getCreatedTime(){ return StringUtils.String2Date(createdTime); }

    public Date getTravelTime(){ return StringUtils.String2Date(createdTime); }

    public Date getWaitUtilTime(){ return StringUtils.String2Date(waitUtilTime); }

    public void setCreatedTime(Date createdTime){
        this.createdTime = StringUtils.Date2String(createdTime);
    }

    public void setTravelTime(Date travelTime){ this.createdTime = StringUtils.Date2String(travelTime); }

    public void setWaitUntilTime(Date waitUntilTime){ this.waitUtilTime=StringUtils.Date2String(waitUntilTime);}



}
