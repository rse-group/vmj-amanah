module aisco.automaticreport.activityreport {
    exports aisco.automaticreport.activityreport;
    requires java.logging;
    requires vmj.routing.route;
    requires prices.auth.vmj;

    requires transitive aisco.automaticreport.core;
    requires aisco.financialreport.core;
    requires aisco.automaticreport.periodic;

    requires vmj.hibernate.integrator;

    opens aisco.automaticreport.activityreport to org.hibernate.orm.core, gson;
}