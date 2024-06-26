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
    protected UUID id;
    protected String datestamp;
    protected long amount;
    protected String description;

    @ManyToOne(targetEntity=aisco.program.core.ProgramComponent.class)
    protected Program program;
    @ManyToOne(targetEntity=aisco.chartofaccount.core.ChartOfAccountComponent.class)
    protected ChartOfAccount coa;
    
    protected String objectName = FinancialReportComponent.class.getName();

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
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

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", datestamp='" + getDatestamp() + "'" +
            ", amount='" + getAmount() + "'" +
            ", description='" + getDescription() + "'" +
            ", idProgram='" + ((getProgram() == null) ? "" : getProgram().getIdProgram()) + "'" +
            ", idCoa='" + ((getCoa() == null) ? "" : getCoa().getId()) + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> financialReportMap = new HashMap<String,Object>();
        financialReportMap.put("id", getId());
        financialReportMap.put("datestamp", getDatestamp());
        financialReportMap.put("amount", getAmount());
        financialReportMap.put("description", getDescription());
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
