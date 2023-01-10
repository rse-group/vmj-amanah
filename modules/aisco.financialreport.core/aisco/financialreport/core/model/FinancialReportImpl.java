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

    protected String datestamp;
    protected long amount;
    protected String description;

    @ManyToOne(targetEntity=aisco.program.core.ProgramComponent.class)
    protected Program program;
    @ManyToOne(targetEntity=aisco.chartofaccount.core.ChartOfAccountComponent.class)
    protected ChartOfAccount coa;

    public FinancialReportImpl(String datestamp, long amount, String description, Program program, ChartOfAccount coa) {
        Random r = new Random();
		this.id = Math.abs(r.nextInt());
        this.datestamp = datestamp;
        this.amount = amount;
        this.description = description;
        this.program = program;
        this.coa = coa;
    }

    public FinancialReportImpl(int id, String datestamp, long amount, String description, Program program, ChartOfAccount coa) {
        this.id = Math.abs(id);
        this.datestamp = datestamp;
        this.amount = amount;
        this.description = description;
        this.program = program;
        this.coa = coa;
    }

    public FinancialReportImpl() {
        Random r = new Random();
		this.id = Math.abs(r.nextInt());
        this.datestamp = "";
        this.amount = 0;
        this.description = "";
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatestamp() {
        return this.datestamp;
    }
    public void setDatestamp(String datestamp) {
        this.datestamp = datestamp;
    }

    public long getAmount() {
        return this.amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Program getProgram() {
        return this.program;
    }
    public void setProgram(Program program) {
        this.program = program;
    }

    public ChartOfAccount getCoa() {
        return this.coa;
    }
    public void setCoa(ChartOfAccount coa) {
        this.coa = coa;
    }
}
