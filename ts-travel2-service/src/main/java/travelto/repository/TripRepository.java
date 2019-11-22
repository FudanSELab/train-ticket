package travelto.repository;

import org.springframework.data.repository.CrudRepository;
import travelto.entity.Trip;
import travelto.entity.TripId;

import java.util.ArrayList;

/**
 * TripRepository class
 *
 * @author fdu
 * @date 2019/11/10
 */
public interface TripRepository extends CrudRepository<Trip, TripId> {
    /**
     * 根据tripid查找
     *
     * @param tripId
     * @return
     */
    Trip findByTripId(TripId tripId);

    /**
     * 根据tripid删除
     *
     * @param tripId
     */
    void deleteByTripId(TripId tripId);

    /**
     * 查找所有
     *
     * @return
     */
    @Override
    ArrayList<Trip> findAll();

    /**
     * 根据routeId查找
     *
     * @param routeId
     * @return
     */
    ArrayList<Trip> findByRouteId(String routeId);
}
