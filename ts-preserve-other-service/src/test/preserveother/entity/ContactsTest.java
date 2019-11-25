package preserveother.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.PreserveOtherApplication;
import preserveother.entity.Contacts;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class ContactsTest {

    private UUID testId;
    private UUID testAccountId;
    private String testName;
    private int testDocumentType;
    private String testDocumentNumber;
    private String testPhoneNumber;
    private Contacts testContacts;

    @Before
    public void setUp() {
        testId = UUID.randomUUID();
        testAccountId = UUID.randomUUID();
        testName = "testContacts";
        testDocumentType = 1;
        testDocumentNumber = "1";
        testPhoneNumber = "10086";
        testContacts = new Contacts(testId, testAccountId, testName, testDocumentType, testDocumentNumber, testPhoneNumber);
    }

    @Test
    public void getId() {
        assertEquals(testContacts.getId(), testId);
    }

    @Test
    public void setId() {
        UUID newId = UUID.randomUUID();
        testContacts.setId(newId);
        assertEquals(testContacts.getId(), newId);
    }

    @Test
    public void getAccountId() {
        assertEquals(testContacts.getAccountId(), testAccountId);
    }

    @Test
    public void setAccountId() {
        UUID newId = UUID.randomUUID();
        testContacts.setAccountId(newId);
        assertEquals(testContacts.getAccountId(), newId);
    }

    @Test
    public void getName() {
        assertEquals(testContacts.getName(), testName);
    }

    @Test
    public void setName() {
        String newName = "newContacts";
        testContacts.setName(newName);
        assertEquals(testContacts.getName(), newName);
    }

    @Test
    public void getDocumentType() {
        assertEquals(testContacts.getDocumentType(), testDocumentType);
    }

    @Test
    public void setDocumentType() {
        int newDocumentType = 2;
        testContacts.setDocumentType(newDocumentType);
        assertEquals(testContacts.getDocumentType(), newDocumentType);
    }

    @Test
    public void getDocumentNumber() {
        assertEquals(testContacts.getDocumentNumber(), testDocumentNumber);
    }

    @Test
    public void setDocumentNumber() {
        String newDocumentNumber = "2";
        testContacts.setDocumentNumber(newDocumentNumber);
        assertEquals(testContacts.getDocumentNumber(), newDocumentNumber);
    }

    @Test
    public void getPhoneNumber() {
        assertEquals(testContacts.getPhoneNumber(), testPhoneNumber);
    }

    @Test
    public void setPhoneNumber() {
        String newPhoneNumber = "1008611";
        testContacts.setPhoneNumber(newPhoneNumber);
        assertEquals(testContacts.getPhoneNumber(), newPhoneNumber);
    }

    @Test
    public void testEquals() {
        Contacts contacts = new Contacts(testId, testAccountId, testName, testDocumentType, testDocumentNumber, testPhoneNumber);
        Contacts contactsCopy = new Contacts(UUID.randomUUID(), testAccountId, testName, testDocumentType, testDocumentNumber, testPhoneNumber);
        assertTrue(contacts.equals(contactsCopy));
        assertTrue(contacts.equals(contacts));
        assertFalse(contacts.equals(null));
        assertFalse(contacts.equals(testId));
    }

    @Test
    public void testHashCode() {
        assertNotEquals(testContacts.hashCode(), 0);
    }

    @Test
    public void testDefault() {
        assertNull(new Contacts().getPhoneNumber());
    }
}