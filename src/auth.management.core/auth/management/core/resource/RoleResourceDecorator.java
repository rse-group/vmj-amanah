package auth.management.core;

import java.util.*;

import vmj.routing.route.VMJExchange;

public abstract class RoleResourceDecorator extends RoleResourceComponent{
    protected RoleResourceComponent record;

    public RoleResourceDecorator(RoleResourceComponent record) {
        this.record = record;
    }

    public List<HashMap<String,Object>> saveRole(VMJExchange vmjExchange){
        return record.saveRole(vmjExchange);
    }

    public HashMap<String,Object> updateRole(VMJExchange vmjExchange){
        return record.updateRole(vmjExchange);
    }

    public Role updateRole(VMJExchange vmjExchange, UUID id){
        return record.updateRole(vmjExchange, id);
    }

    public HashMap<String,Object> getRole(VMJExchange vmjExchange){
        return record.getRole(vmjExchange);
    }

    public List<HashMap<String,Object>> getAllRole(VMJExchange vmjExchange){
        return record.getAllRole(vmjExchange);
    }

    public List<HashMap<String,Object>> deleteRole(VMJExchange vmjExchange){
        return record.deleteRole(vmjExchange);
    }

    public List<HashMap<String,Object>> transformRoleListToHashMap(List<Role> roleList){
        return record.transformRoleListToHashMap(roleList);
    }
}
