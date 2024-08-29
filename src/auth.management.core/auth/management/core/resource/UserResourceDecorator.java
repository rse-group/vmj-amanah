package auth.management.core;

import java.util.*;

import vmj.routing.route.VMJExchange;

public abstract class UserResourceDecorator extends UserResourceComponent {
    
    protected UserResourceComponent record;

    public UserResourceDecorator(UserResourceComponent record) {
        this.record = record;
    }

    public HashMap<String,Object> saveUser(VMJExchange vmjExchange){
        return record.saveUser(vmjExchange);
    }

    public User createUser(VMJExchange vmjExchange){
        return record.createUser(vmjExchange);
    }
    
    public User createUserWithPerms(VMJExchange vmjExchange){
        return record.createUserWithPerms(vmjExchange);
    }

    public HashMap<String,Object> updateUser(VMJExchange vmjExchange){
        return record.updateUser(vmjExchange);
    }

    public HashMap<String,Object> getUser(VMJExchange vmjExchange){
        return record.getUser(vmjExchange);
    }

    public List<HashMap<String,Object>> getAllUser(VMJExchange vmjExchange){
        return record.getAllUser(vmjExchange);
    }

    public List<HashMap<String,Object>> deleteUser(VMJExchange vmjExchange){
        return record.deleteUser(vmjExchange);
    }

    public List<HashMap<String,Object>> transformUserListToHashMap(List<User> userList){
        return record.transformUserListToHashMap(userList);
    }
}
