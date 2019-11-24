package preserveother.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author fdu
 */
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UUID userId;
    private String userName;
    private String password;
    private int gender;
    private int documentType;
    private String documentNum;
    private String email;

    public UUID getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getGender() {
        return gender;
    }

    public int getDocumentType() {
        return documentType;
    }

    public String getDocumentNum() {
        return documentNum;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public void setDocumentNum(String documentNum) {
        this.documentNum = documentNum;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
