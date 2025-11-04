package edu.fudan.common.entity;

import java.util.UUID;

/**
 * @author fdse
 */
public class FoodOrder {

    private String id;

    private String orderId;

    /**
     * 1:train food;2:food store
     */
    private int foodType;

    private String stationName;

    private String storeName;

    private String foodName;

    private double price;

    public FoodOrder(){
        //Default Constructor
    }

    public FoodOrder(String id, String orderId, int foodType, String stationName, 
                    String storeName, String foodName, double price) {
        this.id = id;
        this.orderId = orderId;
        this.foodType = foodType;
        this.stationName = stationName;
        this.storeName = storeName;
        this.foodName = foodName;
        this.price = price;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getFoodType() {
        return foodType;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
