package fdse.microservice.service;

import edu.fudan.common.util.Response;
import fdse.microservice.entity.Station;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

public interface StationService {
    //CRUD
    Response<Station> create(Station info, HttpHeaders headers);

    boolean exist(String stationName, HttpHeaders headers);

    Response<Station> update(Station info, HttpHeaders headers);

    Response<Station> delete(String stationsId, HttpHeaders headers);

    Response<List<Station>> query(HttpHeaders headers);

    Response<String> queryForId(String stationName, HttpHeaders headers);

    Response<Map<String, String>> queryForIdBatch(List<String> nameList, HttpHeaders headers);

    Response<String> queryById(String stationId, HttpHeaders headers);

    Response<List<String>> queryByIdBatch(List<String> stationIdList, HttpHeaders headers);

}
