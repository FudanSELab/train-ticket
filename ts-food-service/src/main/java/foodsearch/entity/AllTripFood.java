package foodsearch.entity;

import java.util.List;
import java.util.Map;

public class AllTripFood {

    private List<TrainFood> trainFoodList;

    private Map<String, List<FoodStore>>  foodStoreListMap;

    public AllTripFood(){
        //do nothing
    }
    public List<TrainFood> getTrainFoodList() {
        return trainFoodList;
    }

    public void setTrainFoodList(List<TrainFood> trainFoodList) {
        this.trainFoodList = trainFoodList;
    }

    public Map<String, List<FoodStore>> getFoodStoreListMap() {
        return foodStoreListMap;
    }

    public void setFoodStoreListMap(Map<String, List<FoodStore>> foodStoreListMap) {
        this.foodStoreListMap = foodStoreListMap;
    }

}
