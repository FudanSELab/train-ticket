package waitorder.entity;

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
public class WaitListOrderVO {
    private String accountId;

    private String contactsId;

    private String tripId;

    private int seatType;

    private String date;

    private String from;

    private String to;

    private String price;


    public Date getDate(){
        return StringUtils.String2Date(date);
    }

    public void setDate(Date date){
        this.date = StringUtils.Date2String(date);
    }


}

