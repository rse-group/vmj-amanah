package auth.management.core;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="auth_role_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class RoleComponent implements Role {

    @Id
    public UUID id;

    @OneToMany(mappedBy="role")
    public Set<UserRoleImpl> userRoles = new HashSet();

    @Override
    public void setId(UUID id) {
        this.id = id;
    };

    @Override
    public UUID getId() {
        return this.id;
    };

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> roleMap = new HashMap<String,Object>();
        roleMap.put("name", getName());
        roleMap.put("allowedPermissions", getAllowedPermissions());
        roleMap.put("id", getId());
        return roleMap;
    }

}
