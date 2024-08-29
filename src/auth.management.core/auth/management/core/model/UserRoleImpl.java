package auth.management.core;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.*;

@Entity(name="auth_user_role_impl")
@Table(name="auth_user_role_impl")
public class UserRoleImpl extends UserRoleComponent {


    @ManyToOne(targetEntity=auth.management.core.RoleComponent.class)
    @JoinColumn(name = "authRole", referencedColumnName = "id")
    public Role role;

    @ManyToOne(targetEntity=auth.management.core.UserImpl.class)
    @JoinColumn(name = "authUser", referencedColumnName = "id")
    public User user;

    public UserRoleImpl() {
    	this.id = UUID.randomUUID();
    }

    public UserRoleImpl(Role role, User user) {
    	this.id = UUID.randomUUID();
        this.role = role;
        this.user = user;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Role getRole() {
        return this.role;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return this.user;
    }
}
