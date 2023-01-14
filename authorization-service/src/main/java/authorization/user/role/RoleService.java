package authorization.user.role;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;

    public Set<Role> parseRoles(Set<String> roles) {
        Set<Role> result = new HashSet<>();

        if (roles.contains("Admin")) {
            result.add(findRoleByName(ERole.ROLE_ADMIN));
        } else {
            result.add(findRoleByName(ERole.ROLE_USER));
        }

        return result;
    }

    private Role findRoleByName(ERole eRole) {
        return roleRepository
                .findByName(eRole)
                .orElseThrow(() -> new RoleNotFoundException(eRole.name()));
    }
}
