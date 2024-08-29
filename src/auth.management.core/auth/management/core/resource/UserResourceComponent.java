package auth.management.core;

import java.util.*;

import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.RepositoryUtil;

public abstract class UserResourceComponent implements UserResource {
    protected RepositoryUtil<User> userDao;

    public UserResourceComponent() {
        this.userDao = new RepositoryUtil<User>(auth.management.core.UserComponent.class);
    }
    public abstract HashMap<String,Object> saveUser(VMJExchange vmjExchange);
    public abstract User createUser(VMJExchange vmjExchange);
    public abstract User createUserWithPerms(VMJExchange vmjExchange);
    public abstract HashMap<String,Object> updateUser(VMJExchange vmjExchange);
    public abstract HashMap<String,Object> getUser(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllUser(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> deleteUser(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformUserListToHashMap(List<User> userList);
}
