package near.me.discovery.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationFormDto {
    private final String urlPath;
    private final String healthCheck;

    public RegistrationFormDto(String urlPath, String healthCheck) {
        this.urlPath = urlPath;
        this.healthCheck = healthCheck;
    }
}
