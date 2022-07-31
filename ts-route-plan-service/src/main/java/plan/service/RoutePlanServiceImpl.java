package plan.service;

import edu.fudan.common.entity.*;
import edu.fudan.common.util.Response;
import edu.fudan.common.util.StringUtils;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fdse
 */
@Service
public class RoutePlanServiceImpl implements RoutePlanService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(RoutePlanServiceImpl.class);

    private String getServiceUrl(String serviceName) {
        return "http://" + serviceName;
    }

    @Override
    public Response searchCheapestResult(RoutePlanInfo info, HttpHeaders headers) {

        //1.Violence pulls out all the results of travel-service and travle2-service
        TripInfo queryInfo = new TripInfo();
        queryInfo.setStartPlace(info.getStartStation());
        queryInfo.setEndPlace(info.getEndStation());
        queryInfo.setDepartureTime(info.getTravelDate());

        ArrayList<TripResponse> highSpeed = getTripFromHighSpeedTravelServive(queryInfo, headers);
        ArrayList<TripResponse> normalTrain = getTripFromNormalTrainTravelService(queryInfo, headers);

        //2.Sort by second-class seats
        ArrayList<TripResponse> finalResult = new ArrayList<>();
        finalResult.addAll(highSpeed);
        finalResult.addAll(normalTrain);

        float minPrice;
        int minIndex = -1;
        int size = Math.min(5, finalResult.size());
        ArrayList<TripResponse> returnResult = new ArrayList<>();
        for (int i = 0; i < size; i++) {

            minPrice = Float.MAX_VALUE;
            for (int j = 0; j < finalResult.size(); j++) {
                TripResponse thisRes = finalResult.get(j);
                if (Float.parseFloat(thisRes.getPriceForEconomyClass()) < minPrice) {
                    minPrice = Float.parseFloat(finalResult.get(j).getPriceForEconomyClass());
                    minIndex = j;
                }
            }
            returnResult.add(finalResult.get(minIndex));
            finalResult.remove(minIndex);
        }


        ArrayList<RoutePlanResultUnit> units = new ArrayList<>();
        for (int i = 0; i < returnResult.size(); i++) {
            TripResponse tempResponse = returnResult.get(i);

            RoutePlanResultUnit tempUnit = new RoutePlanResultUnit();
            tempUnit.setTripId(tempResponse.getTripId().toString());
            tempUnit.setTrainTypeName(tempResponse.getTrainTypeName());
            tempUnit.setStartStation(tempResponse.getStartStation());
            tempUnit.setEndStation(tempResponse.getTerminalStation());
            tempUnit.setStopStations(getStationList(tempResponse.getTripId().toString(), headers));
            tempUnit.setPriceForSecondClassSeat(tempResponse.getPriceForEconomyClass());
            tempUnit.setPriceForFirstClassSeat(tempResponse.getPriceForConfortClass());
            tempUnit.setEndTime(tempResponse.getEndTime());
            tempUnit.setStartTime(tempResponse.getStartTime());

            units.add(tempUnit);
        }

        return new Response<>(1, "Success", units);
    }

    @Override
    public Response searchQuickestResult(RoutePlanInfo info, HttpHeaders headers) {

        //1.Violence pulls out all the results of travel-service and travle2-service
        TripInfo queryInfo = new TripInfo();
        queryInfo.setStartPlace(info.getStartStation());
        queryInfo.setEndPlace(info.getEndStation());
        queryInfo.setDepartureTime(info.getTravelDate());

        ArrayList<TripResponse> highSpeed = getTripFromHighSpeedTravelServive(queryInfo, headers);
        ArrayList<TripResponse> normalTrain = getTripFromNormalTrainTravelService(queryInfo, headers);

        //2.Sort by time
        ArrayList<TripResponse> finalResult = new ArrayList<>();

        for (TripResponse tr : highSpeed) {
            finalResult.add(tr);
        }
        for (TripResponse tr : normalTrain) {
            finalResult.add(tr);
        }

        long minTime;
        int minIndex = -1;
        int size = Math.min(finalResult.size(), 5);
        ArrayList<TripResponse> returnResult = new ArrayList<>();
        for (int i = 0; i < size; i++) {

            minTime = Long.MAX_VALUE;
            for (int j = 0; j < finalResult.size(); j++) {
                TripResponse thisRes = finalResult.get(j);
                Date endTime = StringUtils.String2Date(thisRes.getEndTime());
                Date startTime = StringUtils.String2Date(thisRes.getStartTime());
                if (endTime.getTime() - startTime.getTime() < minTime) {
                    minTime = endTime.getTime() - startTime.getTime();
                    minIndex = j;
                }
            }
            returnResult.add(finalResult.get(minIndex));
            finalResult.remove(minIndex);

        }


        ArrayList<RoutePlanResultUnit> units = new ArrayList<>();
        for (int i = 0; i < returnResult.size(); i++) {
            TripResponse tempResponse = returnResult.get(i);

            RoutePlanResultUnit tempUnit = new RoutePlanResultUnit();
            tempUnit.setTripId(tempResponse.getTripId().toString());
            tempUnit.setTrainTypeName(tempResponse.getTrainTypeName());
            tempUnit.setStartStation(tempResponse.getStartStation());
            tempUnit.setEndStation(tempResponse.getTerminalStation());

            tempUnit.setStopStations(getStationList(tempResponse.getTripId().toString(), headers));

            tempUnit.setPriceForSecondClassSeat(tempResponse.getPriceForEconomyClass());
            tempUnit.setPriceForFirstClassSeat(tempResponse.getPriceForConfortClass());
            tempUnit.setStartTime(tempResponse.getStartTime());
            tempUnit.setEndTime(tempResponse.getEndTime());
            units.add(tempUnit);
        }
        return new Response<>(1, "Success", units);
    }

    @Override
    public Response searchMinStopStations(RoutePlanInfo info, HttpHeaders headers) {
        String fromStationId = info.getStartStation();
        String toStationId = info.getEndStation();
        RoutePlanServiceImpl.LOGGER.info("[searchMinStopStations][Start and Finish][From Id: {} To: {}]", fromStationId , toStationId);
        //1.Get the route through the two stations

        HttpEntity requestEntity = new HttpEntity(null);
        String route_service_url = getServiceUrl("ts-route-service");
        ResponseEntity<Response<ArrayList<Route>>> re = restTemplate.exchange(
                route_service_url + "/api/v1/routeservice/routes/" + info.getStartStation() + "/" + info.getEndStation(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<Route>>>() {
                });


        ArrayList<Route> routeList = re.getBody().getData();
        RoutePlanServiceImpl.LOGGER.info("[searchMinStopStations][Get the route][Candidate Route Number: {}]", routeList.size());
        //2.Calculate how many stops there are between the two stations
        ArrayList<Integer> gapList = new ArrayList<>();
        for (int i = 0; i < routeList.size(); i++) {
            int indexStart = routeList.get(i).getStations().indexOf(fromStationId);
            int indexEnd = routeList.get(i).getStations().indexOf(toStationId);
            gapList.add(indexEnd - indexStart);
        }
        //3.Pick the routes with the fewest stops
        ArrayList<String> resultRoutes = new ArrayList<>();
        int size = Math.min(5, routeList.size());
        for (int i = 0; i < size; i++) {
            int minIndex = 0;
            int tempMinGap = Integer.MAX_VALUE;
            for (int j = 0; j < gapList.size(); j++) {
                if (gapList.get(j) < tempMinGap) {
                    tempMinGap = gapList.get(j);
                    minIndex = j;
                }
            }
            resultRoutes.add(routeList.get(minIndex).getId());
            routeList.remove(minIndex);
            gapList.remove(minIndex);
        }
        //4.Depending on the route, go to travel-service or travel2service to get the train information
        requestEntity = new HttpEntity(resultRoutes, null);
        String travel_service_url=getServiceUrl("ts-travel-service");
        ResponseEntity<Response<ArrayList<ArrayList<Trip>>>> re2 = restTemplate.exchange(
                travel_service_url + "/api/v1/travelservice/trips/routes",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<ArrayList<Trip>>>>() {
                });

        ArrayList<ArrayList<Trip>> travelTrips = re2.getBody().getData();

        String travel2_service_url=getServiceUrl("ts-travel2-service");
        re2 = restTemplate.exchange(
                travel2_service_url + "/api/v1/travel2service/trips/routes",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<ArrayList<Trip>>>>() {
                });
        ArrayList<ArrayList<Trip>> travel2Trips = re2.getBody().getData();

        //Merge query results
        ArrayList<ArrayList<Trip>> finalTripResult = new ArrayList<>();
        for (int i = 0; i < travel2Trips.size(); i++) {
            ArrayList<Trip> tempList = travel2Trips.get(i);
            tempList.addAll(travelTrips.get(i));
            finalTripResult.add(tempList);
        }
        RoutePlanServiceImpl.LOGGER.info("[searchMinStopStations][Get train Information][Trips Num: {}]", finalTripResult.size());
        //5.Then, get the price and the station information according to the train information
        ArrayList<Trip> trips = new ArrayList<>();
        for (ArrayList<Trip> tempTrips : finalTripResult) {
            trips.addAll(tempTrips);
        }
        ArrayList<RoutePlanResultUnit> tripResponses = new ArrayList<>();

        ResponseEntity<Response<TripAllDetail>> re3;
        for (Trip trip : trips) {
            TripResponse tripResponse;
            TripAllDetailInfo allDetailInfo = new TripAllDetailInfo();
            allDetailInfo.setTripId(trip.getTripId().toString());
            allDetailInfo.setTravelDate(info.getTravelDate());
            allDetailInfo.setFrom(info.getStartStation());
            allDetailInfo.setTo(info.getEndStation());
            requestEntity = new HttpEntity(allDetailInfo, null);
            String requestUrl = "";
            if (trip.getTripId().toString().charAt(0) == 'D' || trip.getTripId().toString().charAt(0) == 'G') {
                requestUrl = travel_service_url + "/api/v1/travelservice/trip_detail";
            } else {
                requestUrl = travel2_service_url + "/api/v1/travel2service/trip_detail";
            }
            re3 = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Response<TripAllDetail>>() {
                    });

            TripAllDetail tripAllDetail = re3.getBody().getData();
            tripResponse = tripAllDetail.getTripResponse();


            RoutePlanResultUnit unit = new RoutePlanResultUnit();
            unit.setTripId(trip.getTripId().toString());
            unit.setTrainTypeName(tripResponse.getTrainTypeName());
            unit.setStartStation(tripResponse.getStartStation());
            unit.setEndStation(tripResponse.getTerminalStation());
            unit.setStartTime(tripResponse.getStartTime());
            unit.setEndTime(tripResponse.getEndTime());
            unit.setPriceForFirstClassSeat(tripResponse.getPriceForConfortClass());
            unit.setPriceForSecondClassSeat(tripResponse.getPriceForEconomyClass());
            //Go get the roadmap according to routeid
            String routeId = trip.getRouteId();
            Route tripRoute = getRouteByRouteId(routeId, headers);
            if (tripRoute != null) {
                unit.setStopStations(tripRoute.getStations());
            }

            tripResponses.add(unit);
        }
        RoutePlanServiceImpl.LOGGER.info("[searchMinStopStations][Trips Response Unit Num: {}]", tripResponses.size());
        return new Response<>(1, "Success.", tripResponses);
    }

    private Route getRouteByRouteId(String routeId, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(null);
        String route_service_url = getServiceUrl("ts-route-service");
        ResponseEntity<Response<Route>> re = restTemplate.exchange(
                route_service_url + "/api/v1/routeservice/routes/" + routeId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Route>>() {
                });
        Response<Route> result = re.getBody();

        if (result.getStatus() == 0) {
            RoutePlanServiceImpl.LOGGER.error("[getRouteByRouteId][Get Route By Id Fail][RouteId: {}]", routeId);
            return null;
        } else {
            RoutePlanServiceImpl.LOGGER.info("[getRouteByRouteId][Get Route By Id Success]");
            return result.getData();
        }
    }

    private ArrayList<TripResponse> getTripFromHighSpeedTravelServive(TripInfo info, HttpHeaders headers) {
        RoutePlanServiceImpl.LOGGER.info("[getTripFromHighSpeedTravelServive][trip info: {}]", info);
        HttpEntity requestEntity = new HttpEntity(info, null);
        String travel_service_url=getServiceUrl("ts-travel-service");
        ResponseEntity<Response<ArrayList<TripResponse>>> re = restTemplate.exchange(
                travel_service_url + "/api/v1/travelservice/trips/left",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<TripResponse>>>() {
                });

        ArrayList<TripResponse> tripResponses = re.getBody().getData();
        RoutePlanServiceImpl.LOGGER.info("[getTripFromHighSpeedTravelServive][Route Plan Get Trip][Size:{}]", tripResponses.size());
        return tripResponses;
    }

    private ArrayList<TripResponse> getTripFromNormalTrainTravelService(TripInfo info, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(info, null);
        String travel2_service_url=getServiceUrl("ts-travel2-service");
        ResponseEntity<Response<ArrayList<TripResponse>>> re = restTemplate.exchange(
                travel2_service_url + "/api/v1/travel2service/trips/left",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<TripResponse>>>() {
                });
        ArrayList<TripResponse> list = re.getBody().getData();
        RoutePlanServiceImpl.LOGGER.info("[getTripFromNormalTrainTravelService][Route Plan Get TripOther][Size:{}]", list.size());
        return list;
    }

    private List<String> getStationList(String tripId, HttpHeaders headers) {

        String path;
        String travel_service_url=getServiceUrl("ts-travel-service");
        String travel2_service_url=getServiceUrl("ts-travel2-service");
        if (tripId.charAt(0) == 'G' || tripId.charAt(0) == 'D') {
            path = travel_service_url + "/api/v1/travelservice/routes/" + tripId;
        } else {
            path = travel2_service_url + "/api/v1/travel2service/routes/" + tripId;
        }
        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response<Route>> re = restTemplate.exchange(
                path,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Route>>() {
                });
        Route route = re.getBody().getData();
        return route.getStations();
    }
}
