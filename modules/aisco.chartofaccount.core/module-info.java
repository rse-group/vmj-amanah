module aisco.chartofaccount.core {
    exports aisco.chartofaccount.core;
    exports aisco.chartofaccount;
    requires vmj.routing.route;
    requires java.logging;
    requires prices.auth.vmj;
    opens aisco.chartofaccount.core to org.hibernate.orm.core;
    requires vmj.hibernate.integrator;
}