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

    public FinancialReportImpl(String datestamp, long amount, String description, Program program, ChartOfAccount coa) {
    	this.id = UUID.randomUUID();
        this.datestamp = datestamp;
        this.amount = amount;
        this.description = description;
        this.program = program;
        this.coa = coa;
        this.objectName = FinancialReportImpl.class.getName();
    }

    public FinancialReportImpl(UUID id, String datestamp, long amount, String description, Program program, ChartOfAccount coa) {
        this.id = id;
        this.datestamp = datestamp;
        this.amount = amount;
        this.description = description;
        this.program = program;
        this.coa = coa;
        this.objectName = FinancialReportImpl.class.getName();
    }

    public FinancialReportImpl() {
    	this.id = UUID.randomUUID();
        this.datestamp = "";
        this.amount = 0;
        this.description = "";
        this.objectName = FinancialReportImpl.class.getName();
    }
}
