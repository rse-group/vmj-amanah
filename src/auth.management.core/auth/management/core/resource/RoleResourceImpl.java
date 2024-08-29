package auth.management.core;

import java.util.*;

import vmj.auth.annotations.Restricted;
import auth.management.RoleFactory;
import auth.management.utils.PermissionUtils;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public class RoleResourceImpl extends RoleResourceComponent {
    
    public RoleResourceImpl() {}

    @Restricted(permissionName = "administrator")
    @Route(url = "call/role/save")
    public List<HashMap<String,Object>> saveRole(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        Role role = createRole(vmjExchange);
        roleDao.saveObject(role);
        return getAllRole(vmjExchange);
    }

    public Role createRole(VMJExchange vmjExchange) {
        Object name = vmjExchange.getRequestBodyForm("name");
        Object allowedPermissions = vmjExchange.getRequestBodyForm("allowedPermissions");
        Role role = RoleFactory.createRole("auth.management.core.RoleImpl", (String) name, (String) allowedPermissions);
        return role;
    }

    public List<HashMap<String,Object>> transformRoleListToHashMap(List<Role> roleList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < roleList.size(); i++) {
            HashMap<String,Object> roleDataMap = roleList.get(i).toHashMap();
            roleDataMap.put("allowedPermissions", PermissionUtils.splitPermissions((String)roleDataMap.get("allowedPermissions")));
            resultList.add(roleDataMap);
        }

        return resultList;
    }

    @Restricted(permissionName = "administrator")
    @Route(url="call/role/list")
    public List<HashMap<String,Object>> getAllRole(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        List<Role> roles = roleDao.getAllObject("auth_role_impl");
        List<HashMap<String,Object>> roleDatas = transformRoleListToHashMap(roles);
        return roleDatas;
    }

    @Restricted(permissionName = "administrator")
    @Route(url="call/role/delete")
    public List<HashMap<String,Object>> deleteRole(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID id = UUID.fromString(idStr);
        roleDao.deleteObject(id);
        return getAllRole(vmjExchange);
    }

    public Role updateRole(VMJExchange vmjExchange, UUID id) {
        Role role = roleDao.getObject(id);
        role.setName((String) vmjExchange.getRequestBodyForm("name"));
        role.setAllowedPermissions((String) vmjExchange.getRequestBodyForm("allowedPermissions"));
        
        return role;
    }

    @Restricted(permissionName = "administrator")
    @Route(url="call/role/update")
    public HashMap<String, Object> updateRole(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID id = UUID.fromString(idStr);
        
        Role role = roleDao.getObject(id);
        role = updateRole(vmjExchange, id);
        roleDao.updateObject(role);
        HashMap<String,Object> roleDataMap = role.toHashMap();
        roleDataMap.put("allowedPermissions", PermissionUtils.splitPermissions((String)roleDataMap.get("allowedPermissions")));
        return roleDataMap;
    }

    @Restricted(permissionName = "administrator")
    @Route(url="call/role/detail")
    public HashMap<String, Object> getRole(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = vmjExchange.getGETParam("id");
        UUID id = UUID.fromString(idStr);
        Role role = roleDao.getObject(id);
        HashMap<String,Object> roleDataMap = role.toHashMap();
        roleDataMap.put("allowedPermissions", PermissionUtils.splitPermissions((String)roleDataMap.get("allowedPermissions")));
        return roleDataMap;
    }

}
