package edu.fudan.common.entity;

import lombok.Data;

/**
 * @author fdse
 */
@Data
public class FoodOrder {

    private String id;

    private String orderId;

    /**
     * 1:train food;2:food store
     */
    private int foodType;

    private String stationName;

    private String storeName;

    private String foodName;

    private double price;

    public FoodOrder(){
        //Default Constructor
    }

}
