package food_delivery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInfo {
    private String orderId;
    private String deliveryTime;

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryInfo that = (DeliveryInfo) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(deliveryTime, that.deliveryTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, deliveryTime);
    }
}
