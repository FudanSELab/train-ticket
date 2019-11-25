package preserveother.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.PreserveOtherApplication;
import preserveother.entity.User;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class UserTest {
    private UUID userId;
    private String userName;
    private String password;
    private int gender;
    private int documentType;
    private String documentNum;
    private String email;
    private User user;

    @Before
    public void setUp() {
        userId = UUID.randomUUID();
        userName = "xiaoming";
        password = "123";
        gender = 1;
        documentType = 1;
        documentNum = "111";
        email = "10010@qq.com";
        user = new User(userId, userName, password, gender, documentType, documentNum, email);
    }

    @Test
    public void getUserId() {
        assertEquals(user.getUserId(), userId);
    }

    @Test
    public void getUserName() {
        assertEquals(user.getUserName(), userName);
    }

    @Test
    public void getPassword() {
        assertEquals(user.getPassword(), password);
    }

    @Test
    public void getGender() {
        assertEquals(user.getGender(), gender);
    }

    @Test
    public void getDocumentType() {
        assertEquals(user.getDocumentType(), documentType);
    }

    @Test
    public void getDocumentNum() {
        assertEquals(user.getDocumentNum(), documentNum);
    }

    @Test
    public void getEmail() {
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void setUserId() {
        UUID newUserId = UUID.randomUUID();
        user.setUserId(newUserId);
        assertEquals(user.getUserId(), newUserId);
    }

    @Test
    public void setUserName() {
        String newUserName = "lihua";
        user.setUserName(newUserName);
    }

    @Test
    public void setPassword() {
        String newPw = "234";
        user.setPassword(newPw);
        assertEquals(user.getPassword(), newPw);
    }

    @Test
    public void setGender() {
        int newGender = 2;
        user.setGender(newGender);
        assertEquals(user.getGender(), newGender);
    }

    @Test
    public void setDocumentType() {
        int newDocumentType = 2;
        user.setDocumentType(newDocumentType);
        assertEquals(user.getDocumentType(), newDocumentType);
    }

    @Test
    public void setDocumentNum() {
        String newDocumentNum = "222";
        user.setDocumentNum(newDocumentNum);
        assertEquals(user.getDocumentNum(), newDocumentNum);
    }

    @Test
    public void setEmail() {
        String newEmail = "1@qq.com";
        user.setEmail(newEmail);
        assertEquals(user.getEmail(), newEmail);
    }

    @Test
    public void testDefault() {
        assertNull(new User().getEmail());
    }
}