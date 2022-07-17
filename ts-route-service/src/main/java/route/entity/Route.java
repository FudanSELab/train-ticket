package route.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@GenericGenerator(name = "jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
public class Route {

    @Id
    @Column(length = 36)
    private String id;

    @ElementCollection(targetClass = String.class)
    private List<String> stations;

    @ElementCollection(targetClass = Integer.class)
    private List<Integer> distances;

    private String startStationId;

    private String terminalStationId;

    private String startStationName;

    private String terminalStationName;

    public Route(){
        this.id = UUID.randomUUID().toString();
    }

    public Route(String id, List<String> stations, List<Integer> distances, String startStationName, String terminalStationName) {
        this.id = id;
        this.stations = stations;
        this.distances = distances;
        this.startStationName = startStationName;
        this.terminalStationName = terminalStationName;
    }

    public Route(List<String> stations, List<Integer> distances, String startStationName, String terminalStationName) {
        this.id = UUID.randomUUID().toString();
        this.stations = stations;
        this.distances = distances;
        this.startStationName = startStationName;
        this.terminalStationName = terminalStationName;
    }
}
