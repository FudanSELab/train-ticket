package travel2.service;

import edu.fudan.common.entity.*;
import edu.fudan.common.util.JsonUtils;
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
import travel2.entity.AdminTrip;
import travel2.entity.Trip;
import travel2.entity.Travel;
import travel2.entity.TripAllDetail;
import travel2.repository.TripRepository;

import java.util.*;

/**
 * @author fdse
 */
@Service
public class TravelServiceImpl implements TravelService {

    @Autowired
    TripRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(TravelServiceImpl.class);

    private String getServiceUrl(String serviceName) {
        return "http://" + serviceName; }

    String success = "Success";
    String noCnontent = "No Content";

    @Override
    public Response getRouteByTripId(String tripId, HttpHeaders headers) {
        TripId tripId1 = new TripId(tripId);

        Trip trip = repository.findByTripId(tripId1);
        if (trip == null) {
            TravelServiceImpl.LOGGER.error("[getRouteByTripId][Get Route By Trip ID Fail][Trip Not Found][TripId: {}]", tripId);
            return new Response<>(0, "\"[Get Route By Trip ID] Trip Not Found:\" + tripId", null);
        } else {
            Route route = getRouteByRouteId(trip.getRouteId(), headers);
            if (route == null) {
                TravelServiceImpl.LOGGER.error("[getRouteByTripId][Get route by Trip id error][Route not found][RouteId: {}]",trip.getRouteId());
                return new Response<>(0, "\"[Get Route By Trip ID] Route Not Found:\" + trip.getRouteId()", null);
            } else {
                TravelServiceImpl.LOGGER.info("[getRouteByTripId][Get Route By Trip ID Success]");
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
            trainType = getTrainType(trip.getTrainTypeName(), headers);
        }
        else {
            TravelServiceImpl.LOGGER.error("[getTrainTypeByTripId[]Get Train Type by Trip id error][Trip not found][TripId: {}]",tripId);
        }
        if (trainType != null) {
            return new Response<>(1, "Success query Train by trip id", trainType);
        } else {
            TravelServiceImpl.LOGGER.error("[getTrainTypeByTripId][Get Train Type by Trip id error][Train Type not found][TripId: {}]",tripId);
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
            TravelServiceImpl.LOGGER.warn("[getTripByRoute][Get Trips by Route ids warn][Trips not found][RouteIdNumber: {}]",routeIds.size());
            return new Response<>(0, noCnontent, null);
        }
    }

    @Override
    public Response create(edu.fudan.common.entity.TravelInfo info, HttpHeaders headers) {
        TripId ti = new TripId(info.getTripId());
        if (repository.findByTripId(ti) == null) {
            Trip trip = new Trip(ti, info.getTrainTypeName(), info.getStartStationName(),
                    info.getStationsName(), info.getTerminalStationName(), info.getStartTime(), info.getEndTime());
            trip.setRouteId(info.getRouteId());
            repository.save(trip);
            return new Response<>(1, "Create trip info:" + ti.toString() + ".", null);
        } else {
            TravelServiceImpl.LOGGER.error("[getTripByRoute][Create trip error][Trip already exists][TripId: {}]",info.getTripId());
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
            TravelServiceImpl.LOGGER.error("[retrieve][Retrieve trip error][Trip not found][TripId: {}]",tripId);
            return new Response<>(0, "No Content according to tripId" + tripId, null);
        }
    }

    @Override
    public Response update(edu.fudan.common.entity.TravelInfo info, HttpHeaders headers) {
        TripId ti = new TripId(info.getTripId());
        if (repository.findByTripId(ti) != null) {
            Trip trip = new Trip(ti, info.getTrainTypeName(), info.getStartStationName(),
                    info.getStationsName(), info.getTerminalStationName(), info.getStartTime(), info.getEndTime());
            trip.setRouteId(info.getRouteId());
            repository.save(trip);
            return new Response<>(1, "Update trip info:" + ti.toString(), trip);
        } else {
            TravelServiceImpl.LOGGER.error("[update][Update trip error][Trip not found][TripId: {}]",info.getTripId());
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
            TravelServiceImpl.LOGGER.error("[delete][Delete trip error][Trip not found][TripId: {}]",tripId);
            return new Response<>(0, "Trip " + tripId + " doesn't exist.", null);
        }
    }

    @Override
    public Response query(TripInfo info, HttpHeaders headers) {

        //Gets the start and arrival stations of the train number to query. The originating and arriving stations received here are both station names, so two requests need to be sent to convert to station ids
        String StartPlaceName = info.getStartPlace();
        String endPlaceName = info.getEndPlace();
//        String StartPlaceId = queryForStationId(StartPlaceName, headers);
//        String endPlaceId = queryForStationId(endPlaceName, headers);

        //This is the final result
        ArrayList<TripResponse> list = new ArrayList<>();

        //Check all train info
        ArrayList<Trip> allTripList = repository.findAll();
        if(allTripList != null){
            for (Trip tempTrip : allTripList) {
                //Get the detailed route list of this train
                Route tempRoute = getRouteByRouteId(tempTrip.getRouteId(), headers);
                //Check the route list for this train. Check that the required start and arrival stations are in the list of stops that are not on the route, and check that the location of the start station is before the stop
                //Trains that meet the above criteria are added to the return list
                if (tempRoute.getStations().contains(StartPlaceName) &&
                        tempRoute.getStations().contains(endPlaceName) &&
                        tempRoute.getStations().indexOf(StartPlaceName) < tempRoute.getStations().indexOf(endPlaceName)) {
                    TripResponse response = getTickets(tempTrip, tempRoute, StartPlaceName, endPlaceName, StartPlaceName, endPlaceName, info.getDepartureTime(), headers);
                    if (response == null) {
                        TravelServiceImpl.LOGGER.warn("[query][Query trip error][Tickets not found][start: {},end: {},time: {}]",info.getStartPlace(),info.getEndPlace(),info.getDepartureTime());
                        return new Response<>(0, noCnontent, null);
                    }
                    list.add(response);
                }
            }
        }
        return new Response<>(1, "Success Query", list);
    }

    @Override
    public Response getTripAllDetailInfo(TripAllDetailInfo gtdi, HttpHeaders headers) {
        TripAllDetail gtdr = new TripAllDetail();
        TravelServiceImpl.LOGGER.debug("[getTripAllDetailInfo][gtdi info: {}]", gtdi.toString());
        Trip trip = repository.findByTripId(new TripId(gtdi.getTripId()));
        if (trip == null) {
            gtdr.setTripResponse(null);
            gtdr.setTrip(null);
            TravelServiceImpl.LOGGER.error("[getTripAllDetailInfo][Get trip detail error][Trip not found][TripId: {}]",gtdi.getTripId());
        } else {
            String endPlaceName = gtdi.getTo();
            String StartPlaceName = gtdi.getFrom();
//            String StartPlaceId = queryForStationId(StartPlaceName, headers);
//            String endPlaceId = queryForStationId(endPlaceName, headers);
//            TravelServiceImpl.LOGGER.debug("[getTripAllDetailInfo][endPlaceID: {}]", endPlaceId);
            Route tempRoute = getRouteByRouteId(trip.getRouteId(), headers);
            TripResponse tripResponse = getTickets(trip, tempRoute, StartPlaceName, endPlaceName, gtdi.getFrom(), gtdi.getTo(), gtdi.getTravelDate(), headers);
            if (tripResponse == null) {
                gtdr.setTrip(null);
                gtdr.setTripResponse(null);
                TravelServiceImpl.LOGGER.warn("[getTripAllDetailInfo][Query trip error][Tickets not found][start: {},end: {}]", gtdi.getTo(), gtdi.getFrom());
            } else {
                gtdr.setTripResponse(tripResponse);
                gtdr.setTrip(repository.findByTripId(new TripId(gtdi.getTripId())));
            }
        }


        return new Response<>(1, success, gtdr);
    }


    private TripResponse getTickets(Trip trip, Route route, String StartPlaceId, String endPlaceId, String StartPlaceName, String endPlaceName, Date departureTime, HttpHeaders headers) {

        //Determine if the date checked is the same day and after
        if (!afterToday(departureTime)) {
            return null;
        }

        Travel query = new Travel();
        query.setTrip(trip);
        query.setStartPlace(StartPlaceName);
        query.setEndPlace(endPlaceName);
        query.setDepartureTime(departureTime);

        HttpEntity requestEntity = new HttpEntity(query, null);
        String basic_service_url = getServiceUrl("ts-basic-service");
        ResponseEntity<Response<edu.fudan.common.entity.TravelResult>> re = restTemplate.exchange(
                basic_service_url + "/api/v1/basicservice/basic/travel",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<edu.fudan.common.entity.TravelResult>>() {
                });
        TravelServiceImpl.LOGGER.debug("[getTickets][Ticket info  is: {}]", re.getBody().toString());
        edu.fudan.common.entity.TravelResult resultForTravel =  re.getBody().getData();



        //Ticket order _ high-speed train (number of tickets purchased)
        requestEntity = new HttpEntity(null);
        String order_other_service_url = getServiceUrl("ts-order-other-service");
        ResponseEntity<Response<edu.fudan.common.entity.SoldTicket>> re2 = restTemplate.exchange(
                order_other_service_url + "/api/v1/orderOtherService/orderOther/" + departureTime + "/" + trip.getTripId().toString(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<edu.fudan.common.entity.SoldTicket>>() {
                });
        TravelServiceImpl.LOGGER.debug("[getTickets][Order other Ticket info  is: {}]", re.getBody().toString());
        edu.fudan.common.entity.SoldTicket result = re2.getBody().getData();

        if (result == null) {
            TravelServiceImpl.LOGGER.warn("[getTickets][Get tickets warn][Sold ticket Info doesn't exist][Departure Time: {},TripId: {}]",departureTime,trip.getTripId());
            return null;
        }
        //Set the returned ticket information
        TripResponse response = new TripResponse();
        if (queryForStationId(StartPlaceName, headers).equals(trip.getStartStationName()) &&
                queryForStationId(endPlaceName, headers).equals(trip.getTerminalStationName())) {
            response.setEconomyClass(50);
            response.setConfortClass(50);
        } else {
            response.setConfortClass(50);
            response.setEconomyClass(50);
        }

        int first = getRestTicketNumber(departureTime, trip.getTripId().toString(),
                StartPlaceName, endPlaceName, edu.fudan.common.entity.SeatClass.FIRSTCLASS.getCode(), headers);

        int second = getRestTicketNumber(departureTime, trip.getTripId().toString(),
                StartPlaceName, endPlaceName, edu.fudan.common.entity.SeatClass.SECONDCLASS.getCode(), headers);
        response.setConfortClass(first);
        response.setEconomyClass(second);

        response.setStartStation(StartPlaceName);
        response.setTerminalStation(endPlaceName);

        //Calculate the distance from the starting point
        TravelServiceImpl.LOGGER.info("[getTickets][Calculate Distance][route: {} , station: {}]", route.getId(), route.getStations());
//        int indexStart = route.getStations().indexOf(StartPlaceId);
//        int indexEnd = route.getStations().indexOf(endPlaceId);
        int indexStart = route.getStations().indexOf(StartPlaceName);
        int indexEnd = route.getStations().indexOf(endPlaceName);
        int distanceStart = route.getDistances().get(indexStart) - route.getDistances().get(0);
        int distanceEnd = route.getDistances().get(indexEnd) - route.getDistances().get(0);
        edu.fudan.common.entity.TrainType trainType = getTrainType(trip.getTrainTypeName(), headers);
        //Train running time is calculated according to the average running speed of the train
        int minutesStart = 60 * distanceStart / trainType.getAverageSpeed();
        int minutesEnd = 60 * distanceEnd / trainType.getAverageSpeed();

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(trip.getStartTime());
        calendarStart.add(Calendar.MINUTE, minutesStart);
        response.setStartTime(calendarStart.getTime());
        TravelServiceImpl.LOGGER.info("[getTickets][Calculate Distance][calculate time：{} , time: {}]", minutesStart, calendarStart.getTime());

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(trip.getStartTime());
        calendarEnd.add(Calendar.MINUTE, minutesEnd);
        response.setEndTime(calendarEnd.getTime());
        TravelServiceImpl.LOGGER.info("[getTickets][Calculate Distance][calculate time：{} , time: {}]", minutesEnd, calendarEnd.getTime());

        response.setTripId(new TripId(result.getTrainNumber()));
        response.setTrainTypeName(trip.getTrainTypeName());
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
        TravelServiceImpl.LOGGER.warn("[queryAll][Query all trips warn][{}]","No Content");
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
        String train_service_url = getServiceUrl("ts-train-service");
        ResponseEntity<Response<edu.fudan.common.entity.TrainType>> re = restTemplate.exchange(
                train_service_url + "/api/v1/trainservice/trains/byName" + trainTypeId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<edu.fudan.common.entity.TrainType>>() {
                });

        return re.getBody().getData();
    }

    private String queryForStationId(String stationName, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(null);
        String basic_service_url = getServiceUrl("ts-basic-service");
        ResponseEntity<Response<String>> re = restTemplate.exchange(
                basic_service_url + "/api/v1/basicservice/basic/" + stationName,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<String>>() {
                });


        return re.getBody().getData();
    }

    private Route getRouteByRouteId(String routeId, HttpHeaders headers) {
        TravelServiceImpl.LOGGER.debug("[getRouteByRouteId][Get Route By Id][Route ID：{}]", routeId);
        HttpEntity requestEntity = new HttpEntity(null);
        String route_service_url = getServiceUrl("ts-route-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                route_service_url + "/api/v1/routeservice/routes/" + routeId,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response result = re.getBody();

        if (result.getStatus() == 0 ) {
            TravelServiceImpl.LOGGER.error("[getRouteByRouteId][Get Route By Id Fail][Route not found][RouteId: {}]", routeId);
            return null;
        } else {
            TravelServiceImpl.LOGGER.info("[getRouteByRouteId][Get Route By Id Success]");
            return JsonUtils.conveterObject(result.getData(), Route.class);
        }
    }

    private int getRestTicketNumber(Date travelDate, String trainNumber, String startStationName, String endStationName, int seatType, HttpHeaders headers) {
        edu.fudan.common.entity.Seat seatRequest = new edu.fudan.common.entity.Seat();

        String fromId = queryForStationId(startStationName, headers);
        String toId = queryForStationId(endStationName, headers);

        seatRequest.setDestStation(toId);
        seatRequest.setStartStation(fromId);
        seatRequest.setTrainNumber(trainNumber);
        seatRequest.setSeatType(seatType);
        seatRequest.setTravelDate(travelDate);
        TravelServiceImpl.LOGGER.info("[getRestTicketNumber][Seat request][request: {}]", seatRequest.toString());

        HttpEntity requestEntity = new HttpEntity(seatRequest, null);
        String seat_service_url = getServiceUrl("ts-seat-service");
        ResponseEntity<Response<Integer>> re = restTemplate.exchange(
                seat_service_url + "/api/v1/seatservice/seats/left_tickets",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<Integer>>() {
                });
        int restNumber =   re.getBody().getData();

        TravelServiceImpl.LOGGER.info("[getRestTicketNumber][Get Rest tickets num][num is: {}]", re.getBody().toString());
        return restNumber;
    }

    @Override
    public Response adminQueryAll(HttpHeaders headers) {
        List<Trip> trips = repository.findAll();
        ArrayList<AdminTrip> adminTrips = new ArrayList<>();
        if(trips != null){
            for (Trip trip : trips) {
                AdminTrip adminTrip = new AdminTrip();
                adminTrip.setRoute(getRouteByRouteId(trip.getRouteId(), headers));
                adminTrip.setTrainType(getTrainType(trip.getTrainTypeName(), headers));
                adminTrip.setTrip(trip);
                adminTrips.add(adminTrip);
            }
        }

        if (!adminTrips.isEmpty()) {
            return new Response<>(1, "Travel Service Admin Query All Travel Success", adminTrips);
        } else {
            TravelServiceImpl.LOGGER.warn("[adminQueryAll][Admin query all trips warn][{}]","No Content");
            return new Response<>(0, noCnontent, null);
        }
    }

}

