package edu.fudan.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author fdse
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Route {
    private String id;

    private List<String> stations;

    private List<Integer> distances;

    private String startStationName;

    private String terminalStationName;

    private String startStationId;

    private String terminalStationId;

    public Route(List<String> stations, List<Integer> distances, String startStationName, String terminalStationName) {
        this.stations = stations;
        this.distances = distances;
        this.startStationName = startStationName;
        this.terminalStationName = terminalStationName;
    }

}