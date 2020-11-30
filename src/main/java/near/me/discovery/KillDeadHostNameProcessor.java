package near.me.discovery;

import javafx.util.Pair;
import near.me.discovery.repository.ClientsRepository;
import near.me.discovery.repository.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KillDeadHostNameProcessor {

    private ClientsRepository repository;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private RestClient restClient;

    @Autowired
    public KillDeadHostNameProcessor(ClientsRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {

        List<ClientEntity> clientEntities = repository.findAll();
        clientEntities.forEach(entity -> executorService.submit(() -> checkClientAvailability(entity)));

    }

    private void kill(ClientEntity key) {

    }

    private void checkClientAvailability(ClientEntity clientEntity) {
       clientEntity.getInfo().forEach(clientInfo -> restClient.GET(clientInfo.getHealthCheck()));

    }
}
