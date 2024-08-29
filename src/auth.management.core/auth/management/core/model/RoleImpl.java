package auth.management.core;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="auth_role_impl")
@Table(name="auth_role_impl")
public class RoleImpl extends RoleComponent {

    @Column
    public String name;

    @Column(columnDefinition="TEXT", length = 1000)
    public String allowedPermissions;
    
    public RoleImpl(String name, String allowedPermissions) {
    	this.id = UUID.randomUUID();
        this.name = name;
        this.allowedPermissions = allowedPermissions;
    }

    public RoleImpl() {
    	this.id = UUID.randomUUID();
        this.name = "";
        this.allowedPermissions = "";
    }

    @Override
    public void setName(String name) {
        this.name = name;
    };

    @Override
    public String getName() {
        return this.name;
    };

    @Override
    public void setAllowedPermissions(String allowedPermissions) {
        this.allowedPermissions = allowedPermissions;
    };

    @Override
    public String getAllowedPermissions() {
        return this.allowedPermissions;
    };
}
