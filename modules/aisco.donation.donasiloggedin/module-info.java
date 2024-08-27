module aisco.donation.donasiloggedin {
	requires aisco.donation.core;
	requires aisco.program.core;
    requires aisco.financialreport.core;
    exports aisco.donation.donasiloggedin;

	requires vmj.routing.route;
	requires vmj.hibernate.integrator;
	requires vmj.auth;
	requires vmj.auth.model;
	requires java.logging;
	// https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
	requires java.naming;

	opens aisco.donation.donasiloggedin to org.hibernate.orm.core, gson;
}
