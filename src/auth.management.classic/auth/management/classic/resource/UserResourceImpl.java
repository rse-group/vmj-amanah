package auth.management.classic;

import java.util.*;

import auth.management.UserFactory;
import auth.management.UserRoleFactory;
import auth.management.core.Role;
import auth.management.core.RoleComponent;
import auth.management.core.User;
import auth.management.core.UserComponent;
import auth.management.core.UserDecorator;
import auth.management.core.UserResourceComponent;
import auth.management.core.UserResourceDecorator;
import auth.management.core.UserRoleImpl;
import auth.management.utils.KeepLoginUtils;
import auth.management.utils.PasswordUtils;
import auth.management.utils.PermissionUtils;

import vmj.auth.annotations.Restricted;
import vmj.auth.exceptions.AuthException;
import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

public class UserResourceImpl extends UserResourceDecorator {
    public RepositoryUtil<UserRoleImpl> userRoleDao;
    public RepositoryUtil<Role> roleDao;
    
    public UserResourceImpl(UserResourceComponent record) {
        super(record);
        this.userRoleDao = new RepositoryUtil<UserRoleImpl>(auth.management.core.UserRoleImpl.class);
        this.roleDao = new RepositoryUtil<Role>(auth.management.core.RoleComponent.class);
    }
    
    private String getVerifier() {
    	return "manual";
    }

    @Route(url = "auth/user/save")
    public HashMap<String, Object> saveUser(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        HashMap<String, Object> value = new HashMap<>();
        Object email = vmjExchange.getRequestBodyForm("email");        
        Object passwordRaw = vmjExchange.getRequestBodyForm("password");
        String password = PasswordUtils.hashPassword((String) passwordRaw);
        List<User> users = userDao.<Object>getListObject("auth_user_impl", "email", email);
        String verifier = getVerifier();
        if (!users.isEmpty()) {
            User user = users.get(0);
            List<User> userPasswordedList = userDao.<Object>getListObject("auth_user_passworded", "user_id", user.getId());
            if (userPasswordedList.size() > 0) {
            	throw new FieldValidationException("User dengan email " + email + " sudah terdaftar");
            }
            
            List<User> userSocialList = userDao.<Object>getListObject("auth_user_social", "user_id", user.getId());
            if (userSocialList.size() > 0){
                ArrayList<String> allowedPerms = PermissionUtils.getUserAllowedPerms(user.getId(), user.getAllowedPermissions(), verifier);
                User userPassworded = UserFactory.createUser("auth.management.classic.UserImpl", user, password);   
                userDao.saveObject(userPassworded);
                value = userPassworded.toHashMap();
                value.put("allowedPermissions", allowedPerms);
                value.put("token", PasswordUtils.generateAuthToken(userPassworded.getId().toString(), userPassworded.getEmail(), verifier));
                value.put("token_keep_login", KeepLoginUtils.generateKeepLoginToken(userPassworded.getId().toString(), userPassworded.getEmail(), String.join(",", allowedPerms), verifier));
                return value;
            }
        }
        UserDecorator userPassworded = (UserDecorator) createUserWithPassword(vmjExchange);
        userDao.saveObject(userPassworded);
        User user = userPassworded.getUser();
        List<Role> roles = roleDao.getListObject("auth_role_impl", "name", "Registered");
        if (roles.size() > 0) {
            Role role = roles.get(0);
            UserRoleImpl userRole = UserRoleFactory.createUserRole("auth.management.core.UserRoleImpl", (RoleComponent) role, (UserComponent) user);
            userRoleDao.saveObject(userRole);
        }
        value = userPassworded.toHashMap();
        ArrayList<String> allowedPerms = PermissionUtils.getUserAllowedPerms(user.getId(), userPassworded.getAllowedPermissions(), verifier);
        value.put("allowedPermissions", allowedPerms);
        value.put("token", PasswordUtils.generateAuthToken(userPassworded.getId().toString(), userPassworded.getEmail(), verifier));
        value.put("token_keep_login", KeepLoginUtils.generateKeepLoginToken(userPassworded.getId().toString(), userPassworded.getEmail(), String.join(",", allowedPerms), verifier));
        return value;
    }

    @Restricted(permissionName = "administrator")
    @Route(url = "call/user/save")
    public List<HashMap<String, Object>> addUser(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }

        String roleIds = (String) vmjExchange.getRequestBodyForm("roleIds");
        if (roleIds == null){
            roleIds = "";
        }
        ArrayList<UUID> arrRoleIds = this.splitRoleId(roleIds);

