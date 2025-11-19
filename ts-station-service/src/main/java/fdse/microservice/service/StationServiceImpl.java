package fdse.microservice.service;

import edu.fudan.common.util.Response;
import fdse.microservice.entity.*;
import fdse.microservice.repository.StationRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

	private final StationRepository repository;

	private static final String SUCCESS = "Success";

	@Override
	public Response<Station> create(Station station, HttpHeaders headers) {
		Station foundStation = repository.findByName(station.getName());
		log.info("[create][Create station][found: {}]", foundStation == null);

		if (foundStation != null) {
			log.error("[create][Create station error][Already exists][StationId: {}]", station.getId());
			return new Response<>(0, "Already exists", new Station(station.getName(), station.getStayTime()));
		}

		Station newStation = repository.save(new Station(station.getName(), station.getStayTime()));
		return new Response<>(1, "Create success", newStation);
	}

	@Override
	public boolean exist(String stationName, HttpHeaders headers) {
		return repository.findByName(stationName) != null;
	}

	@Override
	public Response<Station> update(Station info, HttpHeaders headers) {
		return repository.findById(info.getId())
				.map(station -> {
					station.setName(info.getName());
					station.setStayTime(info.getStayTime());
					repository.save(station);
					return new Response<>(1, "Update success", station);
				})
				.orElseGet(() -> {
					log.error("[update][Update station error][Station not found][StationId: {}]", info.getId());
					return new Response<>(0, "Station not exist", null);
				});
	}

	@Override
	public Response<Station> delete(String stationsId, HttpHeaders headers) {
		return repository.findById(stationsId)
				.map(station -> {
					repository.delete(station);
					return new Response<>(1, "Delete success", station);
				})
				.orElseGet(() -> {
					log.error("[delete][Delete station error][Station not found][StationId: {}]", stationsId);
					return new Response<>(0, "Station not exist", null);
				});
	}

	@Override
	public Response<List<Station>> query(HttpHeaders headers) {
		List<Station> stations = repository.findAll();
		if (stations != null && !stations.isEmpty()) {
			return new Response<>(1, "Find all content", stations);
		}

		log.warn("[query][Query stations warn][Find all stations: {}]", "No content");
		return new Response<>(0, "No content", null);
	}

	@Override
	public Response<String> queryForId(String stationName, HttpHeaders headers) {
		Station station = repository.findByName(stationName);
		if (station != null) {
			return new Response<>(1, SUCCESS, station.getId());
		}

		log.warn("[queryForId][Find station id warn][Station not found][StationName: {}]", stationName);
		return new Response<>(0, "Not exists", stationName);
	}

	@Override
	public Response<Map<String, String>> queryForIdBatch(List<String> nameList, HttpHeaders headers) {
		Map<String, String> name2Id = repository.findByNames(nameList).stream()
				.collect(Collectors.toMap(Station::getName, Station::getId));
		Map<String, String> result = nameList.stream()
				.collect(Collectors.toMap(name -> name, name2Id::get));

		log.info("[queryForIdBatch][result size: {}]", result.size());
		if (result.isEmpty()) {
			return new Response<>(0, "No content according to name list", null);
		}
		return new Response<>(1, SUCCESS, result);
	}

	@Override
	public Response<String> queryById(String stationId, HttpHeaders headers) {
		return repository.findById(stationId)
				.map(station -> new Response<>(1, SUCCESS, station.getName()))
				.orElseGet(() -> {
					log.error("[queryById][Find station name error][Station not found][StationId: {}]", stationId);
					return new Response<>(0, "No that stationId", stationId);
				});
	}

	@Override
	public Response<List<String>> queryByIdBatch(List<String> idList, HttpHeaders headers) {
		List<String> result = idList.stream()
				.map(repository::findById)
				.filter(Optional::isPresent)
				.map(optional -> optional.get().getName())
				.collect(Collectors.toList());

		if (!result.isEmpty()) {
			return new Response<>(1, SUCCESS, result);
		}

		log.error("[queryByIdBatch][Find station names error][Stations not found][StationIdNumber: {}]", idList.size());
		return new Response<>(0, "No stationNamelist according to stationIdList", result);
	}
}
