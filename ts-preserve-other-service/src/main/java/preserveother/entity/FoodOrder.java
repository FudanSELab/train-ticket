package preserveother.entity;

import java.util.UUID;

/**
 * @author fdu
 */
public class FoodOrder {
    private UUID id;

    private UUID orderId;

    /**
     * 1:train food;2:food store
     */
    private int foodType;

    private String stationName;

    private String storeName;

    private String foodName;

    private double price;

    public FoodOrder() {
        // default constructor
    }

    public FoodOrder(UUID id, UUID orderId, int foodType, String stationName, String storeName, String foodName, double price) {
        this.id = id;
        this.orderId = orderId;
        this.foodType = foodType;
        this.stationName = stationName;
        this.storeName = storeName;
        this.foodName = foodName;
        this.price = price;
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
