package foodsearch.entity;

import edu.fudan.common.entity.Food;
import edu.fudan.common.entity.StationFoodStore;
import edu.fudan.common.entity.TrainFood;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AllTripFood {

    private List<Food> trainFoodList;

    private Map<String, List<StationFoodStore>>  foodStoreListMap;

    public AllTripFood(){
        //Default Constructor
    }

}
