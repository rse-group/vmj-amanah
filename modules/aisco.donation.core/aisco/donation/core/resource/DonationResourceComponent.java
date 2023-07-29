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
}
