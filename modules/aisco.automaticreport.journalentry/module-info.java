module aisco.automaticreport.journalentry {
    exports aisco.automaticreport.journalentry;
    requires java.logging;
    requires vmj.routing.route;
    requires prices.auth.vmj;

    requires transitive aisco.automaticreport.core;
    requires aisco.financialreport.core;

    requires vmj.hibernate.integrator;

    opens aisco.automaticreport.journalentry to org.hibernate.orm.core, gson;
}