package auth.management.core;

import java.util.*;

import vmj.routing.route.VMJExchange;

public abstract class UserRoleResourceDecorator extends UserRoleResourceComponent {
    protected UserRoleResourceComponent record;

    public UserRoleResourceDecorator(UserRoleResourceComponent record) {
        this.record = record;
    }

    public HashMap<String,Object> saveUserRole(VMJExchange vmjExchange){
        return this.record.saveUserRole(vmjExchange);
    }

    public List<HashMap<String,Object>> getAllUserRole(VMJExchange vmjExchange){
        return this.record.getAllUserRole(vmjExchange);
    }

    public List<HashMap<String,Object>> transformUserRoleListToHashMap(List<UserRole> userRoleList){
        return this.record.transformUserRoleListToHashMap(userRoleList);
    }
}
