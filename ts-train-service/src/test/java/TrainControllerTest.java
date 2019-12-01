import edu.fudan.common.util.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import train.controller.TrainController;
import train.controller.TrainTypeDTO;
import train.entity.TrainType;
import train.service.TrainService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.ResponseEntity.ok;

public class TrainControllerTest {
    @Mock
    private TrainService trainService;

    @InjectMocks
    private TrainController trainController;

    private String id;
    private HttpHeaders httpHeaders;
    private TrainTypeDTO DTOA;
    private TrainType A;
    private List<TrainType> LA;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        httpHeaders = new HttpHeaders();
        id = "123";
        DTOA = new TrainTypeDTO();
        DTOA = new TrainTypeDTO(id, 2, 3, 1);
        A = new TrainType(id, 2, 3, 1);
        LA = new ArrayList<TrainType>();
    }

    @Test
    public void homeTest() {
        assertEquals(trainController.home(httpHeaders), "Welcome to [ Train Service ] !");
    }

    @Test
    public void createTest() {
        Mockito.when(trainService.create(Mockito.any(TrainType.class),Mockito.any(HttpHeaders.class))).thenReturn(true);
        assertEquals(ok(new Response(1, "create success", true)),trainController.create(DTOA, httpHeaders));

        Mockito.when(trainService.create(Mockito.any(TrainType.class),Mockito.any(HttpHeaders.class))).thenReturn(false);
        trainController.create(DTOA, httpHeaders);
    }

    @Test
    public void retrieveTest() {
        Mockito.when(trainService.retrieve(id,httpHeaders)).thenReturn(A);
        trainController.retrieve(id, httpHeaders);
        Mockito.when(trainService.retrieve(id,httpHeaders)).thenReturn(null);
        assertEquals(ok(new Response(0, "here is no TrainType with the trainType id", id)),trainController.retrieve(id, httpHeaders));
    }

    @Test
    public void updateTest() {
        Mockito.when(trainService.update(Mockito.any(TrainType.class),Mockito.any(HttpHeaders.class))).thenReturn(true);
        assertEquals(ok(new Response(1, "update success", true)),trainController.update(DTOA,httpHeaders));
        Mockito.when(trainService.update(Mockito.any(TrainType.class),Mockito.any(HttpHeaders.class))).thenReturn(false);
        assertEquals(ok(new Response(0, "there is no trainType with the trainType id", false)),trainController.update(DTOA,httpHeaders));
    }

    @Test
    public void deleteTest() {
        Mockito.when(trainService.delete(id, httpHeaders)).thenReturn(true);
        assertEquals(ok(new Response(1, "delete success", true)),trainController.delete(id,httpHeaders));
        Mockito.when(trainService.delete(id, httpHeaders)).thenReturn(false);
        assertEquals(ok(new Response(0, "there is no train according to id", id)),trainController.delete(id,httpHeaders));
    }

    @Test
    public void quetyTest() {
        Mockito.when(trainService.query(httpHeaders)).thenReturn(LA);
        trainController.query(httpHeaders);
        LA.add(A);
        Mockito.when(trainService.query(httpHeaders)).thenReturn(LA);
        trainController.query(httpHeaders);
        Mockito.when(trainService.query(httpHeaders)).thenReturn(null);
        assertEquals(ok(new Response(0, "no content", null)),trainController.query(httpHeaders));
    }

}
