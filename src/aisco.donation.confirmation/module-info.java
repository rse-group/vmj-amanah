module aisco.donation.confirmation {
    requires aisco.donation.core;
    requires aisco.program.core;
    requires aisco.financialreport.core;
    exports aisco.donation.confirmation;

    // https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
    requires java.naming;
    requires vmj.hibernate.integrator;
    requires vmj.routing.route;
    requires vmj.auth;
    requires vmj.auth.model;
    
    opens aisco.donation.confirmation to org.hibernate.orm.core, gson;
}
