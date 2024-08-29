module aisco.donation.pgateway {
    requires aisco.donation.core;
    exports aisco.donation.pgateway;
    requires java.logging;
    requires vmj.routing.route;
<<<<<<< HEAD
//    requires payment.method.core;
//    requires payment.method.dokudua;
    requires paymentgateway.product.flip;
=======
    requires payment.method.core;
    requires payment.method.dokudua;
>>>>>>> arga/dev
    requires vmj.auth;
}
