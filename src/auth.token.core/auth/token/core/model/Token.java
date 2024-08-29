package auth.token.core;

import java.util.Map;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface Token {
	String createJWT(String userId, String email, Map<String, String> claims);
	DecodedJWT decodeJWT(String token);
	Algorithm getSigningAlgorithm();
	Algorithm getValidationAlgorithm();
	String getIssuer();
	long getTTL();
}
