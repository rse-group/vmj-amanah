module aisco.financialreport.core {
    exports aisco.financialreport.core;
    exports aisco.financialreport;
    requires vmj.routing.route;
    // https://stackoverflow.com/questions/46488346/error32-13-error-cannot-access-referenceable-class-file-for-javax-naming-re/50568217
    requires java.naming;
    requires vmj.hibernate.integrator;
    requires java.logging;
    requires transitive aisco.program.core;
    requires transitive aisco.program.activity;
    requires transitive aisco.chartofaccount.core;
    requires vmj.auth;

    opens aisco.financialreport.core to org.hibernate.orm.core, gson;
}
