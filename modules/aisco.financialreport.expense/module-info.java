module aisco.financialreport.expense {
    requires aisco.financialreport.core;
    exports aisco.financialreport.expense;
    requires vmj.routing.route;
    requires vmj.hibernate.integrator;
    requires vmj.auth;
}
