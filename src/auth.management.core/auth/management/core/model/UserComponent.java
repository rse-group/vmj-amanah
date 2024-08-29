package auth.management.core;

import java.util.HashMap;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="auth_user_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserComponent implements User {

    @Id
    protected UUID id;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public abstract void setName(String name);
    public abstract String getName();

    public abstract void setEmail(String email);
    public abstract String getEmail();

    public abstract void setAllowedPermissions(String allowedPermissions);
    public abstract String getAllowedPermissions();

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> userMap = new HashMap<String,Object>();
        userMap.put("id", getId());
        userMap.put("name", getName());
        userMap.put("email", getEmail());
        userMap.put("allowedPermissions", getAllowedPermissions());
        return userMap;
    }

}
