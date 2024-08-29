package auth.management.social;

import java.util.*;

import auth.management.UserFactory;
import auth.management.UserRoleFactory;
import auth.management.core.Role;
import auth.management.core.User;
import auth.management.core.UserComponent;
import auth.management.core.UserDecorator;
import auth.management.core.UserResourceComponent;
import auth.management.core.UserResourceDecorator;
import auth.management.core.UserRoleImpl;
import auth.management.core.RoleComponent;
import auth.management.utils.KeepLoginUtils;
import auth.management.utils.PasswordUtils;
import auth.management.utils.PermissionUtils;
import auth.management.social.utils.VerifierUtils;

import vmj.auth.annotations.Restricted;
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
    
    private String getVerifier(String socialToken) {
    	String verifier = VerifierUtils.getVerifierFromToken(socialToken);
    	return verifier;
    }

    @Route(url = "auth/login/social")
    public HashMap<String, Object> saveUser(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        Object socialId = vmjExchange.getRequestBodyForm("social_id");
        Object socialToken = vmjExchange.getRequestBodyForm("social_token");
        String verifier = getVerifier((String)socialToken);
        if (verifier == null || verifier.isEmpty()) {
            throw new BadRequestException("Social token tidak valid");
        }
        List<User> userSocialList = userDao.getListObject("auth_user_social", "socialid", socialId);
        Object email = vmjExchange.getRequestBodyForm("email");
        HashMap<String, Object> value = new HashMap<>();
        if (userSocialList.size() > 0){
            UserDecorator userSocial = (UserDecorator) userSocialList.get(0);
            UUID userSocialId = userSocial.getUser().getId();
            ArrayList<String> allowedPerms = PermissionUtils.getUserAllowedPerms(userSocialId, userSocial.getAllowedPermissions(), verifier);
            value = userSocial.toHashMap();
            value.put("allowedPermissions", allowedPerms);
            value.put("token", PasswordUtils.generateAuthToken(userSocial.getId().toString(), userSocial.getEmail(), verifier));
            value.put("token_keep_login", KeepLoginUtils.generateKeepLoginToken(userSocial.getId().toString(), userSocial.getEmail(), String.join(",", allowedPerms), verifier));
            return value;
        }
        else {
            List<User> users = userDao.getListObject("auth_user_impl", "email", email);
            if (!users.isEmpty()) {
                User user = users.get(0);
                ArrayList<String> allowedPerms = PermissionUtils.getUserAllowedPerms(user.getId(), user.getAllowedPermissions(), verifier);
                User userSocial = UserFactory.createUser("auth.management.social.UserImpl", user, socialId);   
                userDao.saveObject(userSocial);
                value = userSocial.toHashMap();
                value.put("allowedPermissions", allowedPerms);
                value.put("token", PasswordUtils.generateAuthToken(userSocial.getId().toString(), userSocial.getEmail(), verifier));
                value.put("token_keep_login", KeepLoginUtils.generateKeepLoginToken(userSocial.getId().toString(), userSocial.getEmail(), String.join(",", allowedPerms), verifier));
                return value;
            }
            UserDecorator userSocial = (UserDecorator) createUser(vmjExchange);
            userDao.saveObject(userSocial);
            User user = userSocial.getUser();
            List<Role> roles = roleDao.getListObject("auth_role_impl", "name", "Registered");
            if (roles.size() > 0) {
                Role role = roles.get(0);
                UserRoleImpl userRole = UserRoleFactory.createUserRole("auth.management.core.UserRoleImpl", (RoleComponent) role, (UserComponent) user);
                userRoleDao.saveObject(userRole);
            }
            ArrayList<String> allowedPerms = PermissionUtils.getUserAllowedPerms(user.getId(), userSocial.getAllowedPermissions(), verifier);
            value = userSocial.toHashMap();
            value.put("allowedPermissions", allowedPerms);
            value.put("token", PasswordUtils.generateAuthToken(userSocial.getId().toString(), userSocial.getEmail(), verifier));
            value.put("token_keep_login", KeepLoginUtils.generateKeepLoginToken(userSocial.getId().toString(), userSocial.getEmail(), String.join(",", allowedPerms), verifier));
            return value;
        }

    }

    public User createUser(VMJExchange vmjExchange) {
        Object socialId = vmjExchange.getRequestBodyForm("social_id");
        User user = record.createUser(vmjExchange);
        User userSocial = UserFactory.createUser("auth.management.social.UserImpl", user, socialId);
        return userSocial;
    }

    @Restricted(permissionName = "administrator")
    @Route(url="call/user/delete")
    public List<HashMap<String,Object>> deleteUser(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID idUser = UUID.fromString(idStr);
        User user = userDao.getObject(idUser);
        if (user.getEmail().equals(vmjExchange.getAuthPayload().getEmail())){
            return record.getAllUser(vmjExchange);
        }
        else {
            List<User> userSocialList = userDao.getListObject("auth_user_social", "user_id", idUser);
    
            List<UserRoleImpl> userRoles = userRoleDao.getListObject("auth_user_role_impl", "authuser", idUser);
            for (UserRoleImpl userRole : userRoles) {
                userRoleDao.deleteObject(userRole.getId());
            }
    
            if (userSocialList.size() > 0){
                UserDecorator userSocial = (UserDecorator) userSocialList.get(0);
                UUID idUserSocial = userSocial.getId();
                userDao.deleteObject(idUserSocial);
            }
            return record.deleteUser(vmjExchange);
        }
    }
}
