module auth.verifier.core {
	exports auth.verifier;
	exports auth.verifier.core;
	
	requires auth.token.core;
	requires java.logging;
	requires auth0.jwt;
	requires vmj.auth;
}