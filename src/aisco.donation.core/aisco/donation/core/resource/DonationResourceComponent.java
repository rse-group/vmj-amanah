package aisco.donation.core;

import java.util.*;

import aisco.financialreport.core.*;

import aisco.program.core.*;

import vmj.auth.model.core.*;

import vmj.hibernate.integrator.DaoUtil;
import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.VMJExchange;

public abstract class DonationResourceComponent implements DonationResource {
    protected RepositoryUtil<FinancialReport> financialReportRepository;
    protected RepositoryUtil<Donation> donationRepository;
    protected RepositoryUtil<User> userRepository;
    protected RepositoryUtil<UserRole> userRoleRepository;
    protected RepositoryUtil<Program> programRepository;

    public DonationResourceComponent(){
        this.financialReportRepository = new 
        		RepositoryUtil<FinancialReport>(aisco.financialreport.core.FinancialReportComponent.class);
        this.donationRepository = new 
        		RepositoryUtil<Donation>(aisco.donation.core.DonationComponent.class);
        this.userRepository = new
        		RepositoryUtil<User>(vmj.auth.model.core.UserImpl.class);
        this.userRoleRepository = new
        		RepositoryUtil<UserRole>(vmj.auth.model.core.UserRoleImpl.class);
        this.programRepository = new
                RepositoryUtil<Program>(aisco.program.core.ProgramComponent.class);
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
