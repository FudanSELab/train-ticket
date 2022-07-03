package inside_payment.entity;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Entity;
//import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@Entity
public class Money {

    @Valid
    @NotNull
    @Id
    private String id;

    @Valid
    @NotNull
    private String userId;

    @Valid
    @NotNull
    private String money; //NOSONAR

    @Valid
    @NotNull
    @Enumerated(EnumType.STRING)
    private MoneyType type;

    public Money(){
        this.id = UUID.randomUUID().toString();
        this.userId = "";
        this.money = "";

    }

}
