module auth.management.classic {
	requires auth.management.core;
	exports auth.management.classic;
	
	requires java.persistence;
	requires vmj.hibernate.integrator;
	requires vmj.routing.route;
	requires vmj.auth;
}