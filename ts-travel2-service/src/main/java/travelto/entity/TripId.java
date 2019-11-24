package travelto.entity;

import java.io.Serializable;

/**
 * TripId class
 *
 * @author fdu
 * @date 2019/11/10
 */
public class TripId implements Serializable{
    private Type type;
    private String number;


    public TripId(){
        //Default Constructor
    }

    public TripId(String trainNumber){
        char typeChar = trainNumber.charAt(0);
        switch(typeChar){
            case 'Z': this.type = Type.Z;
                break;
            case 'T': this.type = Type.T;
                break;
            case 'K': this.type = Type.K;
                break;
            default:break;
        }

        this.number = trainNumber.substring(1);
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString(){
        return type.getName() + number;
    }
}
