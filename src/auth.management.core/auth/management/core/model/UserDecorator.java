package auth.management.core;

import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;
import java.util.HashMap;
import java.util.UUID;

@MappedSuperclass
public abstract class UserDecorator extends UserComponent {

    @OneToOne()
    public UserComponent user;

    public UserDecorator(UserComponent user) {
    	this.id = UUID.randomUUID();
        this.user = user;
    }

    public UserDecorator(UUID id, UserComponent user) {
        this.id = id;
        this.user = user;
    }

    public UserDecorator() {
        super();
        this.id = UUID.randomUUID();
        this.user = new UserImpl();
    }

    public UserComponent getUser() {
        return this.user;
    }

    public void setUser(UserComponent user) {
        this.user = user;
    }

    @Override
    public void setName(String name) {
        this.user.setName(name);
    };

    @Override
    public String getName() {
        return this.user.getName();
    };

    @Override
    public void setEmail(String email) {
        this.user.setEmail(email);
    };

    @Override
    public String getEmail() {
        return this.user.getEmail();
    };

    @Override
    public void setAllowedPermissions(String allowedPermissions) {
        this.user.setAllowedPermissions(allowedPermissions);
    };

    @Override
    public String getAllowedPermissions() {
        return this.user.getAllowedPermissions();
    };

    @Override
    public HashMap<String, Object> toHashMap() {
        return this.user.toHashMap();
    }
}
