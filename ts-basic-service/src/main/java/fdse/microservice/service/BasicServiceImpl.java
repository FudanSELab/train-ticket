package fdse.microservice.service;

import edu.fudan.common.util.JsonUtils;
import edu.fudan.common.util.Response;
import fdse.microservice.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicServiceImpl.class);

    @Override
    public Response queryForTravel(Travel info, HttpHeaders headers) {

        Response response = new Response<>();
        TravelResult result = new TravelResult();
        result.setStatus(true);
        response.setStatus(1);
        response.setMsg("Success");
        boolean startingPlaceExist = checkStationExists(info.getStartingPlace(), headers);
        boolean endPlaceExist = checkStationExists(info.getEndPlace(), headers);
        if (!startingPlaceExist || !endPlaceExist) {
            result.setStatus(false);
            response.setStatus(0);
            response.setMsg("Start place or end place not exist!");
            if (!startingPlaceExist)
                BasicServiceImpl.LOGGER.warn("Start place {} not exist", info.getStartingPlace());
            if (!endPlaceExist)
                BasicServiceImpl.LOGGER.warn("End place {} not exist", info.getEndPlace());
        }

        TrainType trainType = queryTrainType(info.getTrip().getTrainTypeId(), headers);
        if (trainType == null) {
            BasicServiceImpl.LOGGER.warn("traintype doesn't exist, trainTypeId: {}", info.getTrip().getTrainTypeId());
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

        String startingPlaceId = (String) queryForStationId(info.getStartingPlace(), headers).getData();
        String endPlaceId = (String) queryForStationId(info.getEndPlace(), headers).getData();

        LOGGER.info("startingPlaceId: " + startingPlaceId + "endPlaceId: " + endPlaceId);

        int indexStart = 0;
        int indexEnd = 0;
        if (route != null) {
            indexStart = route.getStations().indexOf(startingPlaceId);
            indexEnd = route.getStations().indexOf(endPlaceId);
        }

        LOGGER.info("indexStart: " + indexStart + " __ " + "indexEnd: " + indexEnd);
        if (route != null){
            LOGGER.info("route.getDistances().size: " + route.getDistances().size());
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
        BasicServiceImpl.LOGGER.info("[Query For Station Id] stationName: {}", stationName);
        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations/id/" + stationName,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        if (re.getBody().getStatus() != 1) {
            String msg = re.getBody().getMsg();
            BasicServiceImpl.LOGGER.warn("Query for stationId error, stationName: {}, message: {}", stationName, msg);
            return new Response<>(0, msg, null);
        }
        return  re.getBody();
    }

    public boolean checkStationExists(String stationName, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[Check Station Exists] stationName: {}", stationName);
        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations/id/" + stationName,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response exist = re.getBody();

        return exist.getStatus() == 1;
    }

    public TrainType queryTrainType(String trainTypeId, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[Query Train Type] Train Type: {}", trainTypeId);
        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-train-service:14567/api/v1/trainservice/trains/" + trainTypeId,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response  response = re.getBody();

        return JsonUtils.conveterObject(response.getData(), TrainType.class);
    }

    private Route getRouteByRouteId(String routeId, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[Get Route By Id] Route ID：{}", routeId);
        HttpEntity requestEntity = new HttpEntity(null);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-route-service:11178/api/v1/routeservice/routes/" + routeId,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response result = re.getBody();
        if ( result.getStatus() == 0) {
            BasicServiceImpl.LOGGER.warn("[Get Route By Id] Fail. {}", result.getMsg());
            return null;
        } else {
            BasicServiceImpl.LOGGER.info("[Get Route By Id] Success.");
            return JsonUtils.conveterObject(result.getData(), Route.class);
        }
    }

    private PriceConfig queryPriceConfigByRouteIdAndTrainType(String routeId, String trainType, HttpHeaders headers) {
        BasicServiceImpl.LOGGER.info("[Query For Price Config] RouteId: {} ,TrainType: {}", routeId, trainType);
        HttpEntity requestEntity = new HttpEntity(null, null);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-price-service:16579/api/v1/priceservice/prices/" + routeId + "/" + trainType,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response result = re.getBody();

        BasicServiceImpl.LOGGER.info("Response Resutl to String {}", result.toString());
        return  JsonUtils.conveterObject(result.getData(), PriceConfig.class);
    }

}
