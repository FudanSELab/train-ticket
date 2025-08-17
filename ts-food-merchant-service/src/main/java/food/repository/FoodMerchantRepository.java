package food.repository;

import food.entity.FoodMerchant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodMerchantRepository extends CrudRepository<FoodMerchant, String> {
}