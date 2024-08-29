package auth.storage.hibernate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;

import auth.storage.core.StorageComponent;
import auth.storage.core.StorageDecorator;

import vmj.auth.core.AuthPayload;
import vmj.hibernate.integrator.HibernateUtil;

public class StorageImpl extends StorageDecorator {
	
	public StorageImpl(StorageComponent storage) {
		super(storage);
    }
	
	public StorageImpl() {
        super();
    }

    private List<String> splitPermissions(String permissions) {
        if(permissions == null || permissions.isEmpty()){
            return Arrays.asList();
        }
        return Arrays.asList(permissions.split(","));
    }

    public Map<String,Object> getUserData(AuthPayload payload) {
        String email = payload.getEmail();
        Map<String,Object> user = new HashMap<>();

        try (Session session = (HibernateUtil.getSessionFactory()).openSession()) {
            String queryStr = "select new map(user.allowedPermissions as allowedPermissions) from auth.management.core.UserImpl user where user.email = '"+ email + "'";
            Query query = session.createQuery(queryStr).setMaxResults(1);
            List<Map<String,Object>> result = query.list();
            user = result.get(0);
            user.put("allowedPermissions", this.splitPermissions((String) user.get("allowedPermissions")));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return user;
    }

    public Map<String, List<String>> getRoles(AuthPayload payload) {
        Map<String, Object> userData = this.getUserData(payload);
        String userTableName ="auth.management.core.UserImpl";
        String roleTableName = "auth.management.core.RoleImpl";
        String userRoleTableName = "auth.management.core.UserRoleImpl";
        Map<String, List<String>> result = new HashMap<>();

        try (Session session = (HibernateUtil.getSessionFactory()).openSession()) {
            String queryStr = "select new map (role.name as roleName, role.allowedPermissions as roleAllowedPermissions) " +
                "FROM " + userTableName + " AS user, " + roleTableName + " AS role, " + userRoleTableName + " AS ur " +
                "WHERE user.email='" + payload.getEmail() + "' AND user.id = ur.user.id AND role.id = ur.role.id and not role.name like '#%'";
            Query query = session.createQuery(queryStr);
            List<Map<String, Object>> roles = query.list();
            
            for (Map<String, Object> role : roles) {
                if (((String) role.get("roleName")).equals("administrator")) {
                    result.put((String) role.get("roleName"), Arrays.asList(new String[]{"administrator"}));
                }
                result.put((String) role.get("roleName"), this.splitPermissions((String) role.get("roleAllowedPermissions")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return result;
    }
}