package consign.repository;

import consign.entity.ConsignPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for accessing the single ConsignPrice configuration row.
 */
@Repository
public interface ConsignPriceConfigRepository extends CrudRepository<ConsignPrice, String> {

    /**
     * Find configuration by index (should always be 0).
     *
     * @param index index value (0)
     * @return ConsignPrice configuration row
     */
    ConsignPrice findByIndex(int index);
}
