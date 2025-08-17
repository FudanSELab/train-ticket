package food.repository;

import food.entity.TrainFood;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainFoodRepository extends CrudRepository<TrainFood, String> {

    @Override
    Optional<TrainFood> findById(String id);

    @Override
    List<TrainFood> findAll();

    TrainFood findByTripId(String tripId);

    @Override
    void deleteById(String id);
}
