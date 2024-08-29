package auth.management.utils;

import java.util.HashMap;
import java.util.Map;

import auth.token.TokenFactory;
import auth.token.core.Token;

public class KeepLoginUtils {
    public static String generateKeepLoginToken(String userId, String email, String permissions, String verifier) {
    	Token token = TokenFactory.createToken();
        Map<String, String> claims = new HashMap<String, String>();
        claims.put("email", email);
        claims.put("verifier", verifier);
        claims.put("permissions", permissions);
        Map<String, String> resources = PermissionUtils.getResourceAccess(email);
        for (String item : resources.keySet()) {
            claims.put(item, resources.get(item));
        }
    	return token.createJWT(userId, email, claims);
    }
}