package aisco.financialreport.core;

import java.lang.Math;
import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import aisco.program.core.*;
import aisco.chartofaccount.core.*;

@Entity(name="financialreport_impl")
@Table(name="financialreport_impl")
public class FinancialReportImpl extends FinancialReportComponent {

    public FinancialReportImpl(String datestamp, long amountFinancialReport, String descriptionFinancialReport, Program program, ChartOfAccount coa) {
    	this.idFinancialReport = UUID.randomUUID();
        this.datestamp = datestamp;
        this.amountFinancialReport = amountFinancialReport;
        this.descriptionFinancialReport = descriptionFinancialReport;
        this.program = program;
        this.coa = coa;
        this.objectName = FinancialReportImpl.class.getName();
    }

    public FinancialReportImpl(UUID idFinancialReport, String datestamp, long amountFinancialReport, String descriptionFinancialReport, Program program, ChartOfAccount coa) {
        this.idFinancialReport = idFinancialReport;
        this.datestamp = datestamp;
        this.amountFinancialReport = amountFinancialReport;
        this.descriptionFinancialReport = descriptionFinancialReport;
        this.program = program;
        this.coa = coa;
        this.objectName = FinancialReportImpl.class.getName();
    }

    public FinancialReportImpl() {
    	this.idFinancialReport = UUID.randomUUID();
        this.datestamp = "";
        this.amountFinancialReport = 0;
        this.descriptionFinancialReport = "";
        this.objectName = FinancialReportImpl.class.getName();
    }
}
