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
    @Column(length = 36, name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    private String password;

    private int gender;
    @Column(name = "document_type")
    private int documentType;
    @Column(name = "document_num")
    private String documentNum;

    private String email;

}
