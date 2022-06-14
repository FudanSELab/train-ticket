package travel2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author fdse
 */
@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(schema = "ts-travel2-mysql")
public class Route {

    @Id
    @Column(name = "route_id")
    private String id;

    @ElementCollection
    @CollectionTable(joinColumns = {@JoinColumn(name = "route_id")})
    private List<String> stations;

    @ElementCollection
    @CollectionTable(joinColumns = {@JoinColumn(name = "route_id")})
    private List<Integer> distances;

    @Column(name = "starting_station_id")
    private String startStationId;

    private String terminalStationId;

    public Route() {
        //Default Constructor
    }

}