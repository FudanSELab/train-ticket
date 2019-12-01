import edu.fudan.common.util.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ticketinfo.entity.TrainType;
import ticketinfo.entity.Travel;
import ticketinfo.entity.TravelResult;
import ticketinfo.service.TicketInfoServiceImpl;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;



public class TicketInfoServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TicketInfoServiceImpl ticketInfoService;

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
        re = new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @Test
    public void testA() {
        Mockito.when(restTemplate.exchange(
                Mockito.any(String.class),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(Class.class)))
                .thenReturn(re);
        assertThat(ticketInfoService.queryForTravel(travel, headers), is(response));
        assertThat(ticketInfoService.queryForStationId(stationID, headers), is(response));
    }
}
