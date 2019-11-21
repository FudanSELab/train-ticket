package plan.service;

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
import plan.entity.*;

import java.util.*;


@Service
public class RoutePlanServiceImpl implements RoutePlanService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response searchCheapestResult(RoutePlanInfo info, HttpHeaders headers) {

        //1.暴力取出travel-service和travle2-service的所有结果
        TripInfo queryInfo = new TripInfo();
        queryInfo.setStartingPlace(info.getFromStationName());
        queryInfo.setEndPlace(info.getToStationName());
        queryInfo.setDepartureTime(info.getTravelDate());

        ArrayList<TripResponse> highSpeed = getTripFromHighSpeedTravelServive(queryInfo, headers);
        ArrayList<TripResponse> normalTrain = getTripFromNormalTrainTravelService(queryInfo, headers);

        //2.按照二等座位结果排序
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
        addUnit(returnResult, units, headers);

        return new Response<>(1,"Success",units);
}

    public void addUnit(List<TripResponse> returnResult, List<RoutePlanResultUnit> units, HttpHeaders headers) {
        for (int i = 0; i < returnResult.size(); i++) {
            TripResponse tempResponse = returnResult.get(i);

            RoutePlanResultUnit tempUnit = new RoutePlanResultUnit();
            tempUnit.setTripId(tempResponse.getTripId().toString());
            tempUnit.setTrainTypeId(tempResponse.getTrainTypeId());
            tempUnit.setFromStationName(tempResponse.getStartingStation());
            tempUnit.setToStationName(tempResponse.getTerminalStation());
            tempUnit.setStopStations(getStationList(tempResponse.getTripId().toString(), headers));
            tempUnit.setPriceForSecondClassSeat(tempResponse.getPriceForEconomyClass());
            tempUnit.setPriceForFirstClassSeat(tempResponse.getPriceForConfortClass());
            tempUnit.setEndTime(tempResponse.getEndTime());
            tempUnit.setStartingTime(tempResponse.getStartingTime());


            units.add(tempUnit);
        }
    }

    @Override
    public Response searchQuickestResult(RoutePlanInfo info, HttpHeaders headers) {

        //1.暴力取出travel-service和travel2-service的所有结果
        TripInfo queryInfo = new TripInfo();
        queryInfo.setStartingPlace(info.getFromStationName());
        queryInfo.setEndPlace(info.getToStationName());
        queryInfo.setDepartureTime(info.getTravelDate());

        ArrayList<TripResponse> highSpeed = getTripFromHighSpeedTravelServive(queryInfo, headers);
        ArrayList<TripResponse> normalTrain = getTripFromNormalTrainTravelService(queryInfo, headers);

        //2.按照时间排序
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
                if (thisRes.getEndTime().getTime() - thisRes.getStartingTime().getTime() < minTime) {
                    minTime = thisRes.getEndTime().getTime() - thisRes.getStartingTime().getTime();
                    minIndex = j;
                }
            }
            returnResult.add(finalResult.get(minIndex));
            finalResult.remove(minIndex);

        }


        ArrayList<RoutePlanResultUnit> units = new ArrayList<>();
        addUnit(returnResult, units, headers);
        return new Response<>(1, "Success", units);
    }

    @Override
    public Response searchMinStopStations(RoutePlanInfo info, HttpHeaders headers) {
        String fromStationId = queryForStationId(info.getFromStationName(), headers);
        String toStationId = queryForStationId(info.getToStationName(), headers);
        logger.info("From Id:{} To:{}", fromStationId, toStationId);
        //1.获取这个经过这两个车站的路线

        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<ArrayList<Route>>> re = restTemplate.exchange(
                "http://ts-route-service:11178/api/v1/routeservice/routes/" + fromStationId + "/" + toStationId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<Route>>>() {
                });


        ArrayList<Route> routeList = re.getBody().getData();
        logger.info("[Route Plan Service] Candidate Route Number:{}", routeList.size());
        //2.计算这两个车站之间有多少停靠站
        Map<Route, Integer> gapList = new HashMap<>();
        for (int i = 0; i < routeList.size(); i++) {
            int indexStart = routeList.get(i).getStations().indexOf(fromStationId);
            int indexEnd = routeList.get(i).getStations().indexOf(toStationId);
            gapList.put(routeList.get(i), indexEnd- indexStart);
        }
        //3.挑选出最少停靠站的几条路线
        ArrayList<String> resultRoutes = new ArrayList<>();
        int size = Math.min(5, routeList.size());
        ArrayList<Map.Entry<Route, Integer>> gaparrayList = new ArrayList<>(gapList.entrySet());
        Collections.sort(gaparrayList, Comparator.comparingInt(Map.Entry::getValue));
        for (int i = 0; i < size; i++) {
            resultRoutes.add(gaparrayList.get(i).getKey().getId());
        }
        //4.根据路线，去travel或者travel2获取这些车次信息
        requestEntity = new HttpEntity(resultRoutes, headers);
        ResponseEntity<Response<ArrayList<ArrayList<Trip>>>> re2 = restTemplate.exchange(
                "http://ts-travel-service:12346/api/v1/travelservice/trips/routes",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<ArrayList<Trip>>>>() {
                });

        ArrayList<ArrayList<Trip>> travelTrips = re2.getBody().getData();


        re2 = restTemplate.exchange(
                "http://ts-travel2-service:16346/api/v1/travel2service/trips/routes",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<ArrayList<Trip>>>>() {
                });
        ArrayList<ArrayList<Trip>> travel2Trips = re2.getBody().getData();

        //合并查询结果
        ArrayList<ArrayList<Trip>> finalTripResult = new ArrayList<>();
        ArrayList<Trip> trips = new ArrayList<>();
        for (int i = 0; i < travel2Trips.size(); i++) {
            ArrayList<Trip> tempList = travel2Trips.get(i);
            tempList.addAll(travelTrips.get(i));
            trips.addAll(tempList);
            finalTripResult.add(tempList);
        }
        logger.info("[Route Plan Service] Trips Num:{}", finalTripResult.size());

        //5.再根据这些车次信息获取其价格和停靠站信息
        ArrayList<RoutePlanResultUnit> tripResponses = new ArrayList<>();

        ResponseEntity<Response<TripAllDetail>> re3;
        for (Trip trip : trips) {
            TripResponse tripResponse;
            TripAllDetailInfo allDetailInfo = new TripAllDetailInfo();
            allDetailInfo.setTripId(trip.getTripId().toString());
            allDetailInfo.setTravelDate(info.getTravelDate());
            allDetailInfo.setFrom(info.getFromStationName());
            allDetailInfo.setTo(info.getToStationName());
            requestEntity = new HttpEntity(allDetailInfo, headers);
            String requestUrl = "";
            if (trip.getTripId().toString().charAt(0) == 'D' || trip.getTripId().toString().charAt(0) == 'G') {
                requestUrl = "http://ts-travel-service:12346/api/v1/travelservice/trip_detail";
            } else {
                requestUrl = "http://ts-travel2-service:16346/api/v1/travel2service/trip_detail";
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
            unit.setTrainTypeId(tripResponse.getTrainTypeId());
            unit.setFromStationName(tripResponse.getStartingStation());
            unit.setToStationName(tripResponse.getTerminalStation());
            unit.setStartingTime(tripResponse.getStartingTime());
            unit.setEndTime(tripResponse.getEndTime());
            unit.setPriceForFirstClassSeat(tripResponse.getPriceForConfortClass());
            unit.setPriceForSecondClassSeat(tripResponse.getPriceForEconomyClass());
            //根据routeid去拿路线图
            String routeId = trip.getRouteId();
            Route tripRoute = getRouteByRouteId(routeId, headers);
            try {
                if (tripRoute == null) {
                    throw new NullPointerException();
                } else {
                    unit.setStopStations(tripRoute.getStations());
                }

            } catch (NullPointerException e) {
                logger.error(e.toString());
            }
            tripResponses.add(unit);
        }
        logger.info("[Route Plan Service] Trips Response Unit Num:{}", tripResponses.size());
        return new Response<>(1, "Success.", tripResponses);
    }

    private String queryForStationId(String stationName, HttpHeaders headers) {
        logger.info("[Preserve Service][Get Station Name]");

        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<String>> re = restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations/id/" + stationName,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<String>>() {
                });
        return re.getBody().getData();
    }

    private Route getRouteByRouteId(String routeId, HttpHeaders headers) {
        logger.info("[Route Plan Service][Get Route By Id] Route ID：{}", routeId);
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<Route>> re = restTemplate.exchange(
                "http://ts-route-service:11178/api/v1/routeservice/routes/" + routeId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Route>>() {
                });
        Response<Route> result = re.getBody();

        if (result.getStatus() == 0) {
            logger.info("[Travel Service][Get Route By Id] Fail.{}", result.getMsg());
            return null;
        } else {
            logger.info("[Travel Service][Get Route By Id] Success.");
            return result.getData();
        }
    }

    private ArrayList<TripResponse> getTripFromHighSpeedTravelServive(TripInfo info, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(info, headers);

        logger.info("info : {}  {}  {}", info.getStartingPlace(), info.getEndPlace(), info.getDepartureTime());
        ResponseEntity<Response<ArrayList<TripResponse>>> re = restTemplate.exchange(
                "http://ts-travel-service:12346/api/v1/travelservice/trips/left",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<TripResponse>>>() {
                });

        ArrayList<TripResponse> tripResponses = re.getBody().getData();
        logger.info("[Route Plan Get Trip][Size]{}", tripResponses.size());
        return tripResponses;
    }

    private ArrayList<TripResponse> getTripFromNormalTrainTravelService(TripInfo info, HttpHeaders headers) {
        HttpEntity requestEntity = new HttpEntity(info, headers);

        ResponseEntity<Response<ArrayList<TripResponse>>> re = restTemplate.exchange(
                "http://ts-travel2-service:16346/api/v1/travel2service/trips/left",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Response<ArrayList<TripResponse>>>() {
                });
        ArrayList<TripResponse> list = re.getBody().getData();
        logger.info("[Route Plan Get TripOther][Size]{}", list.size());
        return list;
    }

    private List<String> getStationList(String tripId, HttpHeaders headers) {

        String path;
        if (tripId.charAt(0) == 'G' || tripId.charAt(0) == 'D') {
            path = "http://ts-travel-service:12346/api/v1/travelservice/routes/" + tripId;
        } else {
            path = "http://ts-travel2-service:16346/api/v1/travel2service/routes/" + tripId;
        }
        HttpEntity requestEntity = new HttpEntity(headers);
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
