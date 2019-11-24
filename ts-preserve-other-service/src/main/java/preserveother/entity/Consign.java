package preserveother.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author fdu
 */
@AllArgsConstructor
@NoArgsConstructor
public class Consign {

    private UUID id;
    private UUID orderId;
    private UUID accountId;
    private String handleDate;
    private String targetDate;
    private String from;
    private String to;
    private String consignee;
    private String phone;
    private double weight;
    private boolean isWithin;

    public UUID getId() {
        return id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getConsignee() {
        return consignee;
    }

    public String getPhone() {
        return phone;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isWithin() {
        return isWithin;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setWithin(boolean within) {
        isWithin = within;
    }
}
