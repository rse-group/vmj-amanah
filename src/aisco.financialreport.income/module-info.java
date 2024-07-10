module aisco.financialreport.income {
    requires aisco.financialreport.core;
    exports aisco.financialreport.income;
    requires vmj.routing.route;
    requires vmj.hibernate.integrator;
    requires vmj.auth;

    opens aisco.financialreport.income to gson;
}
