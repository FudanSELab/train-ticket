package travel2.service;

import edu.fudan.common.util.JsonUtils;
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
import travel2.entity.*;
import travel2.repository.TripRepository;

import java.util.*;

/**
 * @author fdse
 */
@Service
public class Travel2ServiceImpl implements Travel2Service {

    @Autowired
    TripRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(Travel2ServiceImpl.class);

    String success = "Success";
    String noCnontent = "No Content";

    @Override
    public Response getRouteByTripId(String tripId, HttpHeaders headers) {
        TripId tripId1 = new TripId(tripId);

        Trip trip = repository.findByTripId(tripId1);
        if (trip == null) {
            Travel2ServiceImpl.LOGGER.error("[Get Route By Trip ID] Trip Not Found, TripId: {}", tripId);
            return new Response<>(0, "\"[Get Route By Trip ID] Trip Not Found:\" + tripId", null);
        } else {
            Route route = getRouteByRouteId(trip.getRouteId(), headers);
            if (route == null) {
                Travel2ServiceImpl.LOGGER.error("Get route by Trip id error.Route not found, RouteId: {}",trip.getRouteId());
                return new Response<>(0, "\"[Get Route By Trip ID] Route Not Found:\" + trip.getRouteId()", null);
            } else {
                Travel2ServiceImpl.LOGGER.info("[Get Route By Trip ID] Success");
                return new Response<>(1, "[Get Route By Trip ID] Success", route);
            }
        }
    }


    @Override
    public Response getTrainTypeByTripId(String tripId, HttpHeaders headers) {
        TripId tripId1 = new TripId(tripId);
        TrainType trainType = null;
        Trip trip = repository.findByTripId(tripId1);
        if (trip != null) {
            trainType = getTrainType(trip.getTrainTypeId(), headers);
        }
        else {
            Travel2ServiceImpl.LOGGER.error("Get Train Type by Trip id error.Trip not found, TripId: {}",tripId);
        }
        if (trainType != null) {
            return new Response<>(1, "Success query Train by trip id", trainType);
        } else {
            Travel2ServiceImpl.LOGGER.error("Get Train Type by Trip id error.Train Type not found, TripId: {}",tripId);
            return new Response<>(0, noCnontent, null);
        }
    }

    @Override
    public Response getTripByRoute(ArrayList<String> routeIds, HttpHeaders headers) {
        ArrayList<ArrayList<Trip>> tripList = new ArrayList<>();
        for (String routeId : routeIds) {
            ArrayList<Trip> tempTripList = repository.findByRouteId(routeId);
            if (tempTripList == null) {
                tempTripList = new ArrayList<>();
            }
            tripList.add(tempTripList);
        }
        if (!tripList.isEmpty()) {
            return new Response<>(1, success, tripList);
        } else {
            Travel2ServiceImpl.LOGGER.warn("Get Trips by Route ids warn.Trips not found, RouteIdNumber: {}",routeIds.size());
            return new Response<>(0, noCnontent, null);
        }
    }

    @Override
    public Response create(TravelInfo info, HttpHeaders headers) {
        TripId ti = new TripId(info.getTripId());
        if (repository.findByTripId(ti) == null) {
            Trip trip = new Trip(ti, info.getTrainTypeId(), info.getStartingStationId(),
                    info.getStationsId(), info.getTerminalStationId(), info.getStartingTime(), info.getEndTime());
            trip.setRouteId(info.getRouteId());
            repository.save(trip);
            return new Response<>(1, "Create trip info:" + ti.toString() + ".", null);
        } else {
            Travel2ServiceImpl.LOGGER.error("Create trip error.Trip already exists,TripId: {}",info.getTripId());
            return new Response<>(1, "Trip " + info.getTripId() + " already exists", null);
        }
    }

    @Override
    public Response retrieve(String tripId, HttpHeaders headers) {
        TripId ti = new TripId(tripId);
        Trip trip = repository.findByTripId(ti);
        if (trip != null) {
            return new Response<>(1, "Search Trip Success by Trip Id " + tripId, trip);
        } else {
            Travel2ServiceImpl.LOGGER.error("Retrieve trip error.Trip not found,TripId: {}",tripId);
            return new Response<>(0, "No Content according to tripId" + tripId, null);
        }
    }

