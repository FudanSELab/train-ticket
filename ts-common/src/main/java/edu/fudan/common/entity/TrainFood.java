package edu.fudan.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class TrainFood implements Serializable{

    public TrainFood(){
        //Default Constructor
    }

    private UUID id;

    private String tripId;

    private List<Food> foodList;

}
