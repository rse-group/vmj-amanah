/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.verifier.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import auth.verifier.core.VerifierComponent;
import auth.verifier.core.VerifierDecorator;
import auth.verifier.google.adapters.GooglePayloadAdapter;

import vmj.auth.core.AuthPayload;
import vmj.auth.core.TokenPayload;
import vmj.auth.utils.PropertiesReader;

/**
 *
 * @author Afifun
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
            NetHttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = new GsonFactory();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(clientId)).build();

            GoogleIdToken idToken = verifier.verify(token.trim());
            TokenPayload payload = null;

            if (idToken != null) {
                payload = new GooglePayloadAdapter(idToken.getPayload());
                if (!payload.getAudiences().contains(clientId)) {
                    throw new IllegalArgumentException("Client ID mismatch");
                }
            } else {
                throw new IllegalArgumentException("Invalid ID token.");
            }
            return payload;
        } catch (GeneralSecurityException e) {
            System.out.println("Security issue: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Network problem: " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Token problem: " + e.getLocalizedMessage());
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
