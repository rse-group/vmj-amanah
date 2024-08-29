package auth.management.core;

import java.util.*;

import vmj.routing.route.VMJExchange;

public interface UserRoleResource {
    HashMap<String,Object> saveUserRole(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllUserRole(VMJExchange vmjExchange);
    List<HashMap<String,Object>> transformUserRoleListToHashMap(List<UserRole> userRoleList);
}
