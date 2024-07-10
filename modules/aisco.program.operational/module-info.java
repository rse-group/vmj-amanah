module aisco.program.operational {
    requires aisco.program.core;
    exports aisco.program.operational;
    requires vmj.routing.route;
    requires vmj.hibernate.integrator;
    requires vmj.auth;

    opens aisco.program.operational to gson;
}
