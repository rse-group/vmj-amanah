package auth.management.core;

import java.util.*;

import auth.management.UserFactory;
import auth.management.UserRoleFactory;
import auth.management.utils.PermissionUtils;
import vmj.auth.annotations.Restricted;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;
import vmj.hibernate.integrator.RepositoryUtil;

public class UserResourceImpl extends UserResourceComponent {
    public RepositoryUtil<UserRoleImpl> userRoleDao;

    public UserResourceImpl() {
        this.userRoleDao = new RepositoryUtil<UserRoleImpl>(auth.management.core.UserRoleImpl.class);
    }

    private ArrayList<UUID> splitRoleId(String roleIds) {
        List<String> tempIds = Arrays.asList(roleIds.split(","));
        ArrayList<UUID> uuidIds = new ArrayList<UUID>();
        try{
            for (String id : tempIds) {
                if (id != ""){
                    uuidIds.add(UUID.fromString(id));
                }
            }
        } catch (Exception e){
            return uuidIds;
        }
        return uuidIds;
    }

    public List<HashMap<String,Object>> transformUserListToHashMap(List<User> userList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();

        for(int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            HashMap<String,Object> userDataMap = user.toHashMap();
            ArrayList<String> allowedPerms = PermissionUtils.getUserAllowedPerms(user.getId(), user.getAllowedPermissions());
            userDataMap.put("allowedPermissions", allowedPerms);
            UUID id = (UUID) userDataMap.get("id");
            List<UserRoleImpl> userRoles = userRoleDao.getListObject("auth_user_role_impl", "authuser", id);
            List<HashMap<String,Object>> userRolesDatas = transformUserRoleListToHashMap(userRoles);
            HashMap<String,Object> userRolesDatasMap = new HashMap<>();
            userRolesDatasMap.put("userRoles", userRolesDatas);
            userDataMap.putAll(userRolesDatasMap);


            resultList.add(userDataMap);
        }

        return resultList;
    }

    public List<HashMap<String,Object>> transformUserRoleListToHashMap(List<UserRoleImpl> userRoleList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < userRoleList.size(); i++) {
            resultList.add(userRoleList.get(i).toHashMap());
        }

