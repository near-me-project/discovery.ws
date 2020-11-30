package near.me.discovery.repository.entity;

import advice.discovery.server.discovery.repository.entity.ClientInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ClientEntity {

    @Id
    private String id;

    private String clientId;
    private String clientName;
    private Set<ClientInfo> info;
}
