module aisco.product.yayasanarga {
    requires vmj.routing.route;
    requires vmj.hibernate.integrator;
    
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires com.fasterxml.classmate;
    requires jdk.unsupported;

    requires aisco.program.core;
    requires aisco.program.activity;
    requires aisco.program.operational;
    requires aisco.chartofaccount.core;
    requires aisco.financialreport.core;
    requires aisco.financialreport.income;
    requires aisco.financialreport.expense;
    requires aisco.automaticreport.core;
    requires aisco.donation.core;
    requires aisco.donation.confirmation;
    requires aisco.beneficiary.core;
    requires aisco.beneficiary.rumahsinggah;
	
	requires prices.auth.vmj;
    requires prices.auth.vmj.model;
}