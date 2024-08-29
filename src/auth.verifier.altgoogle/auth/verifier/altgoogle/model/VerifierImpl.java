/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.verifier.altgoogle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.security.GeneralSecurityException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import auth.verifier.core.VerifierComponent;
import auth.verifier.core.VerifierDecorator;
import auth.verifier.altgoogle.adapters.GoogleRawPayloadAdapter;

import vmj.auth.core.AuthPayload;
import vmj.auth.core.TokenPayload;
import vmj.auth.utils.ConnectionUtils;
import vmj.auth.utils.PropertiesReader;

/**
 *
 * @author Ichlasul Affan
 */
public class VerifierImpl extends VerifierDecorator {

    public VerifierImpl(VerifierComponent verifier) {
		super(verifier);
        this.name = "google";
	}

    /**
     * @param clientId
     * @param token
     * @return
     */
    @Override
    public AuthPayload verifyAndParse(final String token) {
        final String clientId = (new PropertiesReader()).getClientId(this.name);
        try {
            Map<String, Object> rawPayload = ConnectionUtils.getJsonLessSecure(
                    "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + token.trim());
            TokenPayload payload = new GoogleRawPayloadAdapter(rawPayload);
            if (payload.getEmail() == null) {
                throw new GeneralSecurityException("Invalid ID token");
            } else if (!payload.getIssuer().endsWith("accounts.google.com")) {
                throw new GeneralSecurityException("Token not issued from accounts.google.com");
            } else if (!payload.getAudiences().contains(clientId)) {
                throw new IllegalArgumentException("Client ID mismatch");
            }
            return payload;
        } catch (GeneralSecurityException e) {
            System.out.println("Security issue: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Network problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Token problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Network problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

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
            return true;
        }
    }
}
