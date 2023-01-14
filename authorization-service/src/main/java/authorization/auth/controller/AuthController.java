package authorization.auth.controller;

import authorization.auth.dto.SigninDTO;
import authorization.auth.dto.SignupDTO;
import authorization.auth.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/applications/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninDTO signinDTO) {
        return ResponseEntity.ok(authService.signin(signinDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDTO signupDTO) {
        return authService.signup(signupDTO);
    }
}
