package food_delivery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfo {
    private String orderId;
    private int seatNo;

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatInfo seatInfo = (SeatInfo) o;
        return seatNo == seatInfo.seatNo &&
                Objects.equals(orderId, seatInfo.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, seatNo);
    }
}