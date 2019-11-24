package preserveother.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author fdu
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Assurance {

    private UUID id;

    @NotNull
    /**
     * which order the assurance is related to
     */
    private UUID orderId;

    @NotNull
    /**
     * the type of assurance
     */
    private AssuranceType type;

    public Assurance(UUID id, UUID orderId, AssuranceType type) {
        this.id = id;
        this.orderId = orderId;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public AssuranceType getType() {
        return type;
    }

    public void setType(AssuranceType type) {
        this.type = type;
    }

}
