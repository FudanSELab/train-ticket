package order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Plain assurance view object (no JPA annotations).
 */
@Data
@AllArgsConstructor
public class PlainAssurance implements Serializable {
    private String id;
    private String orderId;
    private int typeIndex;
    private String typeName;
    private double typePrice;

    public PlainAssurance() {}
}
