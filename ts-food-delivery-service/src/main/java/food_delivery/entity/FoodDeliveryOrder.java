package food_delivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import edu.fudan.common.entity.Food;

@Data
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodDeliveryOrder {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
    private String id;

    private String stationFoodStoreId;

    @ElementCollection(targetClass = Food.class)
    private List<Food> foodList;

    private String tripId;

    private int seatNo;

    private String createdTime;

    private String deliveryTime;

    private double deliveryFee;

    public FoodDeliveryOrder() {
        // Default constructor
    }

    public FoodDeliveryOrder(String id, String stationFoodStoreId, List<Food> foodList, 
                           String tripId, int seatNo, String createdTime, 
                           String deliveryTime, double deliveryFee) {
        this.id = id;
        this.stationFoodStoreId = stationFoodStoreId;
        this.foodList = foodList;
        this.tripId = tripId;
        this.seatNo = seatNo;
        this.createdTime = createdTime;
        this.deliveryTime = deliveryTime;
        this.deliveryFee = deliveryFee;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationFoodStoreId() {
        return stationFoodStoreId;
    }

    public void setStationFoodStoreId(String stationFoodStoreId) {
        this.stationFoodStoreId = stationFoodStoreId;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodDeliveryOrder that = (FoodDeliveryOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
