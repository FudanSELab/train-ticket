package order.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import order.entity.WaitListOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface WaitListOrderRepository extends CrudRepository<WaitListOrder,String> {

    @Override
    Optional<WaitListOrder> findById(String id);

    @Override
    List<WaitListOrder> findAll();

    ArrayList<WaitListOrder> findByUserId(String userId);

    @Override
    void deleteById(String id);
}
