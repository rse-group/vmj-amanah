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
    
    public FinancialReportImpl(FinancialReportComponent record) {
        super(record, FinancialReportImpl.class.getName());
        this.objectName = FinancialReportImpl.class.getName();
    }
    
    public FinancialReportImpl(UUID id, FinancialReportComponent record) {
        super(id, record);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", record='" + getRecord() + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
    	HashMap<String, Object> financialReportMap = record.toHashMap();
        financialReportMap.put("id", id);
        return financialReportMap;
    }
}
