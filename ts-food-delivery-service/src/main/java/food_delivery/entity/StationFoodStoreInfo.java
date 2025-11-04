package food_delivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import edu.fudan.common.entity.Food;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationFoodStoreInfo {

    private String id;
    private String stationId;
    private String storeName;
    private String telephone;
    private String businessTime;
    private double deliveryFee;
    private List<Food> foodList;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationFoodStoreInfo that = (StationFoodStoreInfo) o;
        return Double.compare(that.deliveryFee, deliveryFee) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(stationId, that.stationId) &&
                Objects.equals(storeName, that.storeName) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(businessTime, that.businessTime) &&
                Objects.equals(foodList, that.foodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stationId, storeName, telephone, businessTime, deliveryFee, foodList);
    }
}
