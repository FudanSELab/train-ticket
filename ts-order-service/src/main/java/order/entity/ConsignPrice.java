package order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Entity representing consign price configuration.
 */
@Data
@AllArgsConstructor
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
public class ConsignPrice {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
    private String id;

    /**
     * Configuration index – always 0 for the single row that holds the config.
     */
    @Column(name = "index", unique = true)
    private int index;

    private double initialWeight;

    private double initialPrice;

    private double withinPrice;

    private double beyondPrice;

    public ConsignPrice() {
        // Default constructor for JPA
    }
}
