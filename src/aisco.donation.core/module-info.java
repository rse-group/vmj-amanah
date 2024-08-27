module aisco.donation.core {
    exports aisco.donation;
    exports aisco.donation.core;
	
	requires aisco.program.core;
	requires aisco.financialreport.core;
    
    requires vmj.routing.route;
    // https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
    requires java.naming;
    requires vmj.hibernate.integrator;
    requires java.logging;
    
    requires vmj.auth;
    requires vmj.auth.model;
    
    opens aisco.donation.core to org.hibernate.orm.core, gson;
}
