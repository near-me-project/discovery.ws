package near.me.discovery.service;

import near.me.discovery.repository.ClientsRepository;
import near.me.discovery.repository.entity.ClientEntity;
import near.me.discovery.repository.entity.ClientInfo;
import near.me.discovery.service.RegistryService;
import near.me.discovery.service.dto.RegistrationFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistryServiceImpl implements RegistryService {

    @Autowired
    private ClientsRepository repository;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public void add(String clientName, RegistrationFormDto form) {
        final ClientEntity clientEntity = repository.findByClientName(clientName);

        if (clientEntity == null) {
            final ClientEntity entity = new ClientEntity();
            entity.setClientId(UUID.randomUUID().toString());
            entity.setClientName(clientName);
            entity.setInfo(new HashSet<>(Arrays.asList(ClientInfo.builder().urlPath(form.getUrlPath()).healthCheck(form.getHealthCheck()).build())));
            repository.save(entity);
        } else {
            clientEntity.getInfo().add(ClientInfo.builder().urlPath(form.getUrlPath()).healthCheck(form.getHealthCheck()).build());
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Optional<ClientInfo> getByClientName(String clientName) {
        final ClientEntity byClientName = repository.findByClientName(clientName);
        if (byClientName != null) {
            return byClientName.getInfo().stream().findAny();
        }
        return Optional.empty();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void delete(String clientName, String clientHost) {
        final ClientEntity byClientName = repository.findByClientName(clientName);
        if (byClientName != null) {
            byClientName.getInfo().removeIf(info -> info.getUrlPath().equals(clientHost));
        }
    }
}
