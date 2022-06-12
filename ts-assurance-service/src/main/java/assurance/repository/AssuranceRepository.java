package assurance.repository;

import assurance.entity.Assurance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author fdse
 */
@Repository
public interface AssuranceRepository  extends CrudRepository<Assurance, String> {

    /**
     * find by id
     *
     * @param id id
     * @return Assurance
     */
    Optional<Assurance> findById(String id);

    /**
     * find by order id
     *
     * @param orderId order id
     * @return Assurance
     */
    Assurance findByOrderId(String orderId);

    /**
     * delete by id
     *
     * @param id id
     * @return null
     */
    @Transactional
    void deleteById(String id);

    /**
     * remove assurance by order id
     *
     * @param orderId order id
     * @return null
     */
    @Transactional
    void removeAssuranceByOrderId(String orderId);

    /**
     * find all
     *
     * @return ArrayList<Assurance>
     */
    @Override
    ArrayList<Assurance> findAll();
}
