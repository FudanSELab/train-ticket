package train.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import train.entity.TrainType;
import train.repository.TrainTypeRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainTypeRepository repository;

    @Override
    public boolean create(TrainType trainType, HttpHeaders headers) {
        if (trainType.getName().isEmpty()) {
            log.error("[create][Create train error][Train Type name not specified]");
            return false;
        }

        if (repository.findByName(trainType.getName()) != null) {
            log.error("[create][Create train error][Train already exists][TrainTypeId: {}]", trainType.getId());
            return false;
        }

        TrainType type = new TrainType(trainType.getName(), trainType.getEconomyClass(), trainType.getConfortClass());
        type.setAverageSpeed(trainType.getAverageSpeed());
        repository.save(type);
        return true;
    }

    @Override
    public TrainType retrieve(String id, HttpHeaders headers) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public TrainType retrieveByName(String name, HttpHeaders headers) {
        return repository.findByName(name);
    }

    @Override
    public List<TrainType> retrieveByNames(List<String> names, HttpHeaders headers) {
        return repository.findByNames(names);
    }

    @Override
    @Transactional
    public boolean update(TrainType trainType, HttpHeaders headers) {
        Optional<TrainType> found = repository.findById(trainType.getId());
        if (!found.isPresent()) {
            log.error("[update][Update train error][Train not found][TrainTypeId: {}]", trainType.getId());
            return false;
        }

        TrainType type = new TrainType(trainType.getName(), trainType.getEconomyClass(),
                trainType.getConfortClass(),
                trainType.getAverageSpeed());
        type.setId(trainType.getId());
        repository.save(type);
        return true;
    }

    @Override
    public boolean delete(String id, HttpHeaders headers) {
        Optional<TrainType> found = repository.findById(id);
        if(!found.isPresent()) {
            log.error("[delete][Delete train error][Train not found][TrainTypeId: {}]", id);
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @Override
    public List<TrainType> query(HttpHeaders headers) {
        return repository.findAll();
    }

}
