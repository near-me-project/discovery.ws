package near.me.discovery;

import circuit.breaker.RestClient;
import circuit.breaker.SilentResponse;
import near.me.discovery.repository.ClientsRepository;
import near.me.discovery.repository.entity.ClientEntity;
import near.me.discovery.repository.entity.ClientInfo;
import near.me.discovery.service.dto.ClientDto;
import near.me.discovery.shared.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class KillDeadHostNameProcessor {

    private ClientsRepository repository;
    private RestClient restClient;

    @Autowired
    public KillDeadHostNameProcessor(ClientsRepository repository) {
        this.repository = repository;
        this.restClient = new RestClient();
    }

    @Scheduled(fixedRate = 30000)
    public void kill() {
        System.out.println("EVENT: Started kill dead host processor: " + LocalTime.now());

        List<ClientEntity> clientEntities = repository.findAll();
        List<ClientDto> collect = clientEntities.stream().map(ce -> ModelMapper.map(ce, ClientDto.class)).collect(Collectors.toList());
        collect.forEach(this::checkClientAvailability);

        System.out.println("EVENT: Finished kill dead host processor: " + LocalTime.now());
    }

    private void checkClientAvailability(ClientDto clientDto) {

        Stream<CompletableFuture<Void>> all = clientDto.getInfo().stream()
                .map(clientInfo -> restClient.$.GET(clientInfo.getHealthCheck()).thenAccept(response -> ifFail(response, clientDto, clientInfo)));

        CompletableFuture.allOf(all.toArray(CompletableFuture[]::new)).whenComplete((r, b) -> updateDataBase(repository, clientDto));
    }

    private void updateDataBase(ClientsRepository repository, ClientDto clientDto) {
        repository.save(ModelMapper.map(clientDto, ClientEntity.class));
    }

    private void ifFail(SilentResponse response, ClientDto clientDto, ClientInfo clientInfo) {
        if (!response.getStatusCode().orElse(0).equals(200)) {
            clientDto.getInfo().remove(clientInfo);
            System.out.println("INFO: Host with name: " + clientInfo.getUrlPath() + "has been deleted");
//            clientInfo.setAvailable(false);
        }
    }
}
