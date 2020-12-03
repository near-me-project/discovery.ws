package near.me.discovery.repository;

import near.me.discovery.repository.entity.ClientInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientInfoRepository extends MongoRepository<ClientInfo, Long> {
}
