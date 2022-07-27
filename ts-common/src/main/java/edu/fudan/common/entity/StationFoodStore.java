package edu.fudan.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class StationFoodStore implements Serializable{

    private UUID id;

    private String stationId;

    private String storeName;

    private String telephone;

    private String businessTime;

    private double deliveryFee;

    private List<Food> foodList;

    public StationFoodStore(){
        //Default Constructor
    }

}
