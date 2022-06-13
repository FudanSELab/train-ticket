package delivery.repository;

import delivery.entity.Delivery;
import org.springframework.data.repository.CrudRepository;

import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, String> {


    Optional<Delivery> findById(String id);

    Delivery findByOrderId(UUID orderId);

    @Override
    List<Delivery> findAll();

    void deleteById(String id);

    void deleteFoodOrderByOrderId(String id);

}
