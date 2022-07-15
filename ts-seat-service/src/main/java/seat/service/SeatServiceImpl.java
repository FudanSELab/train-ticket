package seat.service;

import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import edu.fudan.common.entity.*;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author fdse
 */
@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(SeatServiceImpl.class);

    private String getServiceUrl(String serviceName) {
        return "http://" + serviceName;
    }

    @Override
    public Response distributeSeat(Seat seatRequest, HttpHeaders headers) {
        Response<Route> routeResult;

        LeftTicketInfo leftTicketInfo;
        TrainType trainTypeResult = null;
        ResponseEntity<Response<Route>> re;
        ResponseEntity<Response<TrainType>> re2;
        ResponseEntity<Response<LeftTicketInfo>> re3;

        //Distinguish G\D from other trains
        String trainNumber = seatRequest.getTrainNumber();

        if (trainNumber.startsWith("G") || trainNumber.startsWith("D")) {
            SeatServiceImpl.LOGGER.info("[distributeSeat][TrainNumber start][G or D]");

            //Call the microservice to query all the station information for the train
            HttpEntity requestEntity = new HttpEntity(null);
            String travel_service_url=getServiceUrl("ts-travel-service");
            re = restTemplate.exchange(
                    travel_service_url + "/api/v1/travelservice/routes/" + trainNumber,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
            routeResult = re.getBody();
            SeatServiceImpl.LOGGER.info("[distributeSeat][Result of GetRouteResult][result is {}]", routeResult.getMsg());

            //Call the microservice to query for residual Ticket information: the set of the Ticket sold for the specified seat type
            requestEntity = new HttpEntity(seatRequest, null);
            String order_service_url=getServiceUrl("ts-order-service");
            re3 = restTemplate.exchange(
                    order_service_url + "/api/v1/orderservice/order/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });
            SeatServiceImpl.LOGGER.info("[distributeSeat][Left ticket info][info is : {}]", re3.getBody().toString());
            leftTicketInfo = re3.getBody().getData();

            //Calls the microservice to query the total number of seats specified for that vehicle
            requestEntity = new HttpEntity(null);
            re2 = restTemplate.exchange(
                    travel_service_url + "/api/v1/travelservice/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();
            trainTypeResult = trainTypeResponse.getData();

            SeatServiceImpl.LOGGER.info("[distributeSeat][Result of getTrainTypeResult][result is {}]", trainTypeResponse.toString());
        } else {
            SeatServiceImpl.LOGGER.info("[distributeSeat][TrainNumber start][Other Capital Except D and G]");
            //Call the micro service to query all the station information for the trains
            HttpEntity requestEntity = new HttpEntity(null);
            String travel2_service_url=getServiceUrl("ts-travel2-service");
            re = restTemplate.exchange(
                    travel2_service_url + "/api/v1/travel2service/routes/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
            routeResult = re.getBody();
            SeatServiceImpl.LOGGER.info("[distributeSeat][Result of getRouteResult][result is {}]", routeResult.toString());

            //Call the microservice to query for residual Ticket information: the set of the Ticket sold for the specified seat type
            requestEntity = new HttpEntity(seatRequest, null);
            String order_other_service_url=getServiceUrl("ts-order-other-service");
            re3 = restTemplate.exchange(
                    order_other_service_url + "/api/v1/orderOtherService/orderOther/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });
            SeatServiceImpl.LOGGER.info("[distributeSeat][Left ticket info][info is : {}]", re3.getBody().toString());
            leftTicketInfo = re3.getBody().getData();

            //Calls the microservice to query the total number of seats specified for that vehicle
            requestEntity = new HttpEntity(null);
            re2 = restTemplate.exchange(
                    travel2_service_url + "/api/v1/travel2service/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();
            trainTypeResult = trainTypeResponse.getData();
            SeatServiceImpl.LOGGER.info("[distributeSeat][Result of getTrainTypeResult][result is {}]", trainTypeResponse.toString());
        }


        //Assign seats
        List<String> stationList = routeResult.getData().getStations();
        int seatTotalNum = 0;
        if (seatRequest.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            seatTotalNum = trainTypeResult.getConfortClass();
            SeatServiceImpl.LOGGER.info("[distributeSeat][Assign Seats][The request seat type is comfortClass and the total num is {}]", seatTotalNum);
        } else {
            seatTotalNum = trainTypeResult.getEconomyClass();
            SeatServiceImpl.LOGGER.info("[distributeSeat][Assign Seats][The request seat type is economyClass and the total num is {}]", seatTotalNum);
        }
        String startStation = seatRequest.getStartStation();
        Ticket ticket = new Ticket();
        ticket.setStartStation(startStation);
        ticket.setDestStation(seatRequest.getDestStation());

        //Assign new tickets
        Random rand = new Random();
        int range = seatTotalNum;
        int seat = rand.nextInt(range) + 1;

        if(leftTicketInfo != null) {
            Set<Ticket> soldTickets = leftTicketInfo.getSoldTickets();
            //Give priority to tickets already sold
            for (Ticket soldTicket : soldTickets) {
                String soldTicketDestStation = soldTicket.getDestStation();
                //Tickets can be allocated if the sold ticket's end station before the start station of the request
                if (stationList.indexOf(soldTicketDestStation) < stationList.indexOf(startStation)) {
                    ticket.setSeatNo(soldTicket.getSeatNo());
                    SeatServiceImpl.LOGGER.info("[distributeSeat][Assign new tickets][Use the previous distributed seat number][seat number:{}]", soldTicket.getSeatNo());
                    return new Response<>(1, "Use the previous distributed seat number!", ticket);
                }
            }
            while (isContained(soldTickets, seat)) {
                seat = rand.nextInt(range) + 1;
            }
        }
        ticket.setSeatNo(seat);
        SeatServiceImpl.LOGGER.info("[distributeSeat][Assign new tickets][Use a new seat number][seat number:{}]", seat);
        return new Response<>(1, "Use a new seat number!", ticket);
    }

    private boolean isContained(Set<Ticket> soldTickets, int seat) {
        //Check that the seat number has been used
        boolean result = false;
        for (Ticket soldTicket : soldTickets) {
            if (soldTicket.getSeatNo() == seat) {
                return true;
            }
        }
        return result;
    }

    @Override
    public Response getLeftTicketOfInterval(Seat seatRequest, HttpHeaders headers) {
        int numOfLeftTicket = 0;
        Response<Route> routeResult;
        TrainType trainTypeResult;
        LeftTicketInfo leftTicketInfo;

        ResponseEntity<Response<Route>> re;
        ResponseEntity<Response<TrainType>> re2;
        ResponseEntity<Response<LeftTicketInfo>> re3;

        //Distinguish G\D from other trains
        String trainNumber = seatRequest.getTrainNumber();
        SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][Seat request][request:{}]", seatRequest.toString());
        if (trainNumber.startsWith("G") || trainNumber.startsWith("D")) {
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][TrainNumber start with G|D][trainNumber:{}]", trainNumber);

            //Call the micro service to query all the station information for the trains
            HttpEntity requestEntity = new HttpEntity(null);
            String travel_service_url=getServiceUrl("ts-travel-service");
            re = restTemplate.exchange(
                    travel_service_url + "/api/v1/travelservice/routes/" + trainNumber,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
            routeResult = re.getBody();
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][Result of getRouteResult][result is {}]", routeResult.getMsg());

            //Call the micro service to query for residual Ticket information: the set of the Ticket sold for the specified seat type
            requestEntity = new HttpEntity(seatRequest, null);
            String order_service_url=getServiceUrl("ts-order-service");
            re3 = restTemplate.exchange(
                    order_service_url + "/api/v1/orderservice/order/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });

            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][Get Order tickets result][result is {}]", re3);
            leftTicketInfo = re3.getBody().getData();

            //Calls the microservice to query the total number of seats specified for that vehicle
            requestEntity = new HttpEntity(null);
            re2 = restTemplate.exchange(
                    travel_service_url + "/api/v1/travelservice/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();


            trainTypeResult = trainTypeResponse.getData();
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][Result of getTrainTypeResult][result is {}]", trainTypeResponse.toString());
        } else {
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][TrainNumber start with other capital][trainNumber:{}]", trainNumber);
            //Call the micro service to query all the station information for the trains
            HttpEntity requestEntity = new HttpEntity(null);
            String travel2_service_url=getServiceUrl("ts-travel2-service");
            re = restTemplate.exchange(
                    travel2_service_url + "/api/v1/travel2service/routes/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
            routeResult = re.getBody();
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][Result of getRouteResult][result is {}]", routeResult.toString());

            //Call the micro service to query for residual Ticket information: the set of the Ticket sold for the specified seat type
            requestEntity = new HttpEntity(seatRequest, null);
            String order_other_service_url=getServiceUrl("ts-order-other-service");
            re3 = restTemplate.exchange(
                    order_other_service_url + "/api/v1/orderOtherService/orderOther/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][Get Order tickets result][result is {}]", re3);
            leftTicketInfo = re3.getBody().getData();


            //Calls the microservice to query the total number of seats specified for that vehicle
            requestEntity = new HttpEntity(null);
            re2 = restTemplate.exchange(
                    travel2_service_url + "/api/v1/travel2service/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();
            trainTypeResult = trainTypeResponse.getData();
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][Result of getTrainTypeResult][result is {}]", trainTypeResponse.toString());
        }

        //Counting the seats remaining in certain sections
        List<String> stationList = routeResult.getData().getStations();
        int seatTotalNum;
        if (seatRequest.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            seatTotalNum = trainTypeResult.getConfortClass();
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][Count Seats][The request seat type is confortClass and the total num is {}]", seatTotalNum);
        } else {
            seatTotalNum = trainTypeResult.getEconomyClass();
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][Count Seats][The request seat type is economyClass and the total num is {}]", seatTotalNum);
        }

        int solidTicketSize = 0;
        if (leftTicketInfo != null) {
            String startStation = seatRequest.getStartStation();
            Set<Ticket> soldTickets = leftTicketInfo.getSoldTickets();
            solidTicketSize = soldTickets.size();
            //To find out if tickets already sold are available
            for (Ticket soldTicket : soldTickets) {
                String soldTicketDestStation = soldTicket.getDestStation();
                //Tickets can be allocated if the sold ticket's end station before the start station of the request
                if (stationList.indexOf(soldTicketDestStation) < stationList.indexOf(startStation)) {
                    SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval][Ticket available or sold][The previous distributed seat number is usable][{}]", soldTicket.getSeatNo());
                    numOfLeftTicket++;
                }
            }
        }
        //Count the unsold tickets

        double direstPart = getDirectProportion(headers);
        Route route = routeResult.getData();
        if (route.getStations().get(0).equals(seatRequest.getStartStation()) &&
                route.getStations().get(route.getStations().size() - 1).equals(seatRequest.getDestStation())) {
            //do nothing
        } else {
            direstPart = 1.0 - direstPart;
        }

        int unusedNum = (int) (seatTotalNum * direstPart) - solidTicketSize;
        numOfLeftTicket += unusedNum;

        return new Response<>(1, "Get Left Ticket of Internal Success", numOfLeftTicket);
    }

    private double getDirectProportion(HttpHeaders headers) {

        String configName = "DirectTicketAllocationProportion";
        HttpEntity requestEntity = new HttpEntity(null);
        String config_service_url = getServiceUrl("ts-config-service");
        ResponseEntity<Response<Config>> re = restTemplate.exchange(
                config_service_url + "/api/v1/configservice/configs/" + configName,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Config>>() {
                });
        Response<Config> configValue = re.getBody();
        SeatServiceImpl.LOGGER.info("[getDirectProportion][Configs is : {}]", configValue.getData().toString());
        return Double.parseDouble(configValue.getData().getValue());
    }
}