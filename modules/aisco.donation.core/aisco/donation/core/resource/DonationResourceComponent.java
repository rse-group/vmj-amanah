package aisco.donation.core;

import java.util.*;

import aisco.financialreport.core.*;

import vmj.hibernate.integrator.DaoUtil;
import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.VMJExchange;

public abstract class DonationResourceComponent implements DonationResource {
    protected RepositoryUtil<FinancialReport> financialReportRepository;
    protected RepositoryUtil<Donation> donationRepository;

    public DonationResourceComponent(){
        this.financialReportRepository = new 
        		RepositoryUtil<FinancialReport>(aisco.financialreport.core.FinancialReportComponent.class);
        this.donationRepository = new 
        		RepositoryUtil<Donation>(aisco.donation.core.DonationComponent.class);
    }

    public abstract List<HashMap<String,Object>> saveDonation(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> updateDonation(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> getDonation(VMJExchange vmjExchange);
    public abstract Donation createDonation(VMJExchange vmjExchange);
    public abstract Donation createDonation(VMJExchange vmjExchange, String objectName);
    public abstract Donation createDonation(VMJExchange vmjExchange, UUID id);
    public abstract Donation createDonation(VMJExchange vmjExchange, UUID id, String objectName);
    public abstract List<HashMap<String,Object>> transformDonationListToHashMap(List<Donation> donationList);
    public abstract List<HashMap<String,Object>> getAllDonation(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> deleteDonation(VMJExchange vmjExchange);
    public abstract FinancialReport createIncome(VMJExchange vmjExchange);
}
