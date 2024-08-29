module auth.management.social {
	requires auth.management.core;
	exports auth.management.social;
	
	requires java.persistence;
	requires vmj.hibernate.integrator;
	requires vmj.routing.route;
	requires auth0.jwt;
	requires vmj.auth;
}