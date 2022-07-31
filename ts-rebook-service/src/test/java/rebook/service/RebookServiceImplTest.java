package rebook.service;

import edu.fudan.common.entity.Seat;
import edu.fudan.common.entity.Ticket;
import edu.fudan.common.entity.TripAllDetail;
import edu.fudan.common.entity.TripResponse;
import edu.fudan.common.util.Response;
import edu.fudan.common.util.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import edu.fudan.common.entity.*;
import rebook.entity.RebookInfo;

import java.util.Date;

@RunWith(JUnit4.class)
public class RebookServiceImplTest {

    @InjectMocks
    private RebookServiceImpl rebookServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRebook() {
        RebookInfo info = new RebookInfo();
        info.setOldTripId("G");
        info.setSeatType(2);

        //response for getOrderByRebookInfo()
        Order order = new Order();
        order.setStatus(1);
        order.setFrom("from_station");
        order.setTo("to_station");
        order.setPrice("1.0");
        String date = StringUtils.Date2String(new Date());
        order.setTravelDate(date);
        order.setTravelTime(date);
        Response<Order> response = new Response<>(1, null, order);
        ResponseEntity<Response<Order>> re = new ResponseEntity<>(response, HttpStatus.OK);

        //response for getTripAllDetailInformation()
        TripAllDetail tripAllDetail = new TripAllDetail();
        TripResponse tripResponse = new TripResponse();
        tripResponse.setConfortClass(1);
        tripResponse.setPriceForConfortClass("2.0");
        tripAllDetail.setTripResponse(tripResponse);
        Response<TripAllDetail> response2 = new Response<>(1, null, tripAllDetail);
        ResponseEntity<Response<TripAllDetail>> re2 = new ResponseEntity<>(response2, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(re).thenReturn(re2);

        //mock queryForStationName()
        Response<String> response3 = new Response(null, null, "");
        ResponseEntity<Response<String>> re3 = new ResponseEntity<>(response3, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(Class.class)))
                .thenReturn(re3);

        Response result = rebookServiceImpl.rebook(info, headers);
        Assert.assertEquals("Please pay the different money!", result.getMsg());
    }

    @Test
    public void testPayDifference() {
        RebookInfo info = new RebookInfo();
        info.setTripId("G");
        info.setOldTripId("G");
        info.setLoginId("login_id");
        info.setDate("");
        info.setSeatType(0);

        //mock getOrderByRebookInfo() and getTripAllDetailInformation()
        Order order = new Order();
        order.setFrom("from_station");
        order.setTo("to_station");
        order.setPrice("0");
        Response<Order> response = new Response<>(1, null, order);
        ResponseEntity<Response<Order>> re = new ResponseEntity<>(response, HttpStatus.OK);

        TripAllDetail tripAllDetail = new TripAllDetail();
        Response<TripAllDetail> response2 = new Response<>(0, null, tripAllDetail);
        ResponseEntity<Response<TripAllDetail>> re2 = new ResponseEntity<>(response2, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(re).thenReturn(re2);

        //mock queryForStationName() and updateOrder()
        Response response3 = new Response<>(0, null, "");
        ResponseEntity<Response> re3 = new ResponseEntity<>(response3, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(Class.class)))
                .thenReturn(re3);
        Response result = rebookServiceImpl.payDifference(info, headers);
        Assert.assertEquals(new Response<>(0, "Can't pay the difference,please try again", null), result);
    }

    @Test
    public void testDipatchSeat() {
        Seat seatRequest = new Seat(StringUtils.Date2String(new Date()), "G1234", "start_station", "dest_station", 2, 100, null);
        HttpEntity requestEntityTicket = new HttpEntity<>(seatRequest, headers);
        Response<Ticket> response = new Response<>();
        ResponseEntity<Response<Ticket>> reTicket = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-seat-service:18898/api/v1/seatservice/seats",
                HttpMethod.POST,
                requestEntityTicket,
                new ParameterizedTypeReference<Response<Ticket>>() {
                })).thenReturn(reTicket);
        Ticket result = rebookServiceImpl.dipatchSeat(StringUtils.Date2String(new Date()), "G1234", "start_station", "dest_station", 2, 100, null,headers);
        Assert.assertNull(result);
    }

}
