package order.repository;

import order.entity.ConsignOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author fdse
 */
@Repository
public interface ConsignRepository extends CrudRepository<ConsignOrder, String> {

    /**
     * find by account id
     *
     * @param accountId account id
     * @return ArrayList<Consign>
     */
    ArrayList<ConsignOrder> findByAccountId(String accountId);

    /**
     * find by order id
     *
     * @param accountId account id
     * @return Consign
     */
    ConsignOrder findByOrderId(String accountId);

    /**
     * find by consignee
     *
     * @param consignee consignee
     * @return ArrayList<Consign>
     */
    ArrayList<ConsignOrder> findByConsignee(String consignee);

    /**
     * find by id
     *
     * @param id id
     * @return Consign
     */
    Optional<ConsignOrder> findById(String id);
}
