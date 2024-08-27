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

    @OneToOne(cascade=CascadeType.ALL)
    protected FinancialReportComponent record;

    public FinancialReportDecorator(FinancialReportComponent record, String objectName) {
        this.record = record;
		this.id = UUID.randomUUID();
		this.record.objectName = objectName;
    }

    public FinancialReportDecorator(UUID id, FinancialReportComponent record) {
        this.id = id;
        this.record = record;
    }

    public FinancialReportDecorator() {
        super();
        this.record = new FinancialReportImpl();
        this.id = UUID.randomUUID();
    }

    public FinancialReportComponent getRecord() {
        return this.record;
    }

    public void setRecord(FinancialReportComponent record) {
        this.record = record;
    }

    public String getDatestamp() {
        return this.record.getDatestamp();
    }
    public void setDatestamp(String datestamp) {
        this.record.setDatestamp(datestamp);
    }

    public long getAmount() {
        return this.record.getAmount();
    }
    public void setAmount(long amount) {
        this.record.setAmount(amount);
    }

    public String getDescription() {
        return this.record.getDescription();
    }
    public void setDescription(String description) {
        this.record.setDescription(description);
    }

    public Program getProgram() {
        return this.record.getProgram();
    }
    public void setProgram(Program program) {
        this.record.setProgram(program);
    }

    public ChartOfAccount getCoa() {
        return this.record.getCoa();
    }
    public void setCoa(ChartOfAccount coa) {
        this.record.setCoa(coa);
    }

    public HashMap<String, Object> toHashMap() {
        return this.record.toHashMap();
    }
}
