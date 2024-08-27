module aisco.program.activity {
    requires aisco.program.core;
    exports aisco.program.activity;
    requires vmj.routing.route;
    requires prices.auth.vmj;
    requires vmj.hibernate.integrator;

    opens aisco.program.activity to gson;
}
