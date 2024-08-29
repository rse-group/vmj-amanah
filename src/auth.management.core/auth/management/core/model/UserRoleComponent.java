package auth.management.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import java.util.*;

@Entity
@Table(name="auth_user_role_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserRoleComponent implements UserRole{
    
    @Id
    protected UUID id;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public abstract void setRole(Role role);
    public abstract Role getRole();

    public abstract void setUser(User user);
    public abstract User getUser();

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> userRoleMap = new HashMap<String,Object>();
        userRoleMap.put("id", this.getId());
        userRoleMap.put("role", this.getRole().toHashMap());
        return userRoleMap;
    }
    
}
