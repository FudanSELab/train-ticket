package travel.entity;

import com.sun.xml.internal.ws.developer.SchemaValidation;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author cns
 */
@Data
@AllArgsConstructor
public class PassengerInfo {

    @Valid
    @NotNull
    private String name;

    @Valid
    @NotNull
    private String email;

    public PassengerInfo(){
        //Default Constructor
        this.name = "";
        this.email = "";
    }

}
