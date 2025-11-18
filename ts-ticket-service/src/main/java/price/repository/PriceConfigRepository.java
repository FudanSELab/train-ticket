package price.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import price.entity.Price;
import java.util.List;
import java.util.Optional;

/**
 * @author fdse
 */
@Repository
public interface PriceConfigRepository extends CrudRepository<Price, String> {

    @Override
    Optional<Price> findById(String id);

    Price findByRouteIdAndTrainType(String routeId,String trainType);

    @Query(value="SELECT * FROM price_config WHERE route_id IN ?1 AND train_type IN ?2", nativeQuery = true)
    List<Price> findByRouteIdsAndTrainTypes(List<String> routeIds, List<String> trainTypes);

    @Override
    List<Price> findAll();

}
