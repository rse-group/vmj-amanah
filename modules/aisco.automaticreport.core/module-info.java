module aisco.automaticreport.core {
    exports aisco.automaticreport.core;
    exports aisco.automaticreport;

    requires aisco.financialreport.core;
    requires aisco.chartofaccount.core;
    requires java.logging;
    requires vmj.routing.route;
    requires vmj.hibernate.integrator;
}