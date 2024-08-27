package aisco.donation.core;

import java.util.*;

import aisco.financialreport.core.*;

import vmj.hibernate.integrator.DaoUtil;
import vmj.routing.route.VMJExchange;

public abstract class DonationResourceComponent implements DonationResource {
    protected DaoUtil<Donation> donationDao;
    protected DaoUtil<FinancialReport> financialReportDao;

    public DonationResourceComponent(){
        this.donationDao = new
            DaoUtil<Donation>(aisco.donation.core.DonationComponent.class);
        this.financialReportDao = new
        		DaoUtil<FinancialReport>(aisco.financialreport.core.FinancialReportComponent.class);
    }
}
