package order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
@GenericGenerator(name = "jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
public class OrderAlterInfo {

    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
    private String accountId;

    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
    private String previousOrderId;

    private String loginToken;

    private Order newOrderInfo;

    public OrderAlterInfo(){
        newOrderInfo = new Order();
    }
}
