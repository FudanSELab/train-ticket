package travel.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author fdse
 */
@Data
@Embeddable
public class TripId implements Serializable {
    private Type type;
    private String number;

    public TripId(Type type, String number) {
        this.type = type;
        this.number = number;
    }

    public TripId() {
        //Default Constructor
    }

    public TripId(String trainNumber) {
        char type0 = trainNumber.charAt(0);
        switch (type0) {
            case 'G':
                this.type = Type.G;
                break;
            case 'D':
                this.type = Type.D;
                break;
            default:
                break;
        }

        this.number = trainNumber.substring(1);
    }

    @Override
    public String toString() {
        return type.getName() + number;
    }
}