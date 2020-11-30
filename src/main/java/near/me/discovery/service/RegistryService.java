package near.me.discovery.service;

import near.me.discovery.repository.entity.ClientInfo;
import near.me.discovery.service.dto.RegistrationFormDto;

import java.util.Optional;

public interface RegistryService {
    void add(String clientName, RegistrationFormDto form);

    Optional<ClientInfo> getByClientName(String clientName);

    void delete(String clientName, String clientHost);
}
