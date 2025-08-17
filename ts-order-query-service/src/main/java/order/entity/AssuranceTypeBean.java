package order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Lightweight DTO for AssuranceType.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssuranceTypeBean implements Serializable {

    private int index;
    private String name;
    private double price;
}
