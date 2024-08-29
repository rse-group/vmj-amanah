module auth.token.core {
	exports auth.token;
	exports auth.token.core;
	exports auth.token.utils;
	
	requires auth0.jwt;
	requires java.logging;
	requires vmj.auth;
	requires vmj.routing.route;
}