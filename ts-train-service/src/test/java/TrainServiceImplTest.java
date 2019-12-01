import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import train.entity.TrainType;
import train.repository.TrainTypeRepository;
import train.service.TrainServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;



public class TrainServiceImplTest {

    @Mock
    private TrainTypeRepository repository;

    @InjectMocks
    private TrainServiceImpl trainService;

    private TrainType trainTypeA, trainTypeAA, trainTypeB, trainTypeC;
    private HttpHeaders headers;
    private String trainTypeAID, trainTypeBID, trainTypeCID;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        headers = new HttpHeaders();
        trainTypeAID = "123";
        trainTypeBID = "456";
        trainTypeCID = "789";
        trainTypeA = new TrainType(trainTypeAID, 1, 2, 3);
        trainTypeAA = new TrainType(trainTypeAID, 7, 8, 9);
        trainTypeB = new TrainType(trainTypeBID, 4, 5, 6);
        trainTypeC = new TrainType(trainTypeCID, 0, 0, 0);
    }
    @Test
    public void testA() {
        assert trainTypeA.getId() != null;
        assert trainService != null;
        Mockito.when(repository.findById(trainTypeAID)).thenReturn(null);
        assertThat(trainService.create(trainTypeA, headers), is(true));

        Mockito.when(repository.findById(trainTypeAID)).thenReturn(trainTypeA);
        assertThat(trainService.create(trainTypeAA, headers), is(false));

        Mockito.when(repository.findById(trainTypeAID)).thenReturn(trainTypeA);
        assertThat(trainService.retrieve(trainTypeAID, headers), is(trainTypeA));

        Mockito.when(repository.findById(trainTypeBID)).thenReturn(null);
        assertEquals(trainService.retrieve(trainTypeBID, headers), null);

        Mockito.when(repository.findById(trainTypeCID)).thenReturn(null);
        assertThat(trainService.update(trainTypeC, headers), is(false));

        Mockito.when(repository.findById(trainTypeCID)).thenReturn(trainTypeA);
        assertThat(trainService.update(trainTypeAA, headers), is(true));

        Mockito.when(repository.findById(trainTypeAID)).thenReturn(trainTypeAA);
        assertThat(trainService.retrieve(trainTypeAID, headers), is(trainTypeAA));

        List<TrainType> list = new ArrayList<TrainType>();
        list.add(trainTypeAA);
        Mockito.when(repository.findAll()).thenReturn(list);
        assertThat(trainService.query(headers), hasItem(trainTypeAA));

        Mockito.when(repository.findById(trainTypeCID)).thenReturn(null);
        assertThat(trainService.delete(trainTypeCID, headers), is(false));

        Mockito.when(repository.findById(trainTypeAID)).thenReturn(trainTypeAA);
        assertThat(trainService.delete(trainTypeAID, headers), is(true));
    }
}
