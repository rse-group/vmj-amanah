package auth.management.core;

import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.CascadeType;

import java.util.*;

@MappedSuperclass
public abstract class UserRoleDecorator extends UserRoleComponent{

    @OneToOne(cascade=CascadeType.ALL)
    protected UserRoleComponent userRole;

    public UserRoleDecorator(UserRoleComponent userRole) {
    	this.id = UUID.randomUUID();
        this.userRole = userRole;
    }
    
    public UserRoleDecorator(UUID id, UserRoleComponent userRole) {
        this.id = id;
        this.userRole = userRole;
    }

    public UserRoleDecorator() {
        super();
        this.id = UUID.randomUUID();
        this.userRole = new UserRoleImpl();
    }


    public void setUserRole(UserRoleComponent userRole) {
        this.userRole = userRole;
    }

    public UserRoleComponent getUserRole() {
        return this.userRole;
    }

    @Override
    public void setRole(Role role) {
        this.userRole.setRole(role);
    }

    @Override
    public Role getRole() {
        return this.userRole.getRole();
    }

    @Override
    public HashMap<String, Object> toHashMap() {
        return this.userRole.toHashMap();
    }

}
