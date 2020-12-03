package near.me.discovery.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfo {

    @Id
    private String id;

    private String urlPath;
    private String healthCheck;
    private Boolean available = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientInfo that = (ClientInfo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
