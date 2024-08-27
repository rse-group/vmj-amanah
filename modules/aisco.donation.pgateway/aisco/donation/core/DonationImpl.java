package aisco.donation.pgateway;

import aisco.donation.core.DonationComponent;

import payment.method.PGTransactionFactory;
import payment.method.core.PGTransaction;
import payment.method.dokudua.PGTransactionImpl;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import java.util.*;


import vmj.auth.annotations.Restricted;
import vmj.auth.annotations.RestrictCRUD;
import vmj.auth.enums.CRUDMethod;

import paymentgateway.product.flip;

@RestrictCRUD(permissionName = "ModifyDonationImpl", allowedMethods = {CRUDMethod.POST, CRUDMethod.PUT, CRUDMethod.DELETE})
public class DonationImpl extends DonationComponent {
    PGTransaction pgTransactionDoku;

    public DonationImpl() {
        System.out.println("\nDonation via Payment Gateway");
        pgTransactionDoku = PGTransactionFactory.createPGTransaction("payment.method.dokudua.PGTransactionImpl",
                PGTransactionFactory.createPGTransaction("payment.method.core.PGTransactionImpl"));
    }

    public void getDonation() {
        System.out.println("get donation");
        // payment.getTransaction();
    }

    public void addDonation() {
        System.out.println("add donation");
        // payment.addTransaction();
    }

    @Route(url = "aisco-pgtransaction/set-status")
    @Restricted(permissionName = "ModifyDonationImpl")
    public HashMap<String, Object> setTransactionStatus(VMJExchange vmjExchange) {
        return pgTransactionDoku.setTransactionstatus(vmjExchange);
    }

    @Route(url = "aisco-pgtransaction/doku/kirimdoku/ping")
    public HashMap<String, Object> pingKirimDoku(VMJExchange vmjExchange) throws Exception {
        PGTransactionImpl pgDoku = (PGTransactionImpl) pgTransactionDoku;
        return pgDoku.pingKirimDoku(vmjExchange);
    }

    @Route(url = "aisco-pgtransaction/doku/kirimdoku/inquiry-wallet")
    @Restricted(permissionName = "ModifyDonationImpl")
    public HashMap<String, Object> inquiryKirimDoku(VMJExchange vmjExchange) throws Exception {
        PGTransactionImpl pgDoku = (PGTransactionImpl) pgTransactionDoku;
        return pgDoku.inquiryKirimDoku(vmjExchange);
    }

}
