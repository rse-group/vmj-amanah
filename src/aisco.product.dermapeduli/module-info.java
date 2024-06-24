module aisco.product.dermapeduli {
    requires vmj.auth.model;
    requires vmj.routing.route;
    requires vmj.hibernate.integrator;
    
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires com.fasterxml.classmate;
    requires jdk.unsupported;

    requires paymentgateway.config.core;
    requires paymentgateway.config.flip;
    requires paymentgateway.config.midtrans;
    requires paymentgateway.config.oy;
    requires paymentgateway.payment.core;
    requires paymentgateway.payment.creditcard;
    requires paymentgateway.payment.debitcard;
    requires paymentgateway.payment.ewallet;
    requires paymentgateway.payment.invoice;
    requires paymentgateway.payment.paymentlink;
    requires paymentgateway.payment.paymentrouting;
    requires paymentgateway.payment.retailoutlet;
    requires paymentgateway.payment.virtualaccount;
    requires aisco.program.core;
    requires aisco.program.activity;
    requires aisco.program.operational;
    requires aisco.program.goodsprogram;
    requires aisco.chartofaccount.core;
    requires aisco.financialreport.core;
    requires aisco.financialreport.income;
    requires aisco.financialreport.expense;
    requires aisco.automaticreport.core;
    requires aisco.automaticreport.twolevel;
    requires aisco.automaticreport.periodic;
    requires aisco.donation.core;
    requires aisco.donation.confirmation;
    requires aisco.donation.viapaymentgateway;
    requires aisco.donation.goodsdonation;
}