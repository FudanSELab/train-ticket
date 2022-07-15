package fdse.microservice.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Locale;
import java.util.UUID;

@Data
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
public class Station {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
    private String id;

    @Valid
    @NotNull
    @Column(unique = true)
    private String name;

    private int stayTime;

    public Station(){
        this.name = "";
    }

    public void setName(String name) {
        this.name = name.replace(" ", "").toLowerCase(Locale.ROOT);
    }

    public Station(String name) {
        this.name = name.replace(" ", "").toLowerCase(Locale.ROOT);
    }


    public Station(String name, int stayTime) {
        this.name = name.replace(" ", "").toLowerCase(Locale.ROOT);;
        this.stayTime = stayTime;
    }

}
