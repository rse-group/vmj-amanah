package aisco.donation.core;

import java.util.*;

import aisco.financialreport.core.FinancialReport;

import vmj.routing.route.VMJExchange;

public interface DonationResource {
    List<HashMap<String,Object>> saveDonation(VMJExchange vmjExchange);
    HashMap<String, Object> updateDonation(VMJExchange vmjExchange);
    HashMap<String, Object> getDonation(VMJExchange vmjExchange);
    Donation createDonation(VMJExchange vmjExchange);
    Donation createDonation(VMJExchange vmjExchange, int id);
    List<HashMap<String,Object>> transformDonationListToHashMap(List<Donation> donationList);
    List<HashMap<String,Object>> getAllDonation(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteDonation(VMJExchange vmjExchange);
    FinancialReport createIncome(VMJExchange vmjExchange);
}
