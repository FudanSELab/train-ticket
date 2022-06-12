package consign.repository;

import consign.entity.ConsignRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author fdse
 */
@Repository
public interface ConsignRepository extends CrudRepository<ConsignRecord, String> {

    /**
     * find by account id
     *
     * @param accountId account id
     * @return ArrayList<ConsignRecord>
     */
    ArrayList<ConsignRecord> findByAccountId(String accountId);

    /**
     * find by order id
     *
     * @param accountId account id
     * @return ConsignRecord
     */
    ConsignRecord findByOrderId(String accountId);

    /**
     * find by consignee
     *
     * @param consignee consignee
     * @return ArrayList<ConsignRecord>
     */
    ArrayList<ConsignRecord> findByConsignee(String consignee);

    /**
     * find by id
     *
     * @param id id
     * @return ConsignRecord
     */
    Optional<ConsignRecord> findById(String id);
}
