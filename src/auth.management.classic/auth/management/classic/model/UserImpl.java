package auth.management.classic;

import java.util.HashMap;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import auth.management.core.UserComponent;
import auth.management.core.UserDecorator;

@Entity(name="auth_user_passworded")
@Table(name="auth_user_passworded")
public class UserImpl extends UserDecorator {

    @Column
    public String password;

    public UserImpl() {
        super();
    }

    public UserImpl(UUID id, UserComponent user, String password) {
        super(id, user);
        this.password = password;
    }
    
    public UserImpl(UserComponent user, String password) {
        super(user);
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return this.password;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> userMap = user.toHashMap();
        userMap.put("id", id);
        return userMap;
    }
}
