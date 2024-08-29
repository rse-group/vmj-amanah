module auth.verifier.altgoogle {
	requires auth.verifier.core;
	exports auth.verifier.altgoogle;

	requires google.api;
	requires google.oauth;
	requires google.http;
	requires google.http.gson;
	requires vmj.auth;
	requires auth0.jwt;
}