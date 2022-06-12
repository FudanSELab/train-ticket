package consign.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(schema = "ts-consign-mysql")
public class ConsignRecord {

    @Id
    @Column(name = "consign_record_id")
    private String id;
    private String orderId;
    @Column(name = "user_id")
    private String accountId;
    private String handleDate;
    private String targetDate;
    @Column(name = "from_place")
    private String from;
    @Column(name = "to_place")
    private String to;
    private String consignee;
    @Column(name = "consign_record_phone")
    private String phone;
    private double weight;
    @Column(name = "consign_record_price")
    private double price;

}
