module aisco.automaticreport.periodic {
    exports aisco.automaticreport.periodic;
    requires java.logging;
    requires vmj.routing.route;

    requires transitive aisco.automaticreport.core;
    requires aisco.financialreport.core;

    requires vmj.hibernate.integrator;
}