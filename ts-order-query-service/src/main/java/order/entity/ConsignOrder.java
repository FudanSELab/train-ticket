package order.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Persistent Consign entity mapped to the ts-consign-mysql schema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsignOrder {

  @Id
  private String id;

  private String orderId;

  private String userId;

  private String handleDate;
  private String targetDate;

  private String from;

  private String to;

  private String consignee;

  private String phone;

  private double weight;

  private double price;

  @Transient
  private boolean within;
}
