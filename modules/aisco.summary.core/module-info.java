module aisco.summary.core {
    exports aisco.summary.core;
    exports aisco.summary;

    requires aisco.financialreport.core;
    requires aisco.program.core;
    requires java.logging;
    requires vmj.routing.route;
    requires vmj.hibernate.integrator;
}