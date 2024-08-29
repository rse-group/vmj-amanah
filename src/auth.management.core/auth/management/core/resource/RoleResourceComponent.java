package auth.management.core;

import java.util.*;

import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.RepositoryUtil;

public abstract class RoleResourceComponent implements RoleResource {
    protected RepositoryUtil<Role> roleDao;

    public RoleResourceComponent() {
        this.roleDao = new RepositoryUtil<Role>(auth.management.core.RoleComponent.class);
    }

    public abstract List<HashMap<String,Object>> saveRole(VMJExchange vmjExchange);
    public abstract Role createRole(VMJExchange vmjExchange);
    public abstract HashMap<String,Object> updateRole(VMJExchange vmjExchange);
    public abstract Role updateRole(VMJExchange vmjExchange, UUID id);
    public abstract HashMap<String,Object> getRole(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllRole(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> deleteRole(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformRoleListToHashMap(List<Role> roleList);
}
