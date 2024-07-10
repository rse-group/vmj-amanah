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
    public String paymentMethodIncome;

    public FinancialReportImpl(){
        super();
        this.objectName = FinancialReportImpl.class.getName();
    }
    
    public FinancialReportImpl(String paymentMethodIncome) {
    	super();
    	this.paymentMethodIncome = paymentMethodIncome;
    	this.objectName = FinancialReportImpl.class.getName();
    }
    
    public FinancialReportImpl(FinancialReportComponent record, String paymentMethodIncome) {
        super(record, FinancialReportImpl.class.getName());
    	this.paymentMethodIncome = paymentMethodIncome;
    	this.objectName = FinancialReportImpl.class.getName();
    }
    
    public FinancialReportImpl(UUID id, FinancialReportComponent record, String paymentMethodIncome) {
        super(id, record);
    	this.paymentMethodIncome = paymentMethodIncome;
    }

    public String getPaymentMethodIncome() {
        return this.paymentMethodIncome;
    }

    public void setPaymentMethodIncome(String paymentMethodIncome) {
        this.paymentMethodIncome = paymentMethodIncome;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            " paymentMethodIncome='" + getPaymentMethodIncome() + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> financialReportMap = new HashMap<String,Object>();
        financialReportMap.put("idFinancialReport", idFinancialReport);
        financialReportMap.put("datestamp", getDatestamp());
        financialReportMap.put("amountFinancialReport", getAmountFinancialReport());
        financialReportMap.put("descriptionFinancialReport", getDescriptionFinancialReport());
        financialReportMap.put("paymentMethodIncome", getPaymentMethodIncome());
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
