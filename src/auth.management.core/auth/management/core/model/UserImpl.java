package auth.management.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name="auth_user_impl")
@Table(name="auth_user_impl")
public class UserImpl extends UserComponent {

    @Column
    protected String name;

    @Column(unique = true)
    protected String email;

    @Column
    protected String allowedPermissions;
    

    public UserImpl(String name, String email, String allowedPermissions) {
    	this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.allowedPermissions = allowedPermissions;
    }

    public UserImpl(String name, String email) {
    	this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.allowedPermissions = "";
    }

    public UserImpl() {
    	this.id = UUID.randomUUID();
        this.name = "";
        this.email = "";
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
    public void setEmail(String email) {
        this.email = email;
    };

    @Override
    public String getEmail() {
        return this.email;
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
