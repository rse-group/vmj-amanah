module aisco.withdraw.viaspecialdisbursement {
	requires aisco.withdraw.core;
    exports aisco.withdraw.viaspecialdisbursement;
	
	requires aisco.program.core;
	requires aisco.financialreport.core;
	

    requires paymentgateway.disbursement.core;
    requires paymentgateway.disbursement.specialdisbursement;

    
    requires vmj.routing.route;
    // https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
    requires java.naming;
    requires vmj.hibernate.integrator;
    requires java.logging;
    
    requires vmj.auth;
    requires vmj.auth.model;
    
 // https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
 	requires java.net.http;
 	requires com.fasterxml.jackson.core;
 	requires com.fasterxml.jackson.databind;
 	requires google.http.gson;
 	requires gson;
    
    opens aisco.withdraw.viaspecialdisbursement to org.hibernate.orm.core, gson;
}
