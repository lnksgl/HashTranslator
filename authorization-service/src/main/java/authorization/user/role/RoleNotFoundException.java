package authorization.user.role;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String name) {
        super("Role not found. RoleName: " + name);
    }
}
