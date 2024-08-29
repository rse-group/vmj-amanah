package auth.management.social;

import java.util.HashMap;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import auth.management.core.UserComponent;
import auth.management.core.UserDecorator;

@Entity(name="auth_user_social")
@Table(name="auth_user_social")
public class UserImpl extends UserDecorator {

    @Column(unique=true)
    public String socialId;

    public UserImpl() {
        super();
    }

    public UserImpl(UUID id, UserComponent user, String socialId) {
        super(id, user);
        this.socialId = socialId;
    }
    
    public UserImpl(UserComponent user, String socialId) {
        super(user);
        this.socialId = socialId;
    }
    
    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialId() {
        return this.socialId;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> userMap = user.toHashMap();
        userMap.put("id", id);
        return userMap;
    }
}
