package auth.token.core;

import java.util.Date;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import vmj.auth.core.Constants;
import vmj.auth.utils.PropertiesReader;

public abstract class TokenComponent implements Token {
	
	@Override
	public String createJWT(String userId, String email, Map<String, String> claims) {
		Algorithm algorithm = getSigningAlgorithm();
        long nowMillis = System.currentTimeMillis();
		long ttlMillis = this.getTTL();
        Date now = new Date(nowMillis);
        String jwtString = null;
        try {
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(getIssuer())
                    .withIssuedAt(now)
                    .withSubject(userId + "::" + email);
            for (var entry : claims.entrySet()) {
                builder = builder.withClaim(entry.getKey(), entry.getValue());
            }
            if (ttlMillis > 0) {
                long expMillis = nowMillis + ttlMillis;
                Date exp = new Date(expMillis);
                builder.withExpiresAt(exp);
            }
            jwtString = builder.sign(algorithm);
        } catch (JWTCreationException e){
            System.out.println("Token problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return jwtString;
	}
	
	@Override
	public DecodedJWT decodeJWT(String token) {
        Algorithm algorithm = getValidationAlgorithm();
		DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(getIssuer())
                    .build();
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e){
            System.out.println("Token problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return jwt;
	}
	
    @Override
	public String getIssuer() {
        return (new PropertiesReader()).getPropOrDefault(
            Constants.MANUAL_JWT_ISSUER_PROP, Constants.MANUAL_JWT_ISSUER_DEFAULT);
    }

    @Override
    public long getTTL() {
        return Long.parseLong((new PropertiesReader()).getPropOrDefault(
            Constants.MANUAL_JWT_TTL_PROP, Constants.MANUAL_JWT_TTL_DEFAULT));
    }
	
    public abstract Algorithm getSigningAlgorithm();
	
    public abstract Algorithm getValidationAlgorithm();
}
