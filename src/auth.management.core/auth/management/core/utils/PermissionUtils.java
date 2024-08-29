package auth.management.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import auth.management.UserRoleFactory;
import auth.management.core.RoleImpl;
import auth.management.core.UserImpl;
import auth.management.core.UserRoleImpl;
import org.hibernate.Session;
import org.hibernate.query.Query;
import vmj.hibernate.integrator.HibernateUtil;
import vmj.hibernate.integrator.RepositoryUtil;

public class PermissionUtils {

    public static ArrayList<String> splitPermissions(String permissions) {
        if(permissions == null || permissions.isEmpty()){
            return new ArrayList<String>();
        }
        List<String> tempPerms = Arrays.asList(permissions.split(","));
        return new ArrayList<String>(tempPerms);
    }

    public static void syncUserRoles(UUID userId, String verifier) {
        String roleTableName = "auth.management.core.RoleImpl";
        String userRoleTableName = "auth.management.core.UserRoleImpl";
        String defaultVerifierTableName = "auth.management.defaultverifier.RoleImpl";

        RepositoryUtil<UserRoleImpl> userRoleDao = new RepositoryUtil<UserRoleImpl>(auth.management.core.UserRoleImpl.class);
        try (Session session = (HibernateUtil.getSessionFactory()).openSession()) {
            String queryStr = "SELECT ur.id " + 
                "FROM " + roleTableName + " AS r, " + userRoleTableName + " AS ur, " + defaultVerifierTableName + " AS rv " + 
                "WHERE r.id = rv.role.id AND r.id = ur.role.id AND ur.user.id='" + userId + "'";
            Query query = session.createQuery(queryStr);
            List<UUID> userRoleIds = query.list();
            for (UUID userRole : userRoleIds) {
                userRoleDao.deleteObject(userRole);
            }

            queryStr = "SELECT r.id " + 
            "FROM " + roleTableName + " AS r, " + defaultVerifierTableName + " AS rv " + 
            "WHERE rv.verifier ='" + verifier + "' AND r.id = rv.role.id";
            query = session.createQuery(queryStr);
            List<UUID> roleIds = query.list();
            
            RepositoryUtil<UserImpl> userDao = new RepositoryUtil<UserImpl>(auth.management.core.UserImpl.class);
            RepositoryUtil<RoleImpl> roleDao = new RepositoryUtil<RoleImpl>(auth.management.core.RoleImpl.class);
            UserImpl user = userDao.getObject(userId);
            for (UUID roleId : roleIds) {
                RoleImpl role = roleDao.getObject(roleId);
                UserRoleImpl userRole = UserRoleFactory.createUserRole("auth.management.core.UserRoleImpl", role, user);
                userRoleDao.saveObject(userRole);
            }
        } catch (Exception e) { }
    }

    public static ArrayList<String> getUserAllowedPerms(UUID userId, String permissions) {
        return getUserAllowedPerms(userId, permissions, null);
    }

    public static ArrayList<String> getUserAllowedPerms(UUID userId, String permissions, String verifier) {
        if (verifier != null) {
            syncUserRoles(userId, verifier);
        }
        ArrayList<String> allowedPerms = splitPermissions(permissions);
        RepositoryUtil<UserRoleImpl> userRoleDao = new RepositoryUtil<UserRoleImpl>(auth.management.core.UserRoleImpl.class);
        List<UserRoleImpl> userRoles = userRoleDao.getListObject("auth_user_role_impl", "authuser", userId);
        for (UserRoleImpl userRole : userRoles) {
            if (!userRole.role.getName().startsWith("#")) {
                ArrayList<String> rolePermissions = splitPermissions(userRole.role.getAllowedPermissions());
                for (String rolePermission : rolePermissions) {
                    if (!allowedPerms.contains(rolePermission)){
                        allowedPerms.add(rolePermission);
                    }
                }
            }
        }
        return allowedPerms;
    }

    public static Map<String, String> getResourceAccess(String email) {
        String userTableName ="auth.management.core.UserImpl";
        String roleTableName = "auth.management.core.RoleImpl";
        String userRoleTableName = "auth.management.core.UserRoleImpl";
        String multipleResourceTableName = "auth.management.multipleresource.RoleImpl";
        Map<String, String> result = new HashMap<>();

        try (Session session = (HibernateUtil.getSessionFactory()).openSession()) {
            String queryStr = "select new map (rr.resource as resource, r.allowedPermissions as allowedPermissions) " +
                "FROM " + userTableName + " AS u, " + roleTableName + " AS r, " + userRoleTableName + " AS ur, " + multipleResourceTableName + " AS rr " +
                "WHERE u.email='" + email + "' AND u.id = ur.user.id AND r.id = ur.role.id AND r.id = rr.role.id";
            Query query = session.createQuery(queryStr);
            List<Map<String, Object>> roles = query.list();
            
            for (Map<String, Object> role : roles) {
            	String resourceAccess = "permissions." + (String) role.get("resource");
            	String allowedPermission = (String) role.get("allowedPermissions");
            	if (result.containsKey(resourceAccess)) {
            		allowedPermission = String.join(",", (String) result.get("resource"), allowedPermission);
            	}
            	result.put(resourceAccess, allowedPermission);
            }
        } catch (IllegalArgumentException e) {
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return result;
    }
}
