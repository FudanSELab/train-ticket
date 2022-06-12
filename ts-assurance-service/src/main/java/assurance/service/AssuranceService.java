package assurance.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author fdse
 */
public interface AssuranceService {

    /**
     * find assurance by id
     *
     * @param id id
     * @param headers headers
     * @return Response
     */
    Response findAssuranceById(UUID id, HttpHeaders headers);

    /**
     * find assurance by order id
     *
     * @param orderId order id
     * @param headers headers
     * @return Response
     */
    Response findAssuranceByOrderId(UUID orderId, HttpHeaders headers);

    /**
     * find assurance by type index, order id
     *
     * @param typeIndex type index
     * @param orderId order id
     * @param headers headers
     * @return Response
     */
    Response create(int typeIndex,String orderId , HttpHeaders headers);

    /**
     * delete by order id
     *
     * @param assuranceId assurance id
     * @param headers headers
     * @return Response
     */
    @Transactional
    Response deleteById(UUID assuranceId, HttpHeaders headers);

    /**
     * delete by order id
     *
     * @param orderId order id
     * @param headers headers
     * @return Response
     */
    @Transactional
    Response deleteByOrderId(UUID orderId, HttpHeaders headers);

    /**
     * modify by assurance id, order id, type index
     *
     * @param assuranceId assurace id
     * @param orderId order id
     * @param typeIndex type index
     * @param headers headers
     * @return Response
     */
    Response modify(String assuranceId, String orderId, int typeIndex , HttpHeaders headers);

    /**
     * get all assurances
     *
     * @param headers headers
     * @return Response
     */
    Response getAllAssurances(HttpHeaders headers);

    /**
     * get all assurance types
     *
     * @param headers headers
     * @return Response
     */
    Response getAllAssuranceTypes(HttpHeaders headers);
}
