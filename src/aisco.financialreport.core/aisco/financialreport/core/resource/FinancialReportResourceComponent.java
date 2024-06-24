package aisco.financialreport.core;

import java.util.*;

import vmj.hibernate.integrator.RepositoryUtil;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.financialreport.FinancialReportFactory;

import aisco.program.core.Program;
import aisco.chartofaccount.core.ChartOfAccount;

import vmj.auth.annotations.Restricted;

public abstract class FinancialReportResourceComponent implements FinancialReportResource {
    protected RepositoryUtil<FinancialReport> financialReportRepository;

    public FinancialReportResourceComponent(){
        this.financialReportRepository = new
            RepositoryUtil<FinancialReport>(aisco.financialreport.core.FinancialReportComponent.class);
    }

    public abstract List<HashMap<String,Object>> saveFinancialReport(VMJExchange vmjExchange);
    public abstract FinancialReport createFinancialReport(VMJExchange vmjExchange);
    public abstract FinancialReport createFinancialReport(VMJExchange vmjExchange, UUID id);
    public abstract HashMap<String, Object> updateFinancialReport(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> getFinancialReport(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllFinancialReport(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformFinancialReportListToHashMap(List<FinancialReport> financialReportList);
    public abstract List<HashMap<String,Object>> deleteFinancialReport(VMJExchange vmjExchange);
}

