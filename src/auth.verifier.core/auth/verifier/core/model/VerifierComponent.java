package auth.verifier.core;

import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import vmj.auth.core.AuthPayload;

public abstract class VerifierComponent implements Verifier {

	public String name;

	public String getName() {
		return this.name;
	}
	
	public abstract AuthPayload verifyAndParse(final String token);

	public abstract boolean isValid(final String token);
}
