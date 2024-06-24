module paymentgateway.payment.debitcard {
	requires paymentgateway.payment.core;
    exports paymentgateway.payment.debitcard;

	requires vmj.routing.route;
	requires paymentgateway.config.core;
	requires vmj.hibernate.integrator;
	requires vmj.auth;
	requires java.logging;
	// https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
	requires java.naming;
	requires java.net.http;
	
	opens paymentgateway.payment.debitcard to org.hibernate.orm.core, gson;
}