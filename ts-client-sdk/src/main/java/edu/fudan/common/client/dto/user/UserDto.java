package edu.fudan.common.client.dto.user;
import lombok.*;

/**
 * @author fdse
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private String userId;
    
    private String userName;

    private String password;

    private int gender;

    private int documentType;

    private String documentNum;

    private String email;

    private String cellphone;
}
