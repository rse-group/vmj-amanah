module aisco.beneficiary.rumahbelajar {
	requires aisco.beneficiary.core;
	exports aisco.beneficiary.rumahbelajar;

	requires aisco.program.core;
	requires vmj.routing.route;
	requires vmj.hibernate.integrator;
//	requires vmj.auth;
//	requires vmj.auth.model;
	requires java.logging;
	// https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
	requires java.naming;

	opens aisco.beneficiary.rumahbelajar to org.hibernate.orm.core, gson;
}
