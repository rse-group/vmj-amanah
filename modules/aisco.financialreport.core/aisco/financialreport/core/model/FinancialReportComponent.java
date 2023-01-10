package aisco.financialreport.core;

import java.util.*;

import aisco.program.core.Program;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import aisco.program.core.Program;

import aisco.chartofaccount.core.ChartOfAccount;

@Entity
@Table(name="financialreport_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class FinancialReportComponent implements FinancialReport {

    @Id
    protected int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getDatestamp();
    public abstract void setDatestamp(String datestamp);

    public abstract long getAmount();
    public abstract void setAmount(long amount);

    public abstract String getDescription();
    public abstract void setDescription(String description);

    public abstract Program getProgram();
    public abstract void setProgram(Program program);

    public abstract ChartOfAccount getCoa();
    public abstract void setCoa(ChartOfAccount coa);

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
