module aisco.automaticreport.twolevel {
    exports aisco.automaticreport.twolevel;
    requires java.logging;
    requires vmj.routing.route;
    requires aisco.automaticreport.journalentry;

    requires aisco.automaticreport.core;

    requires vmj.hibernate.integrator;
}