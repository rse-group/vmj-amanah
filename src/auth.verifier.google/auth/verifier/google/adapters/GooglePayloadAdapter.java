/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.verifier.google.adapters;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.util.List;

import vmj.auth.core.TokenPayload;

/**
 *
 * @author Ichlasul Affan
 */
public class GooglePayloadAdapter implements TokenPayload {
    final private GoogleIdToken.Payload obj;

    public GooglePayloadAdapter(GoogleIdToken.Payload obj) {
        this.obj = obj;
    }

    @Override
    public String getEmail() {
        return obj.getEmail();
    }

    @Override
    public List<String> getAudiences() {
        return obj.getAudienceAsList();
    }

    @Override
    public String getIssuer() {
        return obj.getIssuer();
    }
}
