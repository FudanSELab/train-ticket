/**
 * @author shenxinyao
 * @date 2019/11/19
 */
package other.repository;

import other.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Repository
public interface OrderOtherRepository extends MongoRepository<Order, String> {

    @Query("{ 'id': ?0 }")
    /**
     * findById
     * @param id:
     * @return :
     */
    Order findById(UUID id);

    /**
     * findAll
     * @return :
     */
    ArrayList<Order> findAll();

    /**
     * findByAccountId
     * @param accountId:
     * @return :
     */
    @Query("{ 'accountId' : ?0 }")
    ArrayList<Order> findByAccountId(UUID accountId);

    /**
     * findByTravelDateAndTrainNumber
     * @param travelDate:
     * @param trainNumber:
     * @return :
     */
    @Query("{ 'travelDate' : ?0 , trainNumber : ?1 }")
    ArrayList<Order> findByTravelDateAndTrainNumber(Date travelDate, String trainNumber);

    /**
     * deleteById
     * @param id:
     * @return :
     */
    void deleteById(UUID id);
}
