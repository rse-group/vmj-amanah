package aisco.financialreport.income;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import aisco.financialreport.core.FinancialReportDecorator;
import aisco.financialreport.core.FinancialReportComponent;

@Entity(name="financialreport_income")
@Table(name="financialreport_income")
public class FinancialReportImpl extends FinancialReportDecorator {
    public String paymentMethod;

    public FinancialReportImpl(){
        super();
        this.objectName = FinancialReportImpl.class.getName();
    }
    
    public FinancialReportImpl(String paymentMethod) {
    	super();
    	this.paymentMethod = paymentMethod;
    	this.objectName = FinancialReportImpl.class.getName();
    }
    
    public FinancialReportImpl(FinancialReportComponent record, String paymentMethod) {
        super(record, FinancialReportImpl.class.getName());
    	this.paymentMethod = paymentMethod;
    	this.objectName = FinancialReportImpl.class.getName();
    }
    
    public FinancialReportImpl(UUID id, FinancialReportComponent record, String paymentMethod) {
        super(id, record);
    	this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            " paymentMethod='" + getPaymentMethod() + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> financialReportMap = new HashMap<String,Object>();
        financialReportMap.put("id", id);
        financialReportMap.put("datestamp", getDatestamp());
        financialReportMap.put("amount", getAmount());
        financialReportMap.put("description", getDescription());
        financialReportMap.put("paymentMethod", getPaymentMethod());
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
