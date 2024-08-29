package auth.management.core;


import java.util.HashMap;
import java.util.UUID;

public interface UserRole {
    void setId(UUID id);
    UUID getId();

    void setRole(Role role);
    Role getRole();

    void setUser(User user);
    User getUser();

    HashMap<String, Object> toHashMap();
}
