package order.repository;

import order.entity.AssuranceOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface AssuranceRepository extends CrudRepository<AssuranceOrder, String> {

    Optional<AssuranceOrder> findById(String id);

    AssuranceOrder findByOrderId(String orderId);

    @Transactional
    void deleteById(String id);

    @Transactional
    void removeAssuranceByOrderId(String orderId);

    @Override
    ArrayList<AssuranceOrder> findAll();
}
