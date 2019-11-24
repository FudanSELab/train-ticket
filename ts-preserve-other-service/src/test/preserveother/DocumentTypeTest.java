package preserveother;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import preserveother.entity.DocumentType;

import static org.junit.Assert.*;

/**
 * @author lfliu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PreserveOtherApplication.class)
public class DocumentTypeTest {
    private DocumentType noneType;
    private DocumentType idCardType;
    private DocumentType passportType;
    private DocumentType otherType;

    @Before
    public void setUp() {
        noneType = DocumentType.NONE;
        idCardType = DocumentType.ID_CARD;
        passportType = DocumentType.PASSPORT;
        otherType = DocumentType.OTHER;
    }

    @Test
    public void getCode() {
        assertEquals(noneType.getCode(), 0);
        assertEquals(idCardType.getCode(), 1);
        assertEquals(passportType.getCode(), 2);
        assertEquals(otherType.getCode(), 3);
    }

    @Test
    public void getName() {
        assertEquals(noneType.getName(), "Null");
        assertEquals(idCardType.getName(), "ID Card");
        assertEquals(passportType.getName(), "Passport");
        assertEquals(otherType.getName(), "Other");
    }

    @Test
    public void getNameByCode() {
        assertEquals(DocumentType.getNameByCode(0), noneType.getName());
        assertEquals(DocumentType.getNameByCode(1), idCardType.getName());
        assertEquals(DocumentType.getNameByCode(2), passportType.getName());
        assertEquals(DocumentType.getNameByCode(3), otherType.getName());
        assertEquals(DocumentType.getNameByCode(4), noneType.getName());
    }
}