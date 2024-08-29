package auth.management.core;

import java.util.*;

import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.RepositoryUtil;

public abstract class UserRoleResourceComponent implements UserRoleResource {
    protected RepositoryUtil<UserRole> userRoleDao;

    public UserRoleResourceComponent() {
        this.userRoleDao = new RepositoryUtil<UserRole>(auth.management.core.UserRoleComponent.class);
    }

    public abstract HashMap<String,Object> saveUserRole(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllUserRole(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformUserRoleListToHashMap(List<UserRole> userRoleList);
}
