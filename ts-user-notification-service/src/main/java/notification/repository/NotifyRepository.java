package notification.repository;


import notification.entity.NotifyInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotifyRepository extends CrudRepository<NotifyInfo, String> {

    Optional<NotifyInfo> findById(String id);

    @Override
    List<NotifyInfo> findAll();

    void deleteById(String id);
}
