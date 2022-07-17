package edu.fudan.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {
    private String id;

    private List<String> stations;

    private List<Integer> distances;

    private String startStationName;

    private String terminalStationName;

    private String startStationId;

    private String terminalStationId;

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