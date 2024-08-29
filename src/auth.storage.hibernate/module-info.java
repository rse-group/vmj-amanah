module auth.storage.hibernate {
	requires auth.storage.core;
	exports auth.storage.hibernate;
	
	requires vmj.hibernate.integrator;
	requires org.hibernate.orm.core;
	requires vmj.auth;
}