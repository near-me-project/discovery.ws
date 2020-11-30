package near.me.discovery.repository;

import near.me.discovery.repository.entity.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository  extends MongoRepository<ClientEntity, Long> {
    ClientEntity findByClientName(String clientName);
}
