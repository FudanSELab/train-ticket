package route.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import route.entity.Route;
import java.util.ArrayList;

/**
 * @author cw
 */
@Repository
public interface RouteRepository extends MongoRepository<Route, String> {

    /**
     * find route by id
     * @param id id
     * @return route
     */
    @Query("{ 'id': ?0 }")
    Route findById(String id);

    /**
     * find all routes
     * @return find all routes
     */
    @Override
    ArrayList<Route> findAll();

    /**
     * remove route via id
     * @param id id
     */
    void removeRouteById(String id);

    /**
     * return route with id from 'from' to 'to'
     * @param startingId from
     * @param terminalId to
     * @return routes
     */
    @Query("{ 'startStationId': ?0 , 'terminalStationId': ?1 }")
    ArrayList<Route> findByStartStationIdAndTerminalStationId(String startingId, String terminalId);

}
