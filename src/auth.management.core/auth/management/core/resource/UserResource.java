package auth.management.core;

import java.util.*;

import vmj.routing.route.VMJExchange;

public interface UserResource {
    HashMap<String,Object> saveUser(VMJExchange vmjExchange);
    HashMap<String,Object> updateUser(VMJExchange vmjExchange);
    HashMap<String,Object> getUser(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllUser(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteUser(VMJExchange vmjExchange);
}
