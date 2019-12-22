package travelto.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;

/**
 * TrainType class
 *
 * @author fdu
 * @date 2019/11/10
 */
@Document(collection = "trainType")
public class TrainType {
    @Valid
    @Id
    private String id;

    @Valid
    private int economyClass;
    @Valid
    private int confortClass;

    private int averageSpeed;

    public TrainType(){
        //Default Constructor
    }

    public TrainType(String id) {
        int economyCnt = Integer.MAX_VALUE;
        int confortCnt = Integer.MAX_VALUE;
        if (id.startsWith("G")) {
            this.id = "GaoTieOne";
            this.economyClass = economyCnt;
            this.confortClass = confortCnt;
            this.averageSpeed = 250;
        } else if (id.startsWith("D")) {
            this.id = "DongCheOne";
            this.economyClass = economyCnt;
            this.confortClass = confortCnt;
            this.averageSpeed = 180;
        } else if (id.startsWith("Z")) {
            this.id = "ZhiDa";
            this.economyClass = economyCnt;
            this.confortClass = confortCnt;
            this.averageSpeed = 120;
        } else if (id.startsWith("T")) {
            this.id = "TeKuai";
            this.economyClass = economyCnt;
            this.confortClass = confortCnt;
            this.averageSpeed = 120;
        } else if (id.startsWith("K")) {
            this.id = "KuaiSu";
            this.economyClass = economyCnt;
            this.confortClass = confortCnt;
            this.averageSpeed = 90;
        }
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(int economyClass) {
        this.economyClass = economyClass;
    }

    public int getConfortClass() {
        return confortClass;
    }

    public void setConfortClass(int confortClass) {
        this.confortClass = confortClass;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
