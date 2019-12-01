package foodsearch.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


public class TrainFood implements Serializable{

    public TrainFood(){
        //do nothing
    }

    private UUID id;

    private String tripId;

    private List<Food> foodList;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }


    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

}
