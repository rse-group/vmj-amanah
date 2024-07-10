package aisco.financialreport.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import aisco.program.core.*;
import aisco.chartofaccount.core.*;

@Entity(name="financialreport_comp")
@Table(name="financialreport_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class FinancialReportComponent implements FinancialReport {

    @Id
    protected UUID idFinancialReport;
    protected String datestamp;
    protected long amountFinancialReport;
    protected String descriptionFinancialReport;

    @ManyToOne(targetEntity=aisco.program.core.ProgramComponent.class)
    protected Program program;
    @ManyToOne(targetEntity=aisco.chartofaccount.core.ChartOfAccountComponent.class)
    protected ChartOfAccount coa;
    
    protected String objectName = FinancialReportComponent.class.getName();

    public UUID getIdFinancialReport() {
        return this.idFinancialReport;
    }

    public void setIdFinancialReport(UUID idFinancialReport) {
        this.idFinancialReport = idFinancialReport;
    }

    public String getDatestamp() {
        return this.datestamp;
    }
    public void setDatestamp(String datestamp) {
        this.datestamp = datestamp;
    }

    public long getAmountFinancialReport() {
        return this.amountFinancialReport;
    }
    public void setAmountFinancialReport(long amountFinancialReport) {
        this.amountFinancialReport = amountFinancialReport;
    }

    public String getDescriptionFinancialReport() {
        return this.descriptionFinancialReport;
    }
    public void setDescriptionFinancialReport(String descriptionFinancialReport) {
        this.descriptionFinancialReport = descriptionFinancialReport;
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

    @Override
    public String toString() {
        return "{" +
            " idFinancialReport='" + getIdFinancialReport() + "'" +
            ", datestamp='" + getDatestamp() + "'" +
            ", amountFinancialReport='" + getAmountFinancialReport() + "'" +
            ", descriptionFinancialReport='" + getDescriptionFinancialReport() + "'" +
            ", idProgram='" + ((getProgram() == null) ? "" : getProgram().getIdProgram()) + "'" +
            ", idCoa='" + ((getCoa() == null) ? "" : getCoa().getId()) + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> financialReportMap = new HashMap<String,Object>();
        financialReportMap.put("idFinancialReport", getIdFinancialReport());
        financialReportMap.put("datestamp", getDatestamp());
        financialReportMap.put("amountFinancialReport", getAmountFinancialReport());
        financialReportMap.put("descriptionFinancialReport", getDescriptionFinancialReport());
        if (getProgram() != null) {
            financialReportMap.put("idProgram", getProgram().getIdProgram());
            financialReportMap.put("programName", getProgram().getName());
        }
        if (getCoa() != null) {
            financialReportMap.put("idCoa", getCoa().getId());
            financialReportMap.put("coaName", getCoa().getName());
        }
        return financialReportMap;
    }
}
