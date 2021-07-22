package delivery.repository;

import delivery.entity.Delivery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryRepository extends MongoRepository<Delivery, String> {

    Delivery findById(UUID id);

    @Query("{ 'orderId' : ?0 }")
    Delivery findByOrderId(UUID orderId);

    @Override
    List<Delivery> findAll();

    void deleteById(UUID id);

    void deleteFoodOrderByOrderId(UUID id);

}
