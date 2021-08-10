package notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

import lombok.Data;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
@Document(collection = "notification")
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotifyInfo {

    public NotifyInfo(){
        //Default Constructor
    }

    @Id
    private UUID id;

    private Boolean sendStatus;

    private String email;
    private String orderNumber;
    private String username;
    private String startingPlace;
    private String endPlace;
    private String startingTime;
    private String date;
    private String seatClass;
    private String seatNumber;
    private String price;

}
