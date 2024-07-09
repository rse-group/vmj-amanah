module aisco.beneficiary.rumahsinggah {
	requires aisco.beneficiary.core;
	exports aisco.beneficiary.rumahsinggah;

	requires aisco.program.core;
	requires vmj.routing.route;
	requires vmj.hibernate.integrator;
	
	requires java.logging;
	// https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
	requires java.naming;

	opens aisco.beneficiary.rumahsinggah to org.hibernate.orm.core, gson;
}