module aisco.automaticreport.financialposition {
    exports aisco.automaticreport.financialposition;
    requires java.logging;
    requires vmj.routing.route;
    requires vmj.auth;

    requires transitive aisco.automaticreport.core;
    requires aisco.financialreport.core;
    requires aisco.automaticreport.periodic;

    requires vmj.hibernate.integrator;

    opens aisco.automaticreport.financialposition to org.hibernate.orm.core, gson;
}