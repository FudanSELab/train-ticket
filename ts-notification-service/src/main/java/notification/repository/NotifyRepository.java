package notification.repository;


import notification.entity.NotifyInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotifyRepository extends MongoRepository<NotifyInfo, String> {

    NotifyInfo findById(UUID id);

    @Override
    List<NotifyInfo> findAll();

    void deleteById(UUID id);
}
