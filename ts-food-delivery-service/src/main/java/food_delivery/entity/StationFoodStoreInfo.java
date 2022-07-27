package food_delivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

}
