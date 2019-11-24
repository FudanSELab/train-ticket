package travelto.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import travelto.entity.*;

import java.util.List;

/**
 * Travel2Service class
 *
 * @author fdu
 * @date 2019/11/10
 */
public interface Travel2Service {
    /**
     * 创建服务
     *
     * @param info
     * @param headers
     * @return
     */
    Response create(TravelInfo info, HttpHeaders headers);

    /**
     * 获取信息
     *
     * @param tripId
     * @param headers
     * @return
     */
    Response retrieve(String tripId, HttpHeaders headers);

    /**
     * 更新信息
     *
     * @param info
     * @param headers
     * @return
     */
    Response update(TravelInfo info, HttpHeaders headers);

    /**
     * 删除信息
     *
     * @param tripId
     * @param headers
     * @return
     */
    Response delete(String tripId, HttpHeaders headers);

    /**
     * 查询信息
     *
     * @param info
     * @param headers
     * @return
     */
    Response query(TripInfo info, HttpHeaders headers);

    /**
     * 获取所有详细信息
     *
     * @param gtdi
     * @param headers
     * @return
     */
    Response getTripAllDetailInfo(TripAllDetailInfo gtdi, HttpHeaders headers);

    /**
     * 根据tripid获取路径信息
     *
     * @param tripId
     * @param headers
     * @return
     */
    Response getRouteByTripId(String tripId, HttpHeaders headers);

    /**
     * 根据tripid获取列车类型信息
     *
     * @param tripId
     * @param headers
     * @return
     */
    Response getTrainTypeByTripId(String tripId, HttpHeaders headers);

    /**
     * 查询所有
     *
     * @param headers
     * @return
     */
    Response queryAll(HttpHeaders headers);

    /**
     * 根据路径获取trip信息
     *
     * @param routeIds
     * @param headers
     * @return
     */
    Response getTripByRoute(List<String> routeIds, HttpHeaders headers);

    /**
     * 管理员查询所有
     *
     * @param headers
     * @return
     */
    Response adminQueryAll(HttpHeaders headers);
}
