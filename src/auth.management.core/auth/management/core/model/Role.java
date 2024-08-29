package auth.management.core;

import java.util.HashMap;
import java.util.UUID;

public interface Role {
    void setId(UUID id);
    UUID getId();

    void setName(String name);
    String getName();
    
    void setAllowedPermissions(String allowedPermissions);
    String getAllowedPermissions();

    HashMap<String, Object> toHashMap();
}
