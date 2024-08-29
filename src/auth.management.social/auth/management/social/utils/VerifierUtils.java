package auth.management.social.utils;

import java.util.ArrayList;
import java.util.List;

import vmj.auth.core.AuthPayload;
import vmj.auth.verifiers.Verifier;
import vmj.auth.verifiers.VerifierFactory;

public class VerifierUtils {
	public static String getVerifierFromToken(String token) {
		AuthPayload payload = null;
		for (Verifier verifier : VerifierFactory.createVerifiers()) {
			payload = verifier.verifyAndParse(token);
			if (payload != null) {
				String verifierName = verifier.getName();
				return verifierName;
			}
		}
		return "";
	}
}