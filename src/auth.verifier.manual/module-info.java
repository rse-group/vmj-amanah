module auth.verifier.manual {
	requires auth.token.core;
	requires auth.verifier.core;
	exports auth.verifier.manual;
	
	requires auth0.jwt;
	requires vmj.auth;
}