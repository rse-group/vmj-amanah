module aisco.program.core {
    exports aisco.program.core;
    exports aisco.program;
    requires java.logging;
    requires vmj.routing.route;
    requires prices.auth.vmj;
    requires vmj.hibernate.integrator;

    opens aisco.program.core to org.hibernate.orm.core, gson;
}
