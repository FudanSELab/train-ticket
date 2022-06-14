package travel2.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author fdse
 */
@Data
@Embeddable
public class TripId implements Serializable{
    private Type type;
    private String number;


    public TripId(){
        //Default Constructor
    }

    public TripId(String trainNumber){
        char type0 = trainNumber.charAt(0);
        switch(type0){
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

    @Override
    public String toString(){
        return type.getName() + number;
    }
}
