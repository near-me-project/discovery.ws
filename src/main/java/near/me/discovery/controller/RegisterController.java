package near.me.discovery.controller;

import advice.discovery.server.discovery.repository.entity.ClientInfo;
import advice.discovery.server.discovery.service.RegistryService;
import advice.discovery.server.discovery.service.dto.RegistrationFormDto;
import near.me.discovery.controller.model.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/client")
public class RegisterController {

    private RegistryService registryService;

    @Autowired
    public RegisterController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @PostMapping(path = "/{clientName}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerMyHost(@PathVariable(name = "clientName") String clientName, @RequestBody RegistrationForm form) {
        registryService.add(clientName, new RegistrationFormDto(form.getUrlPath(), form.getHealthCheck()));
    }

    @GetMapping(path = "/pathTo")
    public ResponseEntity<String> getClientHost(@RequestParam(name = "client") String clientName) {
        Optional<ClientInfo> clientHost = registryService.getByClientName(clientName);
        return clientHost.map(s -> new ResponseEntity<>(s.getUrlPath(), HttpStatus.FOUND)).orElseGet(() -> new ResponseEntity<>("", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{clientName}/{clientHost}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteHost(@PathVariable(name = "clientName") String clientName, @PathVariable(name = "clientHost") String clientHost) {
        registryService.delete(clientName, clientHost);
    }
}
