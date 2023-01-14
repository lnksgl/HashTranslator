package authorization.auth.service;

import authorization.auth.dto.JwtResponseDTO;
import authorization.auth.dto.SigninDTO;
import authorization.auth.dto.SignupDTO;
import authorization.auth.config.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import authorization.user.service.UserService;

import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthService {

    UserService userService;
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;

    public JwtResponseDTO signin(SigninDTO signinDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinDTO.getEmail(), signinDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthUserDetailsImpl userDetails = (AuthUserDetailsImpl) authentication.getPrincipal();

        return JwtResponseDTO.builder()
                .token(jwtUtils.generateJwt(authentication))
                .type("Bearer")
                .id(userDetails.getId())
                .email(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .build();
    }

    public ResponseEntity<?> signup(SignupDTO signupDTO) {
        if (userService.existsByEmail(signupDTO.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Error: email is exist.");
        }

        userService.createUser(signupDTO);

        return ResponseEntity.ok("User successfully created.");
    }
}
