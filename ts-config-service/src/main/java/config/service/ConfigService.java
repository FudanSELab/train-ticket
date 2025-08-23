package config.service;

import config.entity.Config;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import java.util.List;


/**
 * @author fdse
 */
public interface ConfigService {

    /**
     * create by config information and headers
     *
     * @param info info
     * @param headers headers
     * @return Response<Config>
     */
    Response<Config> create(Config info, HttpHeaders headers);

    /**
     * update by config information and headers
     *
     * @param info info
     * @param headers headers
     * @return Response<Config>
     */
    Response<Config> update(Config info, HttpHeaders headers);

    /**
     * Config retrieve
     *
     * @param name name
     * @param headers headers
     * @return Response<Config>
     */
    Response<Config> query(String name, HttpHeaders headers);

    /**
     * delete by name and headers
     *
     * @param name name
     * @param headers headers
     * @return Response<Config>
     */
    Response<Config> delete(String name, HttpHeaders headers);

    /**
     * query all by headers
     *
     * @param headers headers
     * @return Response<List<Config>>
     */
    Response<List<Config>> queryAll(HttpHeaders headers);
}
