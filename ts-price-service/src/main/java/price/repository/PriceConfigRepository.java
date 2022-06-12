package price.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import price.entity.PriceConfig;
import java.util.List;
import java.util.Optional;

/**
 * @author fdse
 */
@Repository
public interface PriceConfigRepository extends CrudRepository<PriceConfig, String> {

    @Override
    Optional<PriceConfig> findById(String id);

    PriceConfig findByRouteIdAndTrainType(String routeId,String trainType);

    @Override
    List<PriceConfig> findAll();

}
