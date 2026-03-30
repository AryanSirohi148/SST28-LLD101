import java.util.Objects;

public class User {
    private final String id;
    private final String name;
    private final String email;
    private final Role role;

    public User(String id, String name, String email, Role role) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email).toLowerCase();
        this.role = Objects.requireNonNull(role);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", role=" + role +
            '}';
    }
}
