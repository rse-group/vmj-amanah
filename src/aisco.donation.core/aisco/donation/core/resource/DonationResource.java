package aisco.donation.core;

import java.util.*;

import aisco.financialreport.core.FinancialReport;

import vmj.routing.route.VMJExchange;

public interface DonationResource {
    List<HashMap<String,Object>> saveDonation(VMJExchange vmjExchange);
    HashMap<String, Object> updateDonation(VMJExchange vmjExchange);
    HashMap<String, Object> getDonation(VMJExchange vmjExchange);
    Donation createDonation(VMJExchange vmjExchange);
    Donation createDonation(VMJExchange vmjExchange, String objectName);
    Donation createDonation(VMJExchange vmjExchange, UUID id);
    Donation createDonation(VMJExchange vmjExchange, UUID id, String objectName);
    List<HashMap<String,Object>> transformDonationListToHashMap(List<Donation> donationList);
    List<HashMap<String,Object>> getAllDonation(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteDonation(VMJExchange vmjExchange);
    FinancialReport createIncome(VMJExchange vmjExchange);
}
