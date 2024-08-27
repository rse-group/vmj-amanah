module aisco.donation.goodsdonation {
	requires aisco.donation.core;
	requires aisco.program.core;
	requires aisco.program.goodsprogram;
    requires aisco.financialreport.core;
    exports aisco.donation.goodsdonation;

    requires java.naming;
	requires vmj.routing.route;
	requires vmj.hibernate.integrator;
	requires vmj.auth;
	requires vmj.auth.model;
	requires java.logging;
	// https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
	
	opens aisco.donation.goodsdonation to org.hibernate.orm.core, gson;
}
