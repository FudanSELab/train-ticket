package fdse.microservice.controller;

public class StationDTO {
    String id;
    String name;
    int stayTime;

    public StationDTO(){}

    public StationDTO(String id, String name, int stayTime) {
        this.id = id;
        this.name = name;
        this.stayTime = stayTime;
    }
}
