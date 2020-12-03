package near.me.discovery.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import near.me.discovery.repository.entity.ClientInfo;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ClientDto {

    private String id;

    private String clientId;
    private String clientName;
    private Set<ClientInfo> info;
}
