package waitorder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
    private Date travelTime;
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
    private Date waitUtilTime;
    private Date createdTime;

    public WaitListOrder(){
        //Default Constructor
    }
}
