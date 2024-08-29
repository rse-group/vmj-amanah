module auth.verifier.google {
	requires auth.verifier.core;
	exports auth.verifier.google;

	requires google.api;
	requires google.oauth;
	requires google.http;
	requires google.http.gson;
	requires vmj.auth;
	requires auth0.jwt;
}