package fdse.microservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fudan.common.entity.*;
import edu.fudan.common.util.JsonUtils;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author fdse
 */
@Service
public class BasicServiceImpl implements BasicService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicServiceImpl.class);

    private String getServiceUrl(String serviceName) {
        return "http://" + serviceName;
    }

    @Override
    public Response queryForTravel(Travel info, HttpHeaders headers) {

        Response response = new Response<>();
        TravelResult result = new TravelResult();
        result.setStatus(true);
        response.setStatus(1);
        response.setMsg("Success");
        String start = info.getStartPlace();
        String end = info.getEndPlace();
        boolean startingPlaceExist = checkStationExists(start, headers);
        boolean endPlaceExist = checkStationExists(end, headers);
        if (!startingPlaceExist || !endPlaceExist) {
            result.setStatus(false);
            response.setStatus(0);
            response.setMsg("Start place or end place not exist!");
            if (!startingPlaceExist)
                BasicServiceImpl.LOGGER.warn("[queryForTravel][Start place not exist][start place: {}]", info.getStartPlace());
            if (!endPlaceExist)
                BasicServiceImpl.LOGGER.warn("[queryForTravel][End place not exist][end place: {}]", info.getEndPlace());
        }

        TrainType trainType = queryTrainTypeByName(info.getTrip().getTrainTypeName(), headers);
        if (trainType == null) {
            BasicServiceImpl.LOGGER.warn("[queryForTravel][traintype doesn't exist][trainTypeName: {}]", info.getTrip().getTrainTypeName());
            result.setStatus(false);
            response.setStatus(0);
            response.setMsg("Train type doesn't exist");
            return response;
        } else {
            result.setTrainType(trainType);
        }

        String routeId = info.getTrip().getRouteId();
        Route route = getRouteByRouteId(routeId, headers);
        if(route == null){
            result.setStatus(false);
            response.setStatus(0);
            response.setMsg("Route doesn't exist");
            return response;
        }

        //Check the route list for this train. Check that the required start and arrival stations are in the list of stops that are not on the route, and check that the location of the start station is before the stop
        //Trains that meet the above criteria are added to the return list
        int indexStart = 0;
        int indexEnd = 0;
        if (route.getStations().contains(start) &&
                route.getStations().contains(end) &&
                route.getStations().indexOf(start) < route.getStations().indexOf(end)){
            indexStart = route.getStations().indexOf(start);
            indexEnd = route.getStations().indexOf(end);
            LOGGER.info("[queryForTravel][query start index and end index][indexStart: {} indexEnd: {}]", indexStart, indexEnd);
            LOGGER.info("[queryForTravel][query stations and distances][stations: {} distances: {}]", route.getStations(), route.getDistances());
        }else {
            result.setStatus(false);
            response.setStatus(0);
            response.setMsg("Station not correct in Route");
            return response;
        }
        PriceConfig priceConfig = queryPriceConfigByRouteIdAndTrainType(routeId, trainType.getName(), headers);
        HashMap<String, String> prices = new HashMap<>();
        try {
            int distance = 0;
            distance = route.getDistances().get(indexEnd) - route.getDistances().get(indexStart);
            /**
             * We need the price Rate and distance (starting station).
             */
            double priceForEconomyClass = distance * priceConfig.getBasicPriceRate();
            double priceForConfortClass = distance * priceConfig.getFirstClassPriceRate();
            prices.put("economyClass", "" + priceForEconomyClass);
            prices.put("confortClass", "" + priceForConfortClass);
        }catch (Exception e){
                prices.put("economyClass", "95.0");
                prices.put("confortClass", "120.0");
        }
        result.setRoute(route);
        result.setPrices(prices);
        result.setPercent(1.0);
        response.setData(result);
        BasicServiceImpl.LOGGER.info("[queryForTravel][all done][result: {}]", result);

        return response;
    }

    @Override
    public Response queryForTravels(List<Travel> infos, HttpHeaders headers) {
        Response response = new Response<>();
        response.setStatus(1);
        response.setMsg("Success");

        HashMap<String, Travel> tripInfos = new HashMap<>();
        HashMap<String, List<String>> startTrips = new HashMap<>();
        HashMap<String, List<String>> endTrips = new HashMap<>();
        HashMap<String, List<String>> routeTrips = new HashMap<>();
        HashMap<String, List<String>> typeTrips = new HashMap<>();
        Set<String> stationNames = new HashSet<>();
        Set<String> trainTypeNames = new HashSet<>();
        Set<String> routeIds = new HashSet<>();
        Set<String> avaTrips = new HashSet<>();
        for(Travel info: infos){
            stationNames.add(info.getStartPlace());
            stationNames.add(info.getEndPlace());
            trainTypeNames.add(info.getTrip().getTrainTypeName());
            routeIds.add(info.getTrip().getRouteId());

            String tripNumber = info.getTrip().getTripId().toString();
            avaTrips.add(tripNumber);
            tripInfos.put(tripNumber, info);

            String start = info.getStartPlace();
            List<String> trips = startTrips.get(start);
            if(trips == null) {
               trips = new ArrayList<>();
            }
            trips.add(tripNumber);
            startTrips.put(start, trips);

            String end = info.getEndPlace();
            trips = endTrips.get(end);
            if(trips == null) {
                trips = new ArrayList<>();
            }
            trips.add(tripNumber);
            endTrips.put(end, trips);

            String routeId = info.getTrip().getRouteId();
            trips = routeTrips.get(routeId);
            if(trips == null) {
                trips = new ArrayList<>();
            }
            trips.add(tripNumber);
            routeTrips.put(routeId, trips);

            String trainTypeName = info.getTrip().getTrainTypeName();
            trips = typeTrips.get(trainTypeName);
            if(trips == null) {
                trips = new ArrayList<>();
            }
            trips.add(tripNumber);
            typeTrips.put(trainTypeName, trips);
        }

        //List<String> invalidTrips = new ArrayList<>();

        // check if station exist to exclude invalid travel info
        Map<String, String> stationMap = checkStationsExists(new ArrayList<>(stationNames), headers);
        if(stationMap == null) {
            response.setStatus(0);
            response.setMsg("all stations don't exist");
            return response;
        }
        for(Map.Entry<String, String> s : stationMap.entrySet()){
            if(s.getValue() == null ){
                // station not exist
                if(startTrips.get(s.getKey()) != null){
                    avaTrips.removeAll(startTrips.get(s.getKey()));
                }
                if(endTrips.get(s.getKey()) != null){
                    avaTrips.removeAll(endTrips.get(s.getKey()));
                }
            }
        }

        if(avaTrips.size() == 0){
            response.setStatus(0);
            response.setMsg("no travel info available");
            return response;
        }

        // check if train_type exist
        List<TrainType> tts = queryTrainTypeByNames(new ArrayList<>(trainTypeNames), headers);
        if(tts == null){
            response.setStatus(0);
            response.setMsg("all train_type don't exist");
            return response;
        }
        Map<String, TrainType> trainTypeMap = new HashMap<>();
        for(TrainType t: tts){
            trainTypeMap.put(t.getName(), t);
        }
        for(Map.Entry<String, List<String>> typeTrip: typeTrips.entrySet()){
            String ttype = typeTrip.getKey();
            if(trainTypeMap.get(ttype) == null){
                avaTrips.removeAll(typeTrip.getValue());
            }
        }
        if(avaTrips.size() ==0){
            response.setStatus(0);
            response.setMsg("no travel info available");
            return response;
        }

        // check if route exist to exclude invalid travel info
        List<Route> routes = getRoutesByRouteIds(new ArrayList<>(routeIds), headers);
        if(routes == null) {
            response.setStatus(0);
            response.setMsg("all routes don't exist");
            return response;
        }
        Map<String, Route> routeMap = new HashMap<>();
        for(Route r: routes){
            routeMap.put(r.getId(), r);
        }
        for(Map.Entry<String, List<String>> routeTrip: routeTrips.entrySet()){
            String routeId = routeTrip.getKey();
            if(routeMap.get(routeId) == null){
                avaTrips.removeAll(routeTrip.getValue());
            }else{
                Route route = routeMap.get(routeId);
                List<String> trips = routeTrip.getValue();
                for(String t: trips){
                    String start = tripInfos.get(t).getStartPlace();
                    String end = tripInfos.get(t).getEndPlace();
                    if (!route.getStations().contains(start) ||
                            !route.getStations().contains(end) ||
                            route.getStations().indexOf(start) >= route.getStations().indexOf(end)){
                        avaTrips.remove(t);
                    }
                }
            }
        }
        if(avaTrips.size() == 0){
            response.setStatus(0);
            response.setMsg("no travel info available");
            return response;
        }

        List<String> routeIdAndTypes = new ArrayList<>();
        for(String tripNumber: avaTrips){
            String routeId = tripInfos.get(tripNumber).getTrip().getRouteId();
            String trainType = tripInfos.get(tripNumber).getTrip().getTrainTypeName();
            routeIdAndTypes.add(routeId+":"+trainType);
        }
        Map<String, PriceConfig> pcMap = queryPriceConfigByRouteIdsAndTrainTypes(routeIdAndTypes, headers);

        Map<String, TravelResult> trMap = new HashMap<>();
        for(String tripNumber: avaTrips){
            Travel info = tripInfos.get(tripNumber);
            String trainType = info.getTrip().getTrainTypeName();
            String routeId = info.getTrip().getRouteId();
            Route route = routeMap.get(routeId);

            int indexStart = route.getStations().indexOf(info.getStartPlace());
            int indexEnd = route.getStations().indexOf(info.getEndPlace());

            double basicPriceRate = 0.75;
            double firstPriceRate = 1;
            PriceConfig priceConfig = pcMap.get(routeId+":"+trainType);
            if(priceConfig != null){
                basicPriceRate = priceConfig.getBasicPriceRate();
                firstPriceRate = priceConfig.getFirstClassPriceRate();
            }

            HashMap<String, String> prices = new HashMap<>();
            try {
                int distance = 0;
                distance = route.getDistances().get(indexEnd) - route.getDistances().get(indexStart);
                /**
                 * We need the price Rate and distance (starting station).
                 */
                double priceForEconomyClass = distance * basicPriceRate;
                double priceForConfortClass = distance * firstPriceRate;
                prices.put("economyClass", "" + priceForEconomyClass);
                prices.put("confortClass", "" + priceForConfortClass);
            }catch (Exception e){
                prices.put("economyClass", "95.0");
                prices.put("confortClass", "120.0");
            }


            TravelResult result = new TravelResult();
            result.setStatus(true);
            result.setTrainType(trainTypeMap.get(trainType));
            result.setRoute(route);
            result.setPrices(prices);
            result.setPercent(1.0);

            trMap.put(tripNumber, result);
        }
        response.setData(trMap);
        BasicServiceImpl.LOGGER.info("[queryForTravels][all done][result map: {}]", trMap);
        return response;
    }

    @Override
    public Response queryForStationId(String stationName, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[queryForStationId][Query For Station Id][stationName: {}]", stationName);
        HttpEntity requestEntity = new HttpEntity(null);
        String station_service_url=getServiceUrl("ts-station-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                station_service_url + "/api/v1/stationservice/stations/id/" + stationName,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        if (re.getBody().getStatus() != 1) {
            String msg = re.getBody().getMsg();
            BasicServiceImpl.LOGGER.warn("[queryForStationId][Query for stationId error][stationName: {}, message: {}]", stationName, msg);
            return new Response<>(0, msg, null);
        }
        return  re.getBody();
    }

    public Map<String,String> checkStationsExists(List<String> stationNames, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[checkStationsExists][Check Stations Exists][stationNames: {}]", stationNames);
        HttpEntity requestEntity = new HttpEntity(stationNames, null);
        String station_service_url=getServiceUrl("ts-station-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                station_service_url + "/api/v1/stationservice/stations/idlist",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response<Map<String, String>> r = re.getBody();
        if(r.getStatus() == 0) {
            return null;
        }
        Map<String, String> stationMap = r.getData();
        return stationMap;
    }

    public boolean checkStationExists(String stationName, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[checkStationExists][Check Station Exists][stationName: {}]", stationName);
        HttpEntity requestEntity = new HttpEntity(null);
        String station_service_url=getServiceUrl("ts-station-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                station_service_url + "/api/v1/stationservice/stations/id/" + stationName,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response exist = re.getBody();

        return exist.getStatus() == 1;
    }

    public List<TrainType> queryTrainTypeByNames(List<String> trainTypeNames, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[queryTrainTypeByNames][Query Train Type][Train Type names: {}]", trainTypeNames);
        HttpEntity requestEntity = new HttpEntity(trainTypeNames, null);
        String train_service_url=getServiceUrl("ts-train-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                train_service_url + "/api/v1/trainservice/trains/byNames",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response<List<TrainType>>  response = re.getBody();
        if(response.getStatus() == 0){
            return null;
        }
        List<TrainType> tts = Arrays.asList(JsonUtils.conveterObject(response.getData(), TrainType[].class));
        return tts;
    }

    public TrainType queryTrainTypeByName(String trainTypeName, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[queryTrainTypeByName][Query Train Type][Train Type name: {}]", trainTypeName);
        HttpEntity requestEntity = new HttpEntity(null);
        String train_service_url=getServiceUrl("ts-train-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                train_service_url + "/api/v1/trainservice/trains/byName/" + trainTypeName,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response  response = re.getBody();

        return JsonUtils.conveterObject(response.getData(), TrainType.class);
    }

    private List<Route> getRoutesByRouteIds(List<String> routeIds, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[getRoutesByRouteIds][Get Route By Ids][Route IDs：{}]", routeIds);
        HttpEntity requestEntity = new HttpEntity(routeIds, null);
        String route_service_url=getServiceUrl("ts-route-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                route_service_url + "/api/v1/routeservice/routes/byIds/",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response<List<Route>> result = re.getBody();
        if ( result.getStatus() == 0) {
            BasicServiceImpl.LOGGER.warn("[getRoutesByRouteIds][Get Route By Ids Failed][Fail msg: {}]", result.getMsg());
            return null;
        } else {
            BasicServiceImpl.LOGGER.info("[getRoutesByRouteIds][Get Route By Ids][Success]");
            List<Route> routes = Arrays.asList(JsonUtils.conveterObject(result.getData(), Route[].class));;
            return routes;
        }
    }

    private Route getRouteByRouteId(String routeId, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[getRouteByRouteId][Get Route By Id][Route ID：{}]", routeId);
        HttpEntity requestEntity = new HttpEntity(null);
        String route_service_url=getServiceUrl("ts-route-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                route_service_url + "/api/v1/routeservice/routes/" + routeId,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response result = re.getBody();
        if ( result.getStatus() == 0) {
            BasicServiceImpl.LOGGER.warn("[getRouteByRouteId][Get Route By Id Failed][Fail msg: {}]", result.getMsg());
            return null;
        } else {
            BasicServiceImpl.LOGGER.info("[getRouteByRouteId][Get Route By Id][Success]");
            return JsonUtils.conveterObject(result.getData(), Route.class);
        }
    }

    private PriceConfig queryPriceConfigByRouteIdAndTrainType(String routeId, String trainType, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[queryPriceConfigByRouteIdAndTrainType][Query For Price Config][RouteId: {} ,TrainType: {}]", routeId, trainType);
        HttpEntity requestEntity = new HttpEntity(null, null);
        String price_service_url=getServiceUrl("ts-price-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                price_service_url + "/api/v1/priceservice/prices/" + routeId + "/" + trainType,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response result = re.getBody();

        BasicServiceImpl.LOGGER.info("[queryPriceConfigByRouteIdAndTrainType][Response Resutl to String][result: {}]", result.toString());
        return  JsonUtils.conveterObject(result.getData(), PriceConfig.class);
    }

    private Map<String, PriceConfig> queryPriceConfigByRouteIdsAndTrainTypes(List<String> routeIdsTypes, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[queryPriceConfigByRouteIdsAndTrainTypes][Query For Price Config][RouteId and TrainType: {}]", routeIdsTypes);
        HttpEntity requestEntity = new HttpEntity(routeIdsTypes, null);
        String price_service_url=getServiceUrl("ts-price-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                price_service_url + "/api/v1/priceservice/prices/byRouteIdsAndTrainTypes",
                HttpMethod.POST,
                requestEntity,
                Response.class);
        Response<Map<String, PriceConfig>> result = re.getBody();

        Map<String, PriceConfig> pcMap;
        if ( result.getStatus() == 0) {
            BasicServiceImpl.LOGGER.warn("[queryPriceConfigByRouteIdsAndTrainTypes][Get Price Config by routeId and trainType Failed][Fail msg: {}]", result.getMsg());
            return null;
        } else {
            ObjectMapper mapper = new ObjectMapper();
            try{
                pcMap = mapper.readValue(JsonUtils.object2Json(result.getData()), new TypeReference<Map<String, PriceConfig>>(){});
            }catch(Exception e) {
                BasicServiceImpl.LOGGER.warn("[queryPriceConfigByRouteIdsAndTrainTypes][Get Price Config by routeId and trainType Failed][Fail msg: {}]", e.getMessage());
                return null;
            }
            BasicServiceImpl.LOGGER.info("[queryPriceConfigByRouteIdsAndTrainTypes][Get Price Config by routeId and trainType][Success][priceConfigs: {}]", result.getData());
            return pcMap;
        }
    }

}
