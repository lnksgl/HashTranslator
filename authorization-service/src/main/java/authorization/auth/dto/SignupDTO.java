package authorization.auth.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SignupDTO {

    String email;
    String password;
    Set<String> roles;
}
