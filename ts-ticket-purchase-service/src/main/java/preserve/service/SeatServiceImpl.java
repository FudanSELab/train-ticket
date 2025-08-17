package preserve.service;

import edu.fudan.common.entity.*;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Seat service implementation migrated from ts-seat-service.
 */
@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(SeatServiceImpl.class);

    private String getServiceUrl(String serviceName) {
        return "http://" + serviceName;
    }

    @Override
    public Response distributeSeat(Seat seatRequest, HttpHeaders headers) {
        // Similar implementation as original
        LeftTicketInfo leftTicketInfo;
        ResponseEntity<Response<LeftTicketInfo>> re3;

        String trainNumber = seatRequest.getTrainNumber();
        if (trainNumber.startsWith("G") || trainNumber.startsWith("D")) {
            LOGGER.info("[distributeSeat][TrainNumber start][G or D]");
            HttpEntity<?> requestEntity = new HttpEntity<>(seatRequest, null);
            String order_service_url = getServiceUrl("ts-order-service");
            re3 = restTemplate.exchange(
                    order_service_url + "/api/v1/orderservice/order/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {});
            leftTicketInfo = re3.getBody().getData();
        } else {
            LOGGER.info("[distributeSeat][TrainNumber start][Other]");
            HttpEntity<?> requestEntity = new HttpEntity<>(seatRequest, null);
            String order_service_url = getServiceUrl("ts-order-service");
            re3 = restTemplate.exchange(
                    order_service_url + "/api/v1/orderservice/order/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {});
            leftTicketInfo = re3.getBody().getData();
        }

        List<String> stationList = seatRequest.getStations();
        int seatTotalNum = seatRequest.getTotalNum();
        String startStation = seatRequest.getStartStation();

        Ticket ticket = new Ticket();
        ticket.setStartStation(startStation);
        ticket.setDestStation(seatRequest.getDestStation());

        Random rand = new Random();
        int seat = rand.nextInt(seatTotalNum) + 1;

        if (leftTicketInfo != null) {
            Set<Ticket> soldTickets = leftTicketInfo.getSoldTickets();
            for (Ticket soldTicket : soldTickets) {
                String soldTicketDestStation = soldTicket.getDestStation();
                if (stationList.indexOf(soldTicketDestStation) < stationList.indexOf(startStation)) {
                    ticket.setSeatNo(soldTicket.getSeatNo());
                    LOGGER.info("[distributeSeat][Reuse seat number:{}]", soldTicket.getSeatNo());
                    return new Response<>(1, "Use the previous distributed seat number!", ticket);
                }
            }
            while (isContained(soldTicketNumbers(leftTicketInfo.getSoldTickets()), seat)) {
                seat = rand.nextInt(seatTotalNum) + 1;
            }
        }

        ticket.setSeatNo(seat);
        LOGGER.info("[distributeSeat][Assign new seat:{}]", seat);
        return new Response<>(1, "Use a new seat number!", ticket);
    }

    private boolean isContained(Set<Integer> soldSeatNumbers, int seat) {
        return soldSeatNumbers.contains(seat);
    }

    private Set<Integer> soldTicketNumbers(Set<Ticket> soldTickets) {
        return soldTickets.stream().map(Ticket::getSeatNo).collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public Response getLeftTicketOfInterval(Seat seatRequest, HttpHeaders headers) {
        int numOfLeftTicket = 0;
        LeftTicketInfo leftTicketInfo;
        ResponseEntity<Response<LeftTicketInfo>> re3;

        String trainNumber = seatRequest.getTrainNumber();
        LOGGER.info("[getLeftTicketOfInterval][Seat request:{}]", seatRequest);
        if (trainNumber.startsWith("G") || trainNumber.startsWith("D")) {
            HttpEntity<?> requestEntity = new HttpEntity<>(seatRequest, null);
            String order_service_url = getServiceUrl("ts-order-service");
            re3 = restTemplate.exchange(
                    order_service_url + "/api/v1/orderservice/order/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {});
            leftTicketInfo = re3.getBody().getData();
        } else {
            HttpEntity<?> requestEntity = new HttpEntity<>(seatRequest, null);
            String order_service_url = getServiceUrl("ts-order-service");
            re3 = restTemplate.exchange(
                    order_service_url + "/api/v1/orderservice/order/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {});
            leftTicketInfo = re3.getBody().getData();
        }

        List<String> stationList = seatRequest.getStations();
        int seatTotalNum = seatRequest.getTotalNum();
        int soldTicketSize = 0;
        if (leftTicketInfo != null) {
            String startStation = seatRequest.getStartStation();
            Set<Ticket> soldTickets = leftTicketInfo.getSoldTickets();
            soldTicketSize = soldTickets.size();
            for (Ticket soldTicket : soldTickets) {
                String soldTicketDestStation = soldTicket.getDestStation();
                if (stationList.indexOf(soldTicketDestStation) < stationList.indexOf(startStation)) {
                    numOfLeftTicket++;
                }
            }
        }

        double directPart = getDirectProportion(headers);
        if (!(stationList.get(0).equals(seatRequest.getStartStation()) && stationList.get(stationList.size() - 1).equals(seatRequest.getDestStation()))) {
            directPart = 1.0 - directPart;
        }
        int unusedNum = (int) (seatTotalNum * directPart) - soldTicketSize;
        numOfLeftTicket += unusedNum;
        return new Response<>(1, "Get Left Ticket of Interval Success", numOfLeftTicket);
    }

    private double getDirectProportion(HttpHeaders headers) {
        String configName = "DirectTicketAllocationProportion";
        HttpEntity<?> requestEntity = new HttpEntity<>(null);
        String config_service_url = getServiceUrl("ts-config-service");
        ResponseEntity<Response<Config>> re = restTemplate.exchange(
                config_service_url + "/api/v1/configservice/configs/" + configName,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Config>>() {});
        return Double.parseDouble(re.getBody().getData().getValue());
    }
}
