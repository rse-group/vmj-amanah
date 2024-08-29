package auth.management.core;

import java.util.*;

import auth.management.UserRoleFactory;
import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.RepositoryUtil;

public class UserRoleResourceImpl extends UserRoleResourceComponent {

    public List<HashMap<String,Object>> getAllUserRole(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("id_user");
        UUID id = UUID.fromString(idStr);
        List<UserRole> userRoles = userRoleDao.getListObject("auth_user_role_impl", "authUser", id);
        List<HashMap<String,Object>> userRolesDatas = transformUserRoleListToHashMap(userRoles);
        return userRolesDatas;
    }

    private ArrayList<String> splitIdRole(String idRole) {
        List<String> tempId = Arrays.asList(idRole.split(","));
        return new ArrayList<String>(tempId);
    }

    public HashMap<String,Object> saveUserRole(VMJExchange vmjExchange) {
        String userId = vmjExchange.getGETParam("userId");
        UUID id = UUID.fromString(userId);
        String rolesIdStr = vmjExchange.getGETParam("rolesId");
        List<String> rolesId = this.splitIdRole(rolesIdStr);
        RepositoryUtil<User> userDao = new RepositoryUtil<User>(auth.management.core.UserComponent.class);
        User user = userDao.getObject(id);
        RepositoryUtil<RoleImpl> roleDao = new RepositoryUtil<RoleImpl>(auth.management.core.RoleImpl.class);
        HashMap<String, Object> hasil = new HashMap<>();
        for (String roleId : rolesId) {
        	UUID roleIdUUID = UUID.fromString(roleId);
            RoleImpl role = roleDao.getObject(roleIdUUID);
            if (role != null){
                UserRoleImpl userRole = UserRoleFactory.createUserRole("auth.management.core.UserRoleImpl", (RoleComponent) role, (UserComponent) user);
                userRoleDao.saveObject(userRole);
            }
        }
        return hasil;
    }
    
    public List<HashMap<String,Object>> transformUserRoleListToHashMap(List<UserRole> userRoleList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < userRoleList.size(); i++) {
            resultList.add(userRoleList.get(i).toHashMap());
        }
        return resultList;
    }
}
