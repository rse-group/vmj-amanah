package aisco.automaticreport.journalentry;

import java.util.*;
import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import aisco.automaticreport.core.AutomaticReportResourceDecorator;
import aisco.automaticreport.core.AutomaticReportResourceComponent;

import aisco.chartofaccount.core.ChartOfAccount;
import aisco.automaticreport.journalentry.JournalEntry;

public class AutomaticReportResourceImpl extends AutomaticReportResourceDecorator {
    protected RepositoryUtil<JournalEntry> journalEntryRepository;
    protected RepositoryUtil<ChartOfAccount> chartOfAccountRepository;


    public AutomaticReportResourceImpl(AutomaticReportResourceComponent record) {
        super(record);
        this.journalEntryRepository = new RepositoryUtil<JournalEntry>(
            aisco.automaticreport.journalentry.JournalEntryComponent.class);
        this.chartOfAccountRepository = new RepositoryUtil<ChartOfAccount>(
            aisco.chartofaccount.core.ChartOfAccountComponent.class);
            
    }
}