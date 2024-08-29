/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.verifier.core.adapters;

import java.util.List;

import com.auth0.jwt.interfaces.DecodedJWT;

import vmj.auth.core.TokenPayload;

/**
 *
 * @author ichla
 */
public class JWTPayloadAdapter implements TokenPayload {

    final private DecodedJWT obj;

    public JWTPayloadAdapter(DecodedJWT obj) {
        this.obj = obj;
    }

    @Override
    public String getEmail() {
        return obj.getClaim("email").asString();
    }

    @Override
    public List<String> getAudiences() {
        return obj.getAudience();
    }

    @Override
    public String getIssuer() {
        return obj.getIssuer();
    }

}
