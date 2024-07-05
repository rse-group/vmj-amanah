module aisco.product.blog {
    requires vmj.auth.model;
    requires vmj.routing.route;
    requires vmj.hibernate.integrator;
    
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires com.fasterxml.classmate;
    requires jdk.unsupported;

    requires aisco.program.core;
    requires aisco.program.activity;
    requires aisco.chartofaccount.core;
    requires aisco.financialreport.core;
    requires aisco.financialreport.income;
    requires aisco.beneficiary.core;
    requires aisco.beneficiary.pesertadidik;
}