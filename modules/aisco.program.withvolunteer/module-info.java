module aisco.program.withvolunteer {
    requires aisco.program.core;
    exports aisco.program.withvolunteer;
	requires volunteers.volunteer.core;
    requires vmj.routing.route;
    requires vmj.object.mapper;
    requires vmj.auth;
    requires vmj.hibernate.integrator;

    opens aisco.program.withvolunteer to gson, org.hibernate.orm.core;
}
