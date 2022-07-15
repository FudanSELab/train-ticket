package edu.fudan.common.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class Food implements Serializable{

    private String foodName;
    private double price;
    public Food(){
        //Default Constructor
    }

}
