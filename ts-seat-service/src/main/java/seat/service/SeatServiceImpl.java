package seat.service;

import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import seat.entity.*;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(SeatServiceImpl.class);

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
            SeatServiceImpl.LOGGER.info("TrainNumber start with G|D");

            //Call the microservice to query all the station information for the train
            HttpEntity requestEntity = new HttpEntity(null);
            re = restTemplate.exchange(
                    "http://ts-travel-service:12346/api/v1/travelservice/routes/" + trainNumber,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
            routeResult = re.getBody();
            SeatServiceImpl.LOGGER.info("[distributeSeat] The result of getRouteResult is {}", routeResult.getMsg());

            //Call the microservice to query for residual Ticket information: the set of the Ticket sold for the specified seat type
            requestEntity = new HttpEntity(seatRequest, null);
            re3 = restTemplate.exchange(
                    "http://ts-order-service:12031/api/v1/orderservice/order/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });
            SeatServiceImpl.LOGGER.info("Left ticket info is : {}", re3.getBody().toString());
            leftTicketInfo = re3.getBody().getData();

            //Calls the microservice to query the total number of seats specified for that vehicle
            requestEntity = new HttpEntity(null);
            re2 = restTemplate.exchange(
                    "http://ts-travel-service:12346/api/v1/travelservice/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();
            trainTypeResult = trainTypeResponse.getData();

            SeatServiceImpl.LOGGER.info("[distributeSeat 1] The result of getTrainTypeResult is {}", trainTypeResponse.toString());
        } else {
            SeatServiceImpl.LOGGER.info("TrainNumber start with other capital");
            //Call the micro service to query all the station information for the trains
            HttpEntity requestEntity = new HttpEntity(null);
            re = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/routes/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
            routeResult = re.getBody();
            SeatServiceImpl.LOGGER.info("[distributeSeat] The result of getRouteResult is {}", routeResult.toString());

            //Call the microservice to query for residual Ticket information: the set of the Ticket sold for the specified seat type
            requestEntity = new HttpEntity(seatRequest, null);
            re3 = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });
            SeatServiceImpl.LOGGER.info("Left ticket info is : {}", re3.getBody().toString());
            leftTicketInfo = re3.getBody().getData();

            //Calls the microservice to query the total number of seats specified for that vehicle
            requestEntity = new HttpEntity(null);
            re2 = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();
            trainTypeResult = trainTypeResponse.getData();
            SeatServiceImpl.LOGGER.info("[distributeSeat 2] The result of getTrainTypeResult is {}", trainTypeResponse.toString());
        }


        //Assign seats
        List<String> stationList = routeResult.getData().getStations();
        int seatTotalNum = 0;
        if (seatRequest.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            seatTotalNum = trainTypeResult.getConfortClass();
            SeatServiceImpl.LOGGER.info("[distributeSeat] The request seat type is comfortClass and the total num is {}", seatTotalNum);
        } else {
            seatTotalNum = trainTypeResult.getEconomyClass();
            SeatServiceImpl.LOGGER.info("[distributeSeat] The request seat type is economyClass and the total num is {}", seatTotalNum);
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
                    SeatServiceImpl.LOGGER.info("[distributeSeat] Use the previous distributed seat number! {}", soldTicket.getSeatNo());
                    return new Response<>(1, "Use the previous distributed seat number!", ticket);
                }
            }
            while (isContained(soldTickets, seat)) {
                seat = rand.nextInt(range) + 1;
            }
        }
        ticket.setSeatNo(seat);
        SeatServiceImpl.LOGGER.info("[distributeSeat] Use a new seat number! {}", seat);
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
        SeatServiceImpl.LOGGER.info("Seat request To String: {}", seatRequest.toString());
        if (trainNumber.startsWith("G") || trainNumber.startsWith("D")) {
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval] TrainNumber start with G|D {}", trainNumber);

            //Call the micro service to query all the station information for the trains
            HttpEntity requestEntity = new HttpEntity(null);
            re = restTemplate.exchange(
                    "http://ts-travel-service:12346/api/v1/travelservice/routes/" + trainNumber,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
            routeResult = re.getBody();
            SeatServiceImpl.LOGGER.info("[ getLeftTicketOfInterval] The result of getRouteResult is {}", routeResult.getMsg());

            //Call the micro service to query for residual Ticket information: the set of the Ticket sold for the specified seat type
            requestEntity = new HttpEntity(seatRequest, null);
            re3 = restTemplate.exchange(
                    "http://ts-order-service:12031/api/v1/orderservice/order/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });

            SeatServiceImpl.LOGGER.info("Get Order tickets result is : {}", re3);
            leftTicketInfo = re3.getBody().getData();

            //Calls the microservice to query the total number of seats specified for that vehicle
            requestEntity = new HttpEntity(null);
            re2 = restTemplate.exchange(
                    "http://ts-travel-service:12346/api/v1/travelservice/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();


            trainTypeResult = trainTypeResponse.getData();
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval] The result of getTrainTypeResult is {}", trainTypeResponse.toString());
        } else {
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval] TrainNumber start with other capital");
            //Call the micro service to query all the station information for the trains
            HttpEntity requestEntity = new HttpEntity(null);
            re = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/routes/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<Route>>() {
                    });
            routeResult = re.getBody();
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval] The result of getRouteResult is {}", routeResult.toString());

            //Call the micro service to query for residual Ticket information: the set of the Ticket sold for the specified seat type
            requestEntity = new HttpEntity(seatRequest, null);
            re3 = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/tickets",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<LeftTicketInfo>>() {
                    });
            SeatServiceImpl.LOGGER.info("Get Order tickets result is : {}", re3);
            leftTicketInfo = re3.getBody().getData();


            //Calls the microservice to query the total number of seats specified for that vehicle
            requestEntity = new HttpEntity(null);
            re2 = restTemplate.exchange(
                    "http://ts-travel2-service:16346/api/v1/travel2service/train_types/" + seatRequest.getTrainNumber(),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TrainType>>() {
                    });
            Response<TrainType> trainTypeResponse = re2.getBody();
            trainTypeResult = trainTypeResponse.getData();
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval] The result of getTrainTypeResult is {}", trainTypeResponse.toString());
        }

        //Counting the seats remaining in certain sections
        List<String> stationList = routeResult.getData().getStations();
        int seatTotalNum;
        if (seatRequest.getSeatType() == SeatClass.FIRSTCLASS.getCode()) {
            seatTotalNum = trainTypeResult.getConfortClass();
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval] The request seat type is confortClass and the total num is {}", seatTotalNum);
        } else {
            seatTotalNum = trainTypeResult.getEconomyClass();
            SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval] The request seat type is economyClass and the total num is {}", seatTotalNum);
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
                    SeatServiceImpl.LOGGER.info("[getLeftTicketOfInterval] The previous distributed seat number is usable! {}", soldTicket.getSeatNo());
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
        ResponseEntity<Response<Config>> re = restTemplate.exchange(
                "http://ts-config-service:15679/api/v1/configservice/configs/" + configName,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Config>>() {
                });
        Response<Config> configValue = re.getBody();
        SeatServiceImpl.LOGGER.info("Configs is : {}", configValue.getData().toString());
        return Double.parseDouble(configValue.getData().getValue());
    }
}