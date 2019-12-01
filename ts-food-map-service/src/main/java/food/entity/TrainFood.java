package food.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Document(collection = "trainfoods")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainFood{

    @Id
    private UUID id;

    @NotNull
    private String tripId;

    private List<Food> foodList;
	
    public TrainFood(){
		// do nothing
		this.tripId = " " ;
    }

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
