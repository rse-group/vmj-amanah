module aisco.donation.viapaymentgateway {
	requires aisco.donation.core;
    exports aisco.donation.viapaymentgateway;
    
    requires aisco.financialreport.core;
    
    requires aisco.chartofaccount.core;
    
    requires aisco.program.core;

	requires vmj.routing.route;
	requires vmj.hibernate.integrator;
	requires vmj.auth;
	requires java.logging;
	// https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
	requires java.naming;
	requires java.net.http;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires google.http.gson;
	requires gson;

	opens aisco.donation.viapaymentgateway to org.hibernate.orm.core, gson;
}