        return resultList;
    }

    public List<String> getUserRoleListName(List<UserRoleImpl> userRoleList) {
        List<String> resultList = new ArrayList<String>();
        for(int i = 0; i < userRoleList.size(); i++) {
            resultList.add(userRoleList.get(i).getRole().getName());
        }

        return resultList;
    }

    public List<String> getUserRoleListId(List<UserRoleImpl> userRoleList) {
        List<String> resultList = new ArrayList<String>();
        for(int i = 0; i < userRoleList.size(); i++) {
            UUID id = userRoleList.get(i).getRole().getId();
            String idStr = id.toString();
            resultList.add(idStr);
        }

        return resultList;
    }

    @Route(url="auth/register")
    public HashMap<String,Object> saveUser(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        User user = createUser(vmjExchange);
        HashMap<String, Object> value = new HashMap<>();
        userDao.saveObject(user);
        value = user.toHashMap();
        return value;
    }

    public User createUser(VMJExchange vmjExchange) {
        Object name = vmjExchange.getRequestBodyForm("name");
        Object email = vmjExchange.getRequestBodyForm("email");
        User user = UserFactory.createUser("auth.management.core.UserImpl", (String) name, (String) email);
        userDao.saveObject(user);
        return user;
    }

    public User createUserWithPerms(VMJExchange vmjExchange) {
        Object name = vmjExchange.getRequestBodyForm("name");
        Object email = vmjExchange.getRequestBodyForm("email");
        Object allowedPermissions = vmjExchange.getRequestBodyForm("allowedPermissions");
        User user = UserFactory.createUser("auth.management.core.UserImpl", (String) name, (String) email, (String) allowedPermissions);
        userDao.saveObject(user);
        return user;
    }

    public User updateUser(VMJExchange vmjExchange, UUID idUser) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String name = (String) vmjExchange.getRequestBodyForm("name");
        String email = (String) vmjExchange.getRequestBodyForm("email");
        String allowedPermissions = (String) vmjExchange.getRequestBodyForm("allowedPermissions");

        User user = userDao.getObject(idUser);
        user.setName(name);
        user.setEmail(email);
        user.setAllowedPermissions(allowedPermissions);
        userDao.updateObject(user);
        return user;
    }

    @Restricted(permissionName = "administrator")
    @Route(url = "call/user/update")
    public HashMap<String, Object> updateUser(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID idUser = UUID.fromString(idStr);
        try{
            User user = updateUser(vmjExchange, idUser);
            HashMap<String,Object> userDataMap = user.toHashMap();
            ArrayList<String> allowedPerms = PermissionUtils.getUserAllowedPerms(user.getId(), user.getAllowedPermissions());
            userDataMap.put("allowedPermissions", allowedPerms);
            List<UserRoleImpl> userRoles = userRoleDao.getListObject("auth_user_role_impl", "authuser", idUser);
            List<String> userRolesName = getUserRoleListName(userRoles);
            List<HashMap<String,Object>> userRolesDatas = transformUserRoleListToHashMap(userRoles);
            HashMap<String,Object> userRolesDatasMap = new HashMap<>();
            userRolesDatasMap.put("userRoles", userRolesDatas);
            userRolesDatasMap.put("userRolesName", (userRolesName));
            userDataMap.putAll(userRolesDatasMap);

            return userDataMap;
        } catch (NullPointerException e) {
            throw new BadRequestException("User with id " + idUser + " not found");
        }

    }

    @Restricted(permissionName = "administrator")
    @Route(url = "call/user/changerole")
    public HashMap<String, Object> changeRole(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }

        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID idUser = UUID.fromString(idStr);

        try{
            String roleIds = (String) vmjExchange.getRequestBodyForm("roleIds");
            if (roleIds == null){
                roleIds = "";
            }

            ArrayList<UUID> arrRoleIds = this.splitRoleId(roleIds);

            User user = userDao.getObject(idUser);
            List<UserRoleImpl> userRoles = userRoleDao.getListObject("auth_user_role_impl", "authuser", idUser);
            List<UUID> existedUserRolesId = new ArrayList<UUID>();


            for (UserRoleImpl userRole : userRoles) {
                UUID idRole = userRole.getRole().getId();
                existedUserRolesId.add(idRole);
            }

            for (UserRoleImpl userRole : userRoles) {
                if (!arrRoleIds.contains(userRole.getRole().getId())) {
                    // delete userRole when unchecked
                    userRoleDao.deleteObject(userRole.getId());
                }
            }

            for (UUID roleId : arrRoleIds) {
                if (!existedUserRolesId.contains(roleId)){
                    // add userRole when checked
                    RepositoryUtil<Role> roleDao = new RepositoryUtil<Role>(auth.management.core.RoleComponent.class);
                    Role role = roleDao.getObject(roleId);
                    UserRoleImpl userRole = UserRoleFactory.createUserRole("auth.management.core.UserRoleImpl", (RoleComponent) role, (UserComponent) user);
                    userRoleDao.saveObject(userRole);
                }
            }

            HashMap<String,Object> userDataMap = user.toHashMap();
            ArrayList<String> allowedPerms = PermissionUtils.getUserAllowedPerms(user.getId(), user.getAllowedPermissions());
            userDataMap.put("allowedPermissions", allowedPerms);
            // to refresh newest user role list
            userRoles = userRoleDao.getListObject("auth_user_role_impl", "authuser", idUser);
            List<String> userRolesName = getUserRoleListName(userRoles);
            List<HashMap<String,Object>> userRolesDatas = transformUserRoleListToHashMap(userRoles);
            HashMap<String,Object> userRolesDatasMap = new HashMap<>();
            userRolesDatasMap.put("userRoles", userRolesDatas);
            userRolesDatasMap.put("userRolesName", userRolesName);
            userDataMap.putAll(userRolesDatasMap);

            return userDataMap;
        } catch (NullPointerException e) {
            throw new BadRequestException("User with id " + idUser + " not found");
        }
    }

    @Restricted(permissionName = "administrator")
    @Route(url="call/user/detail")
    public HashMap<String,Object> getUser(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = vmjExchange.getGETParam("id");
        UUID id = UUID.fromString(idStr);
        User user = userDao.getObject(id);
        HashMap<String,Object> userDataMap = user.toHashMap();
        ArrayList<String> allowedPerms = PermissionUtils.getUserAllowedPerms(user.getId(), user.getAllowedPermissions());
        userDataMap.put("allowedPermissions", allowedPerms);
        List<UserRoleImpl> userRoles = userRoleDao.getListObject("auth_user_role_impl", "authuser", id);
        List<String> userRolesName = getUserRoleListName(userRoles);
        List<HashMap<String,Object>> userRolesDatas = transformUserRoleListToHashMap(userRoles);
        HashMap<String,Object> userRolesDatasMap = new HashMap<>();
        List<String> userRolesId = getUserRoleListId(userRoles);
        userRolesDatasMap.put("roleIds", String.join(",", userRolesId));
        userRolesDatasMap.put("userRoles", userRolesDatas);
        userRolesDatasMap.put("userRolesName", userRolesName);
        userDataMap.putAll(userRolesDatasMap);
        return userDataMap;
    }

    @Restricted(permissionName = "administrator")
    @Route(url="call/user/list")
    public List<HashMap<String,Object>> getAllUser(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        List<User> userList = userDao.getAllObject("auth_user_impl");
        List<HashMap<String,Object>> userDatas = transformUserListToHashMap(userList);
        return userDatas;
    }

    public List<HashMap<String,Object>> deleteUser(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID id = UUID.fromString(idStr);
        User user = userDao.getObject(id);
        userDao.deleteObject(id);
        return getAllUser(vmjExchange);
    }
}
