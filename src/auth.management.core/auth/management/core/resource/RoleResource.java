package auth.management.core;

import java.util.*;

import vmj.routing.route.VMJExchange;

public interface RoleResource {
    List<HashMap<String,Object>> saveRole(VMJExchange vmjExchange);
    HashMap<String,Object> updateRole(VMJExchange vmjExchange);
    HashMap<String,Object> getRole(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllRole(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteRole(VMJExchange vmjExchange);
}
