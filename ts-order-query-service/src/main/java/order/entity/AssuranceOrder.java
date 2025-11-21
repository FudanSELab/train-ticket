package order.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Assurance JPA entity.
 */
@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssuranceOrder {

    @Id
    private String id;

    /**
     * Order identifier the assurance relates to.
     */
    @NotNull
    private String orderId;

    /**
     * Assurance type.
     */
    @Enumerated(EnumType.STRING)
    private AssuranceType type;

    public AssuranceOrder() {
        this.orderId = UUID.randomUUID().toString();
    }

    public AssuranceOrder(String id, String orderId, AssuranceType type) {
        this.id = id;
        this.orderId = orderId;
        this.type = type;
    }
}
