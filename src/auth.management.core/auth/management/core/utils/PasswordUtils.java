package auth.management.utils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import vmj.auth.core.Constants;
import vmj.auth.utils.EncryptionUtils;
import vmj.auth.utils.PropertiesReader;
import auth.token.TokenFactory;
import auth.token.core.Token;

public class PasswordUtils {
    private static final String FORGOT_SEPARATOR = "---";

    public static String generateAuthToken(String userId, String email, String verifier) {
    	Token token = TokenFactory.createToken();
    	Map<String, String> claims = new HashMap<String, String>();
        claims.put("email", email);
        claims.put("verifier", verifier);
    	return token.createJWT(userId, email, claims);
    }

    public static String hashPassword(String password) {
    	return EncryptionUtils.hashSHA3512(password, getSalt());
    }

    public static String generateForgotToken(String email) {
        long exp = Instant.now().toEpochMilli() + TokenFactory.createToken().getTTL();
        return EncryptionUtils.encryptAES(email + FORGOT_SEPARATOR + Long.toString(exp),
            getSalt());
    }

    public static String getEmailFromForgotToken(String token) {
        String payload = EncryptionUtils.decryptAES(token, getSalt());
        String[] splitted = payload.split(FORGOT_SEPARATOR);
        if (splitted.length == 2 && Instant.now().toEpochMilli() < Long.parseLong(splitted[1])) {
            return splitted[0];
        }
        return "";
    }

    private static String getSalt() {
        return (new PropertiesReader()).getPropOrDefault(
            Constants.MANUAL_SECRET_KEY_PROP, Constants.MANUAL_SECRET_KEY_DEFAULT);
    }
}
