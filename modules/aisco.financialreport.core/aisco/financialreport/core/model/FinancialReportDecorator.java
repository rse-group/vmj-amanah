package aisco.financialreport.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.CascadeType;

import aisco.program.core.Program;
import aisco.chartofaccount.core.ChartOfAccount;

@MappedSuperclass
public abstract class FinancialReportDecorator extends FinancialReportComponent {

    public FinancialReportDecorator() {}
    
    public FinancialReportDecorator(FinancialReportComponent financialReport) {
        Random r = new Random();
		this.id = Math.abs(r.nextInt());
		this.datestamp = financialReport.getDatestamp();
        this.amount = financialReport.getAmount();
        this.description = financialReport.getDescription();
        this.program = financialReport.getProgram();
        this.coa = financialReport.getCoa();
    }
    
    public FinancialReportDecorator(int id, FinancialReportComponent financialReport) {
		this.id = id;
		this.datestamp = financialReport.getDatestamp();
        this.amount = financialReport.getAmount();
        this.description = financialReport.getDescription();
        this.program = financialReport.getProgram();
        this.coa = financialReport.getCoa();
    }
}