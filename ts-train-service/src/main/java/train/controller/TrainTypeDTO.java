package train.controller;

public class TrainTypeDTO {
    String id;
    int economyClass;
    int confortClass;
    int averageSpeed;
    public TrainTypeDTO () {}
    public TrainTypeDTO(String id, int economyClass, int confortClass, int averageSpeed) {
        this.id = id;
        this.economyClass = economyClass;
        this.confortClass = confortClass;
        this.averageSpeed = averageSpeed;
    }
}