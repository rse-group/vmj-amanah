module aisco.program.core {
    exports aisco.program.core;
    exports aisco.program;
    requires java.logging;
    requires vmj.routing.route;
    requires vmj.auth;
    requires vmj.hibernate.integrator;
//    requires aisco.financialreport.core;

    opens aisco.program.core to org.hibernate.orm.core, gson;
}