    @Override
    public Response update(TravelInfo info, HttpHeaders headers) {
        TripId ti = new TripId(info.getTripId());
        if (repository.findByTripId(ti) != null) {
            Trip trip = new Trip(ti, info.getTrainTypeId(), info.getStartingStationId(),
                    info.getStationsId(), info.getTerminalStationId(), info.getStartingTime(), info.getEndTime());
            trip.setRouteId(info.getRouteId());
            repository.save(trip);
            return new Response<>(1, "Update trip info:" + ti.toString(), trip);
        } else {
            Travel2ServiceImpl.LOGGER.error("Update trip error.Trip not found,TripId: {}",info.getTripId());
            return new Response<>(1, "Trip" + info.getTripId() + "doesn 't exists", null);
        }
    }

    @Override
    public Response delete(String tripId, HttpHeaders headers) {
        TripId ti = new TripId(tripId);
        if (repository.findByTripId(ti) != null) {
            repository.deleteByTripId(ti);
            return new Response<>(1, "Delete trip:" + tripId + ".", tripId);
        } else {
            Travel2ServiceImpl.LOGGER.error("Delete trip error.Trip not found,TripId: {}",tripId);
            return new Response<>(0, "Trip " + tripId + " doesn't exist.", null);
        }
    }

    @Override
    public Response query(TripInfo info, HttpHeaders headers) {

        //Gets the start and arrival stations of the train number to query. The originating and arriving stations received here are both station names, so two requests need to be sent to convert to station ids
        String startingPlaceName = info.getStartingPlace();
        String endPlaceName = info.getEndPlace();
        String startingPlaceId = queryForStationId(startingPlaceName, headers);
        String endPlaceId = queryForStationId(endPlaceName, headers);

        //This is the final result
        ArrayList<TripResponse> list = new ArrayList<>();

        //Check all train info
        ArrayList<Trip> allTripList = repository.findAll();
        for (Trip tempTrip : allTripList) {
            //Get the detailed route list of this train
            Route tempRoute = getRouteByRouteId(tempTrip.getRouteId(), headers);
            //Check the route list for this train. Check that the required start and arrival stations are in the list of stops that are not on the route, and check that the location of the start station is before the stop
            //Trains that meet the above criteria are added to the return list
            if (tempRoute.getStations().contains(startingPlaceId) &&
                    tempRoute.getStations().contains(endPlaceId) &&
                    tempRoute.getStations().indexOf(startingPlaceId) < tempRoute.getStations().indexOf(endPlaceId)) {
                TripResponse response = getTickets(tempTrip, tempRoute, startingPlaceId, endPlaceId, startingPlaceName, endPlaceName, info.getDepartureTime(), headers);
                if (response == null) {
                    Travel2ServiceImpl.LOGGER.warn("Query trip error.Tickets not found,start: {},end: {},time: {}",info.getStartingPlace(),info.getEndPlace(),info.getDepartureTime());
                    return new Response<>(0, noCnontent, null);
                }
                list.add(response);
            }
        }
        return new Response<>(1, "Success Query", list);
    }

    @Override
    public Response getTripAllDetailInfo(TripAllDetailInfo gtdi, HttpHeaders headers) {
        TripAllDetail gtdr = new TripAllDetail();
        Travel2ServiceImpl.LOGGER.info("[getTripAllDetailInfo] gtdi info: {}", gtdi.toString());
        Trip trip = repository.findByTripId(new TripId(gtdi.getTripId()));
        if (trip == null) {
            gtdr.setTripResponse(null);
            gtdr.setTrip(null);
            Travel2ServiceImpl.LOGGER.error("Get trip detail error.Trip not found,TripId: {}",gtdi.getTripId());
        } else {
            String endPlaceName = gtdi.getTo();
            String startingPlaceName = gtdi.getFrom();
            String startingPlaceId = queryForStationId(startingPlaceName, headers);
            String endPlaceId = queryForStationId(endPlaceName, headers);
            Travel2ServiceImpl.LOGGER.info("[getTripAllDetailInfo] endPlaceID: {}", endPlaceId);
            Route tempRoute = getRouteByRouteId(trip.getRouteId(), headers);
            TripResponse tripResponse = getTickets(trip, tempRoute, startingPlaceId, endPlaceId, gtdi.getFrom(), gtdi.getTo(), gtdi.getTravelDate(), headers);
            if (tripResponse == null) {
                gtdr.setTrip(null);
                gtdr.setTripResponse(null);
                Travel2ServiceImpl.LOGGER.warn("Query trip error.Tickets not found,start: {},end: {}", gtdi.getTo(), gtdi.getFrom());
            } else {
                gtdr.setTripResponse(tripResponse);
                gtdr.setTrip(repository.findByTripId(new TripId(gtdi.getTripId())));
            }
        }


        return new Response<>(1, success, gtdr);
    }


