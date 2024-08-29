package auth.token.core;

import java.util.Map;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenImpl extends TokenComponent {
	@Override
    public Algorithm getSigningAlgorithm() {
    	return Algorithm.none();
    }
    
    @Override
    public Algorithm getValidationAlgorithm() {
    	return Algorithm.none();
    }
}
