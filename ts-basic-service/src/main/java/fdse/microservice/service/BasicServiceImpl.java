package fdse.microservice.service;

import edu.fudan.common.entity.PriceConfig;
import edu.fudan.common.entity.Route;
import edu.fudan.common.entity.TrainType;
import edu.fudan.common.entity.Travel;
import edu.fudan.common.entity.TravelResult;
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

import java.util.HashMap;

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
        boolean startingPlaceExist = checkStationExists(info.getStartPlace(), headers);
        boolean endPlaceExist = checkStationExists(info.getEndPlace(), headers);
        if (!startingPlaceExist || !endPlaceExist) {
            result.setStatus(false);
            response.setStatus(0);
            response.setMsg("Start place or end place not exist!");
            if (!startingPlaceExist)
                BasicServiceImpl.LOGGER.warn("[queryForTravel][Start place not exist][start place: {}]", info.getStartPlace());
            if (!endPlaceExist)
                BasicServiceImpl.LOGGER.warn("[queryForTravel][End place not exist][end place: {}]", info.getEndPlace());
        }

        TrainType trainType = queryTrainType(info.getTrip().getTrainTypeName(), headers);
        if (trainType == null) {
            BasicServiceImpl.LOGGER.warn("[queryForTravel][traintype doesn't exist][trainTypeId: {}]", info.getTrip().getTrainTypeName());
            result.setStatus(false);
            response.setStatus(0);
            response.setMsg("Train type doesn't exist");
        } else {
            result.setTrainType(trainType);
        }

        String routeId = info.getTrip().getRouteId();
        String trainTypeString = "";
        if (trainType != null){
            trainTypeString = trainType.getId();
        }
        Route route = getRouteByRouteId(routeId, headers);
        PriceConfig priceConfig = queryPriceConfigByRouteIdAndTrainType(routeId, trainTypeString, headers);

        String startingPlaceId = (String) queryForStationId(info.getStartPlace(), headers).getData();
        String endPlaceId = (String) queryForStationId(info.getEndPlace(), headers).getData();

        LOGGER.info("[queryForTravel][query start and end info][startingPlaceId: {} endPlaceId: {}]", startingPlaceId, endPlaceId);

        int indexStart = 0;
        int indexEnd = 0;
        if (route != null) {
            indexStart = route.getStations().indexOf(startingPlaceId);
            indexEnd = route.getStations().indexOf(endPlaceId);
        }

        LOGGER.info("[queryForTravel][query start index and end index][indexStart: {} indexEnd: {}]", indexStart, indexEnd);
        if (route != null){
            LOGGER.info("[queryForTravel][route exists][route.getDistances().size: {}]", route.getDistances().size());
        }
        HashMap<String, String> prices = new HashMap<>();
        try {
            int distance = 0;
            if (route != null){
                distance = route.getDistances().get(indexEnd) - route.getDistances().get(indexStart);
            }

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
        result.setPrices(prices);
        result.setPercent(1.0);
        response.setData(result);
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

    public TrainType queryTrainType(String trainTypeId, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[queryTrainType][Query Train Type][Train Type id: {}]", trainTypeId);
        HttpEntity requestEntity = new HttpEntity(null);
        String train_service_url=getServiceUrl("ts-train-service");
        ResponseEntity<Response> re = restTemplate.exchange(
                train_service_url + "/api/v1/trainservice/trains/" + trainTypeId,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response  response = re.getBody();

        return JsonUtils.conveterObject(response.getData(), TrainType.class);
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

}
