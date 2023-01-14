package authorization.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponseDTO {

    String token;
    String type;
    Long id;
    String email;
    List<String> roles;
}
