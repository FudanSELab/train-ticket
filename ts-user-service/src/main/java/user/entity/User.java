package user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@GenericGenerator(name = "jpa-uuid",strategy="uuid")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //    private UUID userId;
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
    private String userId;
    private String userName;
    private String password;

    private int gender;

    private int documentType;

    private String documentNum;

    private String email;

}
