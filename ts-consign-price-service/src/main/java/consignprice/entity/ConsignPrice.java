package consignprice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.UUID;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
@Entity
@GenericGenerator(name = "jpa-uuid",strategy="uuid")
public class ConsignPrice {
    @Id
//    private UUID id;
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
    private String id;

    private int index;
    @Column(name = "initial_weight")
    private double initialWeight;
    @Column(name = "initial_price")
    private double initialPrice;
    @Column(name = "within_price")
    private double withinPrice;
    @Column(name = "beyond_price")
    private double beyondPrice;

    public ConsignPrice(){
        //Default Constructor
    }

}
