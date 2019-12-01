import edu.fudan.common.util.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import ticketinfo.controller.TicketInfoController;
import ticketinfo.entity.TrainType;
import ticketinfo.entity.Travel;
import ticketinfo.entity.TravelResult;
import ticketinfo.service.TicketInfoService;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.ResponseEntity.ok;


public class TicketInfoControllerTest {

    @Mock
    private TicketInfoService service;

    @InjectMocks
    private TicketInfoController ticketInfoController;

    private Travel travel;
    private HttpHeaders headers;
    private Response response;
    private ResponseEntity<Response> re;
    private TravelResult result;
    private HashMap<String, String> prices = new HashMap<String, String>();
    private TrainType trainType;
    private String stationID;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        travel = null;
        headers = null;
        stationID = "G7512";
        trainType = new TrainType(stationID, 95, 120);
        trainType.setAverageSpeed(123);
        prices = new HashMap<String, String>();
        prices.put("economyClass", "95.0");
        prices.put("confortClass", "120.0");
        result = new TravelResult();
        result.setStatus(true);
        result.setTrainType(trainType);
        result.setPrices(prices);
        result.setPercent(1.0);
        response = new Response<TravelResult>(1, "Success", result);
    }

    @Test
    public void testA() {
        assertEquals(ticketInfoController.home(), "Welcome to [ TicketInfo Service ] !");
        Mockito.when(service.queryForTravel(
                Mockito.any(Travel.class),
                Mockito.any(HttpHeaders.class)))
                .thenReturn(response);
        assertEquals(ticketInfoController.queryForTravel(travel, headers), ok(response));
        Mockito.when(service.queryForStationId(
                Mockito.any(String.class),
                Mockito.any(HttpHeaders.class)))
                .thenReturn(response);
        assertEquals(ticketInfoController.queryForStationId(stationID, headers), ok(response));
    }
}