        UserDecorator userPassworded = (UserDecorator) createUserWithPerms(vmjExchange);
        HashMap<String, Object> value = new HashMap<>();
        userDao.saveObject(userPassworded);
        User user = userPassworded.getUser();
        for (UUID roleId : arrRoleIds) {
            Role role = roleDao.getObject(roleId);
            UserRoleImpl userRole = UserRoleFactory.createUserRole("auth.management.core.UserRoleImpl", (RoleComponent) role, (UserComponent) user);
            userRoleDao.saveObject(userRole);
        }
        return record.getAllUser(vmjExchange);
    }

    public User createUserWithPassword(VMJExchange vmjExchange) {
    	Object email = vmjExchange.getRequestBodyForm("email");
    	List<User> users = userDao.<Object>getListObject("auth_user_impl", "email", email);
    	if (!users.isEmpty()) {
    		throw new FieldValidationException("User dengan email " + email + " sudah terdaftar");
    	}
    	
        Object passwordRaw = vmjExchange.getRequestBodyForm("password");
        String password = PasswordUtils.hashPassword((String) passwordRaw);
        User user = record.createUser(vmjExchange);
        User userPassworded = UserFactory.createUser("auth.management.classic.UserImpl", user, password);
        return userPassworded;
    }

    public User createUserWithPerms(VMJExchange vmjExchange) {
    	Object email = vmjExchange.getRequestBodyForm("email");
    	List<User> users = userDao.<Object>getListObject("auth_user_impl", "email", email);
    	if (!users.isEmpty()) {
    		throw new FieldValidationException("User dengan email " + email + " sudah terdaftar");
    	}
    	
        Object passwordRaw = vmjExchange.getRequestBodyForm("password");
        String password = PasswordUtils.hashPassword((String) passwordRaw);
        User user = record.createUserWithPerms(vmjExchange);
        User userPassworded = UserFactory.createUser("auth.management.classic.UserImpl", user, password);
        return userPassworded;
    }

    @Route(url = "auth/forgot-password-token")
    public HashMap<String, Object> getForgotPasswordToken(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        Object email = vmjExchange.getRequestBodyForm("email");

        List<User> users = userDao.<Object>getListObject("auth_user_impl", "email", email);
        HashMap<String, Object> result = new HashMap<>();
        if (users.isEmpty()) {
            throw new BadRequestException("User dengan email " + email + " tidak ditemukan");
        }
        User user = users.get(0);
        List<User> userPasswordedList = userDao.getListObject("auth_user_passworded", "user_id", user.getId());
        if (userPasswordedList.isEmpty()) {
            throw new BadRequestException("User tidak ditemukan");
        }
        String forgotPasswordToken = PasswordUtils.generateForgotToken((String) email);
        result.put("forgotPasswordToken", forgotPasswordToken);
        return result;
    }

    @Route(url = "auth/forgot-password")
    public HashMap<String, Object> forgotPassword(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String token = (String) vmjExchange.getRequestBodyForm("forgotPasswordToken");
        token = token.replace(" ", "+");
        String email = PasswordUtils.getEmailFromForgotToken((String) token);
        Object newPasswordRaw = vmjExchange.getRequestBodyForm("password");
        String newPassword = PasswordUtils.hashPassword((String) newPasswordRaw);
        HashMap<String, Object> result = new HashMap<>();
        List<User> users = userDao.<Object>getListObject("auth_user_impl", "email", email);
        if (users.isEmpty()) {
            System.out.println("Token tidak valid");
        }

        User user = users.get(0);
        List<User> userPasswordedList = userDao.getListObject("auth_user_passworded", "user_id", user.getId());
        if (userPasswordedList.isEmpty()) {
            throw new BadRequestException("User tidak ditemukan");
        }
        
        User userPassworded = userPasswordedList.get(0);
        ((UserImpl) userPassworded).setPassword(newPassword);
        userDao.updateObject(userPassworded);
        return result;
    }

    @Route(url = "auth/login/pwd")
    public HashMap<String, Object> login(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        Object email = vmjExchange.getRequestBodyForm("email");
        Object passwordRaw = vmjExchange.getRequestBodyForm("password");
        String password = PasswordUtils.hashPassword((String) passwordRaw);
        
        List<User> users = userDao.<Object>getListObject("auth_user_impl", "email", email);
        if (users.isEmpty()) {
            HashMap<String, Object> value = new HashMap<>();
            throw new AuthException("User dengan email " + email + " tidak ditemukan");
        }

        User user = users.get(0);
        UUID idUser = user.getId();
        List<User> listUserPassworded = userDao.<Object>getListObject("auth_user_passworded", "user_id", idUser);
        User userPassworded = listUserPassworded.get(0);
        if (!((UserImpl) userPassworded).getPassword().equals(password)) {
            throw new AuthException("email dan password tidak cocok");
        }
        String verifier = getVerifier();
        ArrayList<String> allowedPerms = PermissionUtils.getUserAllowedPerms(user.getId(), user.getAllowedPermissions(), verifier);
        HashMap<String, Object> value = new HashMap<>();
        value.put("token", PasswordUtils.generateAuthToken(user.getId().toString(), (String) email, verifier));
        value.put("name", user.getName());
        value.put("email", email);
        value.put("allowedPermissions", allowedPerms);
        value.put("token_keep_login", KeepLoginUtils.generateKeepLoginToken(user.getId().toString(), user.getEmail(), String.join(",", allowedPerms), verifier));

        return value;
    }

    public List<HashMap<String,Object>> deleteUser(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID idUser = UUID.fromString(idStr);
        List<User> userPasswordedList = userDao.getListObject("auth_user_passworded", "user_id", idUser);
        if (userPasswordedList.size() > 0){
            UserDecorator userPassworded = (UserDecorator) userPasswordedList.get(0);
            UUID idUserPassworded = userPassworded.getId();
            userDao.deleteObject(idUserPassworded);
        }
        return record.deleteUser(vmjExchange);
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
}
