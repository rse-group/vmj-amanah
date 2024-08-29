/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.verifier.manual;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import auth.token.TokenFactory;
import auth.token.core.Token;
import auth.verifier.core.VerifierComponent;
import auth.verifier.core.VerifierDecorator;
import auth.verifier.manual.adapters.JWTPayloadAdapter;

import vmj.auth.core.AuthPayload;
import vmj.auth.core.TokenPayload;
import vmj.auth.utils.JWTUtils;

/**
 *
 * @author ichla
 */
public class VerifierImpl extends VerifierDecorator {

    public VerifierImpl(VerifierComponent verifier) {
		super(verifier);
        this.name = "manual";
	}

    @Override
    public AuthPayload verifyAndParse(final String token) {
        return verifier.verifyAndParse(token);
    }

    @Override
    public boolean isValid(final String token) {
		try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String verifierName = decodedJWT.getClaim("verifier").asString();
            boolean isVerifierNull = verifierName == null;
            if (!isVerifierNull)
                return verifierName.trim().isEmpty() || verifierName.equals(this.name);
            return true;
        } catch (JWTDecodeException e) {
            return false;
        }
    }
}
