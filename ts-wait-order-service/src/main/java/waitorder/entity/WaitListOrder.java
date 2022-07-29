package waitorder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Data
@AllArgsConstructor
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
public class WaitListOrder {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
    private String id;

    private String travelDate;
    private String travelTime;
    private String accountId;
    private String contactsName;
    private int contactsDocumentType;
    private String contactsDocumentNumber;
    private String trainNumber;
    private int coachNumber;
    private int seatClass;
    private String seatNumber;
    private String fromStation;
    private String toStation;
    private String price;
    private String waitUtilTime;
    private String createdTime;

    public WaitListOrder(){
        //Default Constructor
    }
}
