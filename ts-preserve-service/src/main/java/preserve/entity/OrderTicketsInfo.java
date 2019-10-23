package preserve.entity;

import java.util.Date;

public class OrderTicketsInfo extends FoodOrderBase{
    private String accountId;

    private String contactsId;

    private String tripId;

    private int seatType;

    private Date date;

    private String from;

    private String to;

    private int assurance;

    //food

    private double foodPrice;

    //consign
    private String handleDate;

    private String consigneeName = "";

    private String consigneePhone = "";

    private double consigneeWeight;

    private boolean isWithin;


    public OrderTicketsInfo() {
        //Default Constructor
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public double getConsigneeWeight() {
        return consigneeWeight;
    }

    public void setConsigneeWeight(double consigneeWeight) {
        this.consigneeWeight = consigneeWeight;
    }

    public boolean isWithin() {
        return isWithin;
    }

    public void setWithin(boolean within) {
        isWithin = within;
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public int getSeatType() {
        return seatType;
    }

    public void setSeatType(int seatType) {
        this.seatType = seatType;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getAssurance() {
        return assurance;
    }

    public void setAssurance(int assurance) {
        this.assurance = assurance;
    }
}
