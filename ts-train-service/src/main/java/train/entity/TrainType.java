package train.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

import javax.persistence.Entity;

import javax.validation.Valid;

@Data
@Entity
public class TrainType {
    @Valid
    @Id
    private String id;

    @Valid
    @Column(name = "economy_class")
    private int economyClass;
    @Valid
    @Column(name = "confort_class")
    private int confortClass;
    @Column(name = "average_speed")
    private int averageSpeed;

    public TrainType(){
        //Default Constructor
    }

    public TrainType(String id, int economyClass, int confortClass) {
        this.id = id;
        this.economyClass = economyClass;
        this.confortClass = confortClass;
    }

    public TrainType(String id, int economyClass, int confortClass, int averageSpeed) {
        this.id = id;
        this.economyClass = economyClass;
        this.confortClass = confortClass;
        this.averageSpeed = averageSpeed;
    }

}
