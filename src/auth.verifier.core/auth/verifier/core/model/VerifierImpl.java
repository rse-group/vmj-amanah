package auth.verifier.core;

import java.util.Arrays;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import auth.token.TokenFactory;
import auth.token.core.Token;
import auth.verifier.core.VerifierComponent;
import auth.verifier.core.VerifierDecorator;
import auth.verifier.core.adapters.JWTPayloadAdapter;

import vmj.auth.core.AuthPayload;
import vmj.auth.core.TokenPayload;
import vmj.auth.utils.JWTUtils;

public class VerifierImpl extends VerifierComponent {
	
	public VerifierImpl() {
        this.name = "";
	}
	
	@Override
	public AuthPayload verifyAndParse(final String token) {
        try {
        	Token tokenUtil = TokenFactory.createToken();
            DecodedJWT jwt = tokenUtil.decodeJWT(token.trim());
            TokenPayload payload = null;
            if (jwt != null) {
                payload = new JWTPayloadAdapter(jwt);
                if (!payload.getIssuer().equals(tokenUtil.getIssuer())) {
                    throw new IllegalArgumentException("Issuer mismatch");
                }
            } else {
                throw new IllegalArgumentException("Invalid ID token.");
            }
            return payload;
        } catch (IllegalArgumentException e) {
            System.out.println("Token problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

	@Override
    public boolean isValid(final String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String issuer = decodedJWT.getIssuer();
            boolean isSameIssuer = issuer.equals(JWTUtils.getIssuer());
            return isSameIssuer;
        } catch (JWTDecodeException e) {
            return false;
        }
    }
}
