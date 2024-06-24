package aisco.donation.core;

import java.util.*;

import aisco.financialreport.core.FinancialReport;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class DonationResourceDecorator extends DonationResourceComponent {

    protected DonationResourceComponent record;

    public DonationResourceDecorator(DonationResourceComponent record) {
        this.record = record;
    }

    public List<HashMap<String,Object>> saveDonation(VMJExchange vmjExchange) {
        return record.saveDonation(vmjExchange);
    }

    public Donation createDonation(VMJExchange vmjExchange) {
        return record.createDonation(vmjExchange);
    }
    
    public Donation createDonation(VMJExchange vmjExchange, String objectName) {
    	return record.createDonation(vmjExchange, objectName);
    }

    public HashMap<String, Object> updateDonation(VMJExchange vmjExchange) {
        return record.updateDonation(vmjExchange);
    }

    public HashMap<String, Object> getDonation(VMJExchange vmjExchange) {
        return record.getDonation(vmjExchange);
    }

    public List<HashMap<String,Object>> getAllDonation(VMJExchange vmjExchange) {
        return record.getAllDonation(vmjExchange);
    }

     public List<HashMap<String,Object>> transformDonationListToHashMap(List<Donation> donationList) {
        return record.transformDonationListToHashMap(donationList);
     }

    public List<HashMap<String,Object>> deleteDonation(VMJExchange vmjExchange) {
        return record.deleteDonation(vmjExchange);
    }

    public FinancialReport createIncome(VMJExchange vmjExchange) {
        return record.createIncome(vmjExchange);
    }
}
