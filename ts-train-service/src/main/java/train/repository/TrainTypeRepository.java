package train.repository;

import org.springframework.data.repository.CrudRepository;
import train.entity.TrainType;

import java.util.List;
import java.util.Optional;


public interface TrainTypeRepository extends CrudRepository<TrainType,String> {

    Optional<TrainType> findById(String id);
    @Override
    List<TrainType> findAll();
    void deleteById(String id);
    TrainType findByName(String name);
}
