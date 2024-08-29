package auth.management.core;

import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.CascadeType;

import java.util.HashMap;
import java.util.UUID;

@MappedSuperclass
public abstract class RoleDecorator extends RoleComponent {

    @OneToOne(cascade=CascadeType.ALL)
    protected RoleComponent role;

    public RoleDecorator(RoleComponent role) {
    	this.id = UUID.randomUUID();
        this.role = role;
    }

    public RoleDecorator(UUID id, RoleComponent role) {
        this.id = id;
        this.role = role;
    }

    public RoleDecorator() {
        super();
        this.id = UUID.randomUUID();
        this.role = new RoleImpl();
    }

    public RoleComponent getRole() {
        return this.role;
    }

    public void setRole(RoleComponent role) {
        this.role = role;
    }

    @Override
    public void setName(String name) {
        this.role.setName(name);
    };

    @Override
    public String getName() {
        return this.role.getName();
    };

    @Override
    public void setAllowedPermissions(String allowedPermissions) {
        this.role.setAllowedPermissions(allowedPermissions);
    };

    @Override
    public String getAllowedPermissions() {
        return this.role.getAllowedPermissions();
    };

    @Override
    public HashMap<String, Object> toHashMap() {
        return this.role.toHashMap();
    }

}
