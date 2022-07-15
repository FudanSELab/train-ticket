package food.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.fudan.common.entity.Food;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationFoodStore {

    @Id
    @Column(name = "store_id")
    private String id;

    @NotNull
    private String stationId;

    private String storeName;

    private String telephone;

    private String businessTime;

    private double deliveryFee;

    @ElementCollection(targetClass = Food.class, fetch = FetchType.EAGER)
    @CollectionTable(joinColumns = @JoinColumn(name = "store_id"))
    private List<Food> foodList;

    public StationFoodStore(){
        //Default Constructor
        this.stationId = "";
    }

}
