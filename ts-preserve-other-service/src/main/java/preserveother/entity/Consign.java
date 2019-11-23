package preserveother.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author fdu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consign {

    private UUID id;
    private UUID orderId;
    private UUID accountId;
    private String handleDate;
    private String targetDate;
    private String from;
    private String to;
    private String consignee;
    private String phone;
    private double weight;
    private boolean isWithin;


}
