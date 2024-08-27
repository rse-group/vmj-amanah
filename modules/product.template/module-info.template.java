module aisco.product.charityschool {
    requires vmj.object.mapper;
    requires vmj.routing.route;

    requires aisco.program.core;
    requires aisco.program.activity;
    requires aisco.program.operational;

    requires aisco.financialreport.core;
    requires aisco.financialreport.income;
    requires aisco.financialreport.expense;

    requires aisco.chartofaccount.core;

    requires aisco.dashboard.core;
    requires aisco.dashboard.expense;

    requires aisco.donation.core;
    requires aisco.donation.pgateway;

    requires blogging.product.standard;
    requires vmj.auth;
    requires vmj.auth.model;
    requires com.fasterxml.jackson.databind;
}