    private TripResponse getTickets(Trip trip, Route route, String startingPlaceId, String endPlaceId, String startingPlaceName, String endPlaceName, Date departureTime, HttpHeaders headers) {

        //Determine if the date checked is the same day and after
        if (!afterToday(departureTime)) {
            return null;
        }

        Travel query = new Travel();
        query.setTrip(trip);
        query.setStartingPlace(startingPlaceName);
        query.setEndPlace(endPlaceName);
        query.setDepartureTime(departureTime);

        HttpEntity requestEntity = new HttpEntity(query, null);
        ResponseEntity<Response<TravelResult>> re = restTemplate.exchange(
                "http://ts-ticketinfo-service:15681/api/v1/ticketinfoservice/ticketinfo",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<TravelResult>>() {
                });
        Travel2ServiceImpl.LOGGER.info("Ticket info  is: {}", re.getBody().toString());
        TravelResult resultForTravel =  re.getBody().getData();



        //Ticket order _ high-speed train (number of tickets purchased)
        requestEntity = new HttpEntity(null);
        ResponseEntity<Response<SoldTicket>> re2 = restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/" + departureTime + "/" + trip.getTripId().toString(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<SoldTicket>>() {
                });
        Travel2ServiceImpl.LOGGER.info("Order other Ticket info  is: {}", re.getBody().toString());
        SoldTicket result = re2.getBody().getData();

        if (result == null) {
            Travel2ServiceImpl.LOGGER.warn("Get tickets warn.Sold ticket Info doesn't exist,Departure Time: {},TripId: {}",departureTime,trip.getTripId());
            return null;
        }
        //Set the returned ticket information
        TripResponse response = new TripResponse();
        if (queryForStationId(startingPlaceName, headers).equals(trip.getStartingStationId()) &&
                queryForStationId(endPlaceName, headers).equals(trip.getTerminalStationId())) {
            response.setEconomyClass(50);
            response.setConfortClass(50);
        } else {
            response.setConfortClass(50);
            response.setEconomyClass(50);
        }

        int first = getRestTicketNumber(departureTime, trip.getTripId().toString(),
                startingPlaceName, endPlaceName, SeatClass.FIRSTCLASS.getCode(), headers);

        int second = getRestTicketNumber(departureTime, trip.getTripId().toString(),
                startingPlaceName, endPlaceName, SeatClass.SECONDCLASS.getCode(), headers);
        response.setConfortClass(first);
        response.setEconomyClass(second);

        response.setStartingStation(startingPlaceName);
        response.setTerminalStation(endPlaceName);

        //Calculate the distance from the starting point
        Travel2ServiceImpl.LOGGER.info("[getTickets] route: {}  station: {}", route.getId(), route.getStations());
        int indexStart = route.getStations().indexOf(startingPlaceId);
        int indexEnd = route.getStations().indexOf(endPlaceId);
        int distanceStart = route.getDistances().get(indexStart) - route.getDistances().get(0);
        int distanceEnd = route.getDistances().get(indexEnd) - route.getDistances().get(0);
        TrainType trainType = getTrainType(trip.getTrainTypeId(), headers);
        //Train running time is calculated according to the average running speed of the train
        int minutesStart = 60 * distanceStart / trainType.getAverageSpeed();
        int minutesEnd = 60 * distanceEnd / trainType.getAverageSpeed();

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(trip.getStartingTime());
        calendarStart.add(Calendar.MINUTE, minutesStart);
        response.setStartingTime(calendarStart.getTime());
        Travel2ServiceImpl.LOGGER.info("calculate time：{}  time: {}", minutesStart, calendarStart.getTime());

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(trip.getStartingTime());
        calendarEnd.add(Calendar.MINUTE, minutesEnd);
        response.setEndTime(calendarEnd.getTime());
        Travel2ServiceImpl.LOGGER.info("calculate time：{}  time: {}", minutesEnd, calendarEnd.getTime());

        response.setTripId(new TripId(result.getTrainNumber()));
        response.setTrainTypeId(trip.getTrainTypeId());
        response.setPriceForConfortClass(resultForTravel.getPrices().get("confortClass"));
        response.setPriceForEconomyClass(resultForTravel.getPrices().get("economyClass"));

        return response;
    }

    @Override
    public Response queryAll(HttpHeaders headers) {
        List<Trip> tripList = repository.findAll();
        if (tripList != null && !tripList.isEmpty()) {
            return new Response<>(1, success, tripList);
        }
        Travel2ServiceImpl.LOGGER.warn("Query all trips warn: {}","No Content");
        return new Response<>(0, noCnontent, null);
    }

    private static boolean afterToday(Date date) {
        Calendar calDateA = Calendar.getInstance();
        Date today = new Date();
        calDateA.setTime(today);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date);

        if (calDateA.get(Calendar.YEAR) > calDateB.get(Calendar.YEAR)) {
            return false;
        } else if (calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)) {
            if (calDateA.get(Calendar.MONTH) > calDateB.get(Calendar.MONTH)) {
                return false;
            } else if (calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)) {
                return calDateA.get(Calendar.DAY_OF_MONTH) <= calDateB.get(Calendar.DAY_OF_MONTH);
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private TrainType getTrainType(String trainTypeId, HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response<TrainType>> re = restTemplate.exchange(
                "http://ts-train-service:14567/api/v1/trainservice/trains/" + trainTypeId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<TrainType>>() {
                });

        return re.getBody().getData();
    }

    private String queryForStationId(String stationName, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response<String>> re = restTemplate.exchange(
                "http://ts-ticketinfo-service:15681/api/v1/ticketinfoservice/ticketinfo/" + stationName,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<String>>() {
                });


        return re.getBody().getData();
    }

    private Route getRouteByRouteId(String routeId, HttpHeaders headers) {
        Travel2ServiceImpl.LOGGER.info("[Get Route By Id] Route ID：{}", routeId);
        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-route-service:11178/api/v1/routeservice/routes/" + routeId,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response result = re.getBody();

        if (result.getStatus() == 0 ) {
            Travel2ServiceImpl.LOGGER.error("[Get Route By Id] Fail.Route not found,RouteId: {}", routeId);
            return null;
        } else {
            Travel2ServiceImpl.LOGGER.info("[Get Route By Id] Success.");
            return JsonUtils.conveterObject(result.getData(), Route.class);
        }
    }

    private int getRestTicketNumber(Date travelDate, String trainNumber, String startStationName, String endStationName, int seatType, HttpHeaders headers) {
        Seat seatRequest = new Seat();

        String fromId = queryForStationId(startStationName, headers);
        String toId = queryForStationId(endStationName, headers);

        seatRequest.setDestStation(toId);
        seatRequest.setStartStation(fromId);
        seatRequest.setTrainNumber(trainNumber);
        seatRequest.setSeatType(seatType);
        seatRequest.setTravelDate(travelDate);
        Travel2ServiceImpl.LOGGER.info("Seat request To String: {}", seatRequest.toString());

        HttpEntity requestEntity = new HttpEntity(seatRequest, null);
        ResponseEntity<Response<Integer>> re = restTemplate.exchange(
                "http://ts-seat-service:18898/api/v1/seatservice/seats/left_tickets",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<Integer>>() {
                });
        int restNumber =   re.getBody().getData();

        Travel2ServiceImpl.LOGGER.info("Get Rest tickets num is: {}", re.getBody().toString());
        return restNumber;
    }

    @Override
    public Response adminQueryAll(HttpHeaders headers) {
        List<Trip> trips = repository.findAll();
        ArrayList<AdminTrip> adminTrips = new ArrayList<>();
        for (Trip trip : trips) {
            AdminTrip adminTrip = new AdminTrip();
            adminTrip.setRoute(getRouteByRouteId(trip.getRouteId(), headers));
            adminTrip.setTrainType(getTrainType(trip.getTrainTypeId(), headers));
            adminTrip.setTrip(trip);
            adminTrips.add(adminTrip);
        }
        if (!adminTrips.isEmpty()) {
            return new Response<>(1, "Travel Service Admin Query All Travel Success", adminTrips);
        } else {
            Travel2ServiceImpl.LOGGER.warn("Admin query all trips warn: {}","No Content");
            return new Response<>(0, noCnontent, null);
        }
    }

}

