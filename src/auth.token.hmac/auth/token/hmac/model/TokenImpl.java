package auth.token.hmac;

import com.auth0.jwt.algorithms.Algorithm;
import auth.token.core.TokenComponent;
import auth.token.core.TokenDecorator;

import vmj.auth.core.Constants;
import vmj.auth.utils.PropertiesReader;

public class TokenImpl extends TokenDecorator {
	
	public TokenImpl(TokenComponent token) {
		super(token);
	}
	
	public TokenImpl() {
		super();
	}
	
	@Override
	public Algorithm getSigningAlgorithm() {
    	return Algorithm.HMAC256(getSecretKey());
    }
	
	@Override
	public Algorithm getValidationAlgorithm() {
    	return Algorithm.HMAC256(getSecretKey());
    }
	
	private String getSecretKey() {
		return (new PropertiesReader()).getPropOrDefault(
	            Constants.MANUAL_JWT_SECRET_KEY_PROP, Constants.MANUAL_JWT_SECRET_KEY_DEFAULT);
    }
}
