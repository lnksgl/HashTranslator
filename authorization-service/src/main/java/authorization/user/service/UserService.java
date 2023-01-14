package authorization.user.service;

import authorization.auth.dto.SignupDTO;
import authorization.user.entity.User;
import authorization.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import authorization.user.role.RoleService;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserService {

    RoleService roleService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public void createUser(SignupDTO signupDTO) {
        userRepository.save(
                User.builder()
                        .email(signupDTO.getEmail())
                        .password(passwordEncoder.encode(signupDTO.getPassword()))
                        .roles(roleService.parseRoles(signupDTO.getRoles()))
                        .build()
        );
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> deleteUser(Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User successfully deleted.");
    }
}
