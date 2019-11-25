package travelto.service;

import edu.fudan.common.util.JsonUtils;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import travelto.entity.*;
import travelto.repository.TripRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Travel2ServiceImpl class
 *
 * @author fdu
 * @date 2019/11/10
 */
@Service
public class TravelToServiceImpl implements Travel2Service {
    private static Logger logger = Logger.getLogger(TravelToServiceImpl.class.getName());

    private static final String SUCCESS = "Success";
    private static final String NO_CONTENT = "No Content";

    @Autowired
    TripRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public void testInit(TripRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Response getRouteByTripId(String tripId, HttpHeaders headers) {
        TripId tripId1 = new TripId(tripId);

        Trip trip = repository.findByTripId(tripId1);
        if (trip == null) {
            logger.log(Level.INFO, () -> "[Get Route By Trip ID] Trip Not Found:" + tripId);
            return new Response<>(0, "\"[Get Route By Trip ID] Trip Not Found:\" + tripId", null);
        } else {
            Route route = getRouteByRouteId(trip.getRouteId(), headers);
            if (route == null) {
                return new Response<>(0, "\"[Get Route By Trip ID] Route Not Found:\" + trip.getRouteId()", null);
            } else {
                logger.log(Level.INFO, () -> "[Get Route By Trip ID] Success");
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
        if (trainType != null) {
            return new Response<>(1, "Success query Train by trip id", trainType);
        } else {
            return new Response<>(0, NO_CONTENT, null);
        }
    }

    @Override
    public Response getTripByRoute(List<String> routeIds, HttpHeaders headers) {
        ArrayList<ArrayList<Trip>> tripList = new ArrayList<>();
        for (String routeId : routeIds) {
            ArrayList<Trip> tempTripList = repository.findByRouteId(routeId);
            if (tempTripList == null) {
                tempTripList = new ArrayList<>();
            }
            tripList.add(tempTripList);
        }
        if (!tripList.isEmpty()) {
            return new Response<>(1, SUCCESS, tripList);
        } else {
            return new Response<>(0, NO_CONTENT, null);
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
            return new Response<>(0, "Trip " + tripId + " doesn't exist.", null);
        }
    }

    @Override
    public Response query(TripInfo info, HttpHeaders headers) {

        //获取要查询的车次的起始站和到达站。这里收到的起始站和到达站都是站的名称，所以需要发两个请求转换成站的id
        String startingPlaceName = info.getStartingPlace();
        String endPlaceName = info.getEndPlace();
        String startingPlaceId = queryForStationId(startingPlaceName, headers);
        String endPlaceId = queryForStationId(endPlaceName, headers);

        //这个是最终的结果
        ArrayList<TripResponse> list = new ArrayList<>();

        //查询所有的车次信息
        ArrayList<Trip> allTripList = repository.findAll();
        for (Trip tempTrip : allTripList) {
            //拿到这个车次的具 体路线表
            Route tempRoute = getRouteByRouteId(tempTrip.getRouteId(), headers);
            //检查这个车次的路线表。检查要求的起始站和到达站在不在车次路线的停靠站列表中
            //并检查起始站的位置在到达站之前。满足以上条件的车次被加入返回列表
            if (tempRoute == null) {
                return new Response<>(0, NO_CONTENT, null);
            }
            if (tempRoute.getStations().contains(startingPlaceId) &&
                    tempRoute.getStations().contains(endPlaceId) &&
                    tempRoute.getStations().indexOf(startingPlaceId) < tempRoute.getStations().indexOf(endPlaceId)) {
                TripResponse response = getTickets(new PlaceInfoWrapper(tempTrip, tempRoute, startingPlaceId, endPlaceId, startingPlaceName, endPlaceName), info.getDepartureTime(), headers);
                if (response == null) {
                    return new Response<>(0, NO_CONTENT, null);
                }
                list.add(response);
            }
        }
        return new Response<>(1, "Success Query", list);
    }

    @Override
    public Response getTripAllDetailInfo(TripAllDetailInfo gtdi, HttpHeaders headers) {
        TripAllDetail gtdr = new TripAllDetail();
        logger.log(Level.INFO, () -> "[TravelService] [getTripAllDetailInfo] gtdi info:" + gtdi.toString());
        Trip trip = repository.findByTripId(new TripId(gtdi.getTripId()));
        if (trip == null) {
            gtdr.setTripResponse(null);
            gtdr.setTrip(null);
        } else {
            String endPlaceName = gtdi.getTo();
            String startingPlaceName = gtdi.getFrom();
            String startingPlaceId = queryForStationId(startingPlaceName, headers);
            String endPlaceId = queryForStationId(endPlaceName, headers);
            logger.log(Level.INFO, () -> "[TravelService] [getTripAllDetailInfo] endPlaceID: " + endPlaceId);
            Route tempRoute = getRouteByRouteId(trip.getRouteId(), headers);
            TripResponse tripResponse = getTickets(new PlaceInfoWrapper(trip, tempRoute, startingPlaceId, endPlaceId, gtdi.getFrom(), gtdi.getTo()), gtdi.getTravelDate(), headers);
            if (tripResponse == null) {
                gtdr.setTrip(null);
                gtdr.setTripResponse(null);
            } else {
                gtdr.setTripResponse(tripResponse);
                gtdr.setTrip(repository.findByTripId(new TripId(gtdi.getTripId())));
            }
        }


        return new Response<>(1, SUCCESS, gtdr);
    }

    private TravelResult getTravelResult(HttpEntity requestEntity) {
        ResponseEntity<Response<TravelResult>> re = restTemplate.exchange(
                "http://ts-ticketinfo-service:15681/api/v1/ticketinfoservice/ticketinfo",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<TravelResult>>() {
                });
        logger.log(Level.INFO, () -> "Ticket info  is: " + re.getBody().toString());
        return re.getBody().getData();
    }

    private SoldTicket getSoldTicket(HttpHeaders headers, Trip trip, Date departureTime) {
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<SoldTicket>> re2 = restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/" + departureTime + "/" + trip.getTripId().toString(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<SoldTicket>>() {
                });
        logger.log(Level.INFO, () -> "Order other Ticket info  is: " + re2.getBody().toString());
        return re2.getBody().getData();
    }

    private TripResponse getTripResponse(HttpHeaders headers, PlaceInfoWrapper wrapper, Date departureTime) {
        Trip trip = wrapper.trip;
        String startingPlaceName = wrapper.startingPlaceName;
        String endPlaceName = wrapper.endPlaceName;
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
        return response;
    }

    private TripResponse getFinalResponse(HttpHeaders headers, TripResponse response, PlaceInfoWrapper wrapper, TravelResult resultForTravel, SoldTicket result) {
        Trip trip = wrapper.trip;
        Route route = wrapper.route;
        String startingPlaceId = wrapper.startingPlaceId;
        String endPlaceId = wrapper.endPlaceId;
        logger.log(Level.INFO, () -> "[TravelService][getTickets] route: " + route.getId() + " " + "stations: " + route.getStations());
        int indexStart = route.getStations().indexOf(startingPlaceId);
        int indexEnd = route.getStations().indexOf(endPlaceId);
        int distanceStart = route.getDistances().get(indexStart) - route.getDistances().get(0);
        int distanceEnd = route.getDistances().get(indexEnd) - route.getDistances().get(0);
        TrainType trainType = getTrainType(trip.getTrainTypeId(), headers);
        //根据列车平均运行速度计算列车运行时间
        int minutesStart = 60 * distanceStart / trainType.getAverageSpeed();
        int minutesEnd = 60 * distanceEnd / trainType.getAverageSpeed();

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(trip.getStartingTime());
        calendarStart.add(Calendar.MINUTE, minutesStart);
        response.setStartingTime(calendarStart.getTime());
        logger.log(Level.INFO, () -> "[Train Service]计算时间：" + minutesStart + " 时间:" + calendarStart.getTime().toString());

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(trip.getStartingTime());
        calendarEnd.add(Calendar.MINUTE, minutesEnd);
        response.setEndTime(calendarEnd.getTime());
        logger.log(Level.INFO, () -> "[Train Service]计算时间：" + minutesEnd + " 时间:" + calendarEnd.getTime().toString());

        response.setTripId(new TripId(result.getTrainNumber()));
        response.setTrainTypeId(trip.getTrainTypeId());
        response.setPriceForConfortClass(resultForTravel.getPrices().get("confortClass"));
        response.setPriceForEconomyClass(resultForTravel.getPrices().get("economyClass"));

        return response;
    }

    private TripResponse getTickets(PlaceInfoWrapper wrapper, Date departureTime, HttpHeaders headers) {
        Trip trip = wrapper.trip;
        Route route = wrapper.route;
        String startingPlaceId = wrapper.startingPlaceId;
        String endPlaceId = wrapper.endPlaceId;
        String startingPlaceName = wrapper.startingPlaceName;
        String endPlaceName = wrapper.endPlaceName;
        //判断所查日期是否在当天及之后
        if (!afterToday(departureTime)) {
            return null;
        }

        Travel query = new Travel();
        query.setTrip(trip);
        query.setStartingPlace(startingPlaceName);
        query.setEndPlace(endPlaceName);
        query.setDepartureTime(departureTime);

        HttpEntity requestEntity = new HttpEntity(query, headers);
        TravelResult resultForTravel = getTravelResult(requestEntity);
        //车票订单_高铁动车（已购票数）
        SoldTicket result = getSoldTicket(headers, trip, departureTime);
        if (result == null) {
            logger.log(Level.INFO, () -> "soldticket Info doesn't exist");
            return null;
        }
        //设置返回的车票信息
        TripResponse response = getTripResponse(headers, new PlaceInfoWrapper(trip, startingPlaceName, endPlaceName), departureTime);
        //计算车从起始站开出的距离
        return getFinalResponse(headers, response, new PlaceInfoWrapper(trip, route, startingPlaceId, endPlaceId), resultForTravel, result);
    }

    @Override
    public Response queryAll(HttpHeaders headers) {
        List<Trip> tripList = repository.findAll();
        if (tripList != null && !tripList.isEmpty()) {
            return new Response<>(1, SUCCESS, tripList);
        }
        return new Response<>(0, NO_CONTENT, null);
    }

    private static boolean afterToday(Date date) {
        Calendar calendarToday = Calendar.getInstance();
        Date today = new Date();
        calendarToday.setTime(today);

        Calendar calendarOther = Calendar.getInstance();
        calendarOther.setTime(date);

        if (calendarToday.get(Calendar.YEAR) > calendarOther.get(Calendar.YEAR)) {
            return false;
        } else if (calendarToday.get(Calendar.YEAR) == calendarOther.get(Calendar.YEAR)) {
            if (calendarToday.get(Calendar.MONTH) > calendarOther.get(Calendar.MONTH)) {
                return false;
            } else if (calendarToday.get(Calendar.MONTH) == calendarOther.get(Calendar.MONTH)) {
                return calendarToday.get(Calendar.DAY_OF_MONTH) <= calendarOther.get(Calendar.DAY_OF_MONTH);
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private TrainType getTrainType(String trainTypeId, HttpHeaders headers) {

        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<TrainType>> re = restTemplate.exchange(
                "http://ts-train-service:14567/api/v1/trainservice/trains/" + trainTypeId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<TrainType>>() {
                });

        return re.getBody().getData();
    }

    private String queryForStationId(String stationName, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<String>> re = restTemplate.exchange(
                "http://ts-ticketinfo-service:15681/api/v1/ticketinfoservice/ticketinfo/" + stationName,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<String>>() {
                });


        return re.getBody().getData();
    }

    private Route getRouteByRouteId(String routeId, HttpHeaders headers) {
        logger.log(Level.INFO, () -> "[Travel Service][Get Route By Id] Route ID：" + routeId);
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-route-service:11178/api/v1/routeservice/routes/" + routeId,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response result = re.getBody();

        if (result.getStatus() == 0) {
            logger.log(Level.INFO, () -> "[Travel Other Service][Get Route By Id] Fail." + result.getMsg());
            return null;
        } else {
            logger.log(Level.INFO, () -> "[Travel Other Service][Get Route By Id] Success.");
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
        logger.log(Level.INFO, () -> "Seat request To String: " + seatRequest.toString());

        HttpEntity requestEntity = new HttpEntity(seatRequest, headers);
        ResponseEntity<Response<Integer>> re = restTemplate.exchange(
                "http://ts-seat-service:18898/api/v1/seatservice/seats/left_tickets",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<Integer>>() {
                });
        int restNumber = re.getBody().getData();

        logger.log(Level.INFO, () -> "Get Rest tickets num is: " + re.getBody().toString());
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
            return new Response<>(0, NO_CONTENT, null);
        }
    }

    public static class PlaceInfoWrapper {
        Trip trip;
        Route route;
        String startingPlaceId;
        String endPlaceId;
        String startingPlaceName;
        String endPlaceName;

        public PlaceInfoWrapper(Trip trip, Route route, String startingPlaceId, String endPlaceId, String startingPlaceName, String endPlaceName) {
            this.trip = trip;
            this.route = route;
            this.startingPlaceId = startingPlaceId;
            this.endPlaceId = endPlaceId;
            this.startingPlaceName = startingPlaceName;
            this.endPlaceName = endPlaceName;
        }

        public PlaceInfoWrapper(Trip trip, Route route, String startingPlaceId, String endPlaceId) {
            this.trip = trip;
            this.route = route;
            this.startingPlaceId = startingPlaceId;
            this.endPlaceId = endPlaceId;
            this.startingPlaceName = "";
            this.endPlaceName = "";
        }

        public PlaceInfoWrapper(Trip trip, String startingPlaceName, String endPlaceName) {
            this.trip = trip;
            this.route = null;
            this.startingPlaceId = "";
            this.endPlaceId = "";
            this.startingPlaceName = startingPlaceName;
            this.endPlaceName = endPlaceName;
        }

    }

}

