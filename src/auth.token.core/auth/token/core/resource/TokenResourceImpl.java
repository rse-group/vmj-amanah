package auth.token.core;

import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.interfaces.DecodedJWT;
import auth.token.TokenFactory;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public class TokenResourceImpl extends TokenResourceComponent {
    @Route(url="call/token/verify")
    public HashMap<String,Object> verifyToken(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String token = (String)vmjExchange.getRequestBodyForm("token");
        Map<String, String> claims = (Map<String, String>)vmjExchange.getRequestBodyForm("claims");
        
        HashMap<String,Object> result = new HashMap<String,Object>();
        result.put("is_valid", isTokenValid(token, claims));
        return result;
    }
    
    public boolean isTokenValid(String token, Map<String, String> claims) {
        DecodedJWT decodedJWT = TokenFactory.createToken().decodeJWT(token);
        if (decodedJWT == null) {
            return false;
        }
        for (String claim_key : claims.keySet()) {
            String claim = decodedJWT.getClaim(claim_key).asString();
            if (claim == null || !claim.equals(claims.get(claim_key))) {
                return false;
            }
        }
        return true;
    }
}