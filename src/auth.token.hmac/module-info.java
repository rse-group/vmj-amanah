module auth.token.hmac {
	requires auth.token.core;
	exports auth.token.hmac;
	
	requires auth0.jwt;
	requires vmj.auth;
}