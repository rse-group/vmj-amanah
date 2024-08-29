/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.verifier.altgoogle.adapters;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import vmj.auth.core.TokenPayload;

/**
 *
 * @author Ichlasul Affan
 */
public class GoogleRawPayloadAdapter implements TokenPayload {
    final private Map<String, Object> obj;

    public GoogleRawPayloadAdapter(Map<String, Object> obj) {
        this.obj = obj;
    }

    @Override
    public String getEmail() {
        return (String) obj.getOrDefault("email", null);
    }

    @Override
    public List<String> getAudiences() {
        List<String> result = new ArrayList<>();
        result.add((String) obj.getOrDefault("aud", null));
        return result;
    }

    @Override
    public String getIssuer() {
        return (String) obj.getOrDefault("iss", null);
    }
}
