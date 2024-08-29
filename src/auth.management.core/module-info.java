module auth.management.core {
	requires auth.token.core;
	exports auth.management;
	exports auth.management.core;
	exports auth.management.utils;
	
	requires java.logging;
	requires java.persistence;
	requires vmj.routing.route;
	requires vmj.hibernate.integrator;
	requires org.hibernate.orm.core;
	requires vmj.auth;
	requires vmj.auth.model;
	
	opens auth.management.core to org.hibernate.orm.core, gson;
}