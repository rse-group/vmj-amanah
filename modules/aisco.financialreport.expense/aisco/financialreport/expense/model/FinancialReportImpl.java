package aisco.financialreport.expense;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Table;

import aisco.financialreport.core.FinancialReportDecorator;
import aisco.financialreport.core.FinancialReportComponent;

@Entity(name="financialreport_expense")
@Table(name="financialreport_expense")
public class FinancialReportImpl extends FinancialReportDecorator {

    public FinancialReportImpl(){
        super();
        this.objectName = FinancialReportImpl.class.getName();
    }
    
    public FinancialReportImpl(aisco.financialreport.core.FinancialReportImpl financialReport) {
        super((FinancialReportComponent) financialReport, FinancialReportImpl.class.getName());
        this.objectName = FinancialReportImpl.class.getName();
    }
    
    public FinancialReportImpl(int id, aisco.financialreport.core.FinancialReportImpl financialReport) {
        super(id, (FinancialReportComponent) financialReport);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> financialReportMap = new HashMap<String,Object>();
        financialReportMap.put("id", id);
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
