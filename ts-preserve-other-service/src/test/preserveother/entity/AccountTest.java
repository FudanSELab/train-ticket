package preserveother.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.PreserveOtherApplication;
import preserveother.entity.Account;
import preserveother.entity.DocumentType;
import preserveother.entity.Gender;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class AccountTest {
    private UUID id;
    private String pw;
    private int gender;
    private String name;
    private int documentType;
    private String documentNum;
    private String email;
    private Account account;

    @Before
    public void setUp() throws Exception {
        id = UUID.randomUUID();
        pw = "123";
        gender = Gender.MALE.getCode();
        name = "xiaoming";
        documentType = DocumentType.PASSPORT.getCode();
        documentNum = "123456";
        email = "10001@qq.com";
        account = new Account();
    }

    @Test
    public void getId() {
        assertNull(account.getId());
    }

    @Test
    public void setId() {
        account.setId(id);
        assertEquals(account.getId(), id);
    }

    @Test
    public void getPassword() {
        assertEquals(account.getPassword(), "defaultPassword");
    }

    @Test
    public void setPassword() {
        account.setPassword(pw);
        assertEquals(account.getPassword(), pw);
    }

    @Test
    public void getGender() {
        assertEquals(account.getGender(), Gender.OTHER.getCode());
    }

    @Test
    public void setGender() {
        account.setGender(gender);
        assertEquals(account.getGender(), gender);
    }

    @Test
    public void getName() {
        assertEquals(account.getName(), "None");
    }

    @Test
    public void setName() {
        account.setName(name);
        assertEquals(account.getName(), name);
    }

    @Test
    public void getDocumentType() {
        assertEquals(account.getDocumentType(), DocumentType.NONE.getCode());
    }

    @Test
    public void setDocumentType() {
        account.setDocumentType(documentType);
        assertEquals(account.getDocumentType(), documentType);
    }

    @Test
    public void getDocumentNum() {
        assertEquals(account.getDocumentNum(), "0123456789");
    }

    @Test
    public void setDocumentNum() {
        account.setDocumentNum(documentNum);
        assertEquals(account.getDocumentNum(), documentNum);
    }

    @Test
    public void getEmail() {
        assertEquals(account.getEmail(), "0123456789");
    }

    @Test
    public void setEmail() {
        account.setEmail(email);
        assertEquals(account.getEmail(), email);
    }
}