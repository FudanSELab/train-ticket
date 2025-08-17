package order.repository;

import order.entity.Assurance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface AssuranceRepository extends CrudRepository<Assurance, String> {

    Optional<Assurance> findById(String id);

    Assurance findByOrderId(String orderId);

    @Transactional
    void deleteById(String id);

    @Transactional
    void removeAssuranceByOrderId(String orderId);

    @Override
    ArrayList<Assurance> findAll();
}
