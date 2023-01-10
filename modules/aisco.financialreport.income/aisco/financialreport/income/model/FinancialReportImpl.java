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

    public FinancialReportImpl(FinancialReportComponent record, String paymentMethod) {
        super(record);
        this.paymentMethod = paymentMethod;
    }

    public FinancialReportImpl(int id, FinancialReportComponent record, String paymentMethod) {
        super(id, record);
        this.paymentMethod = paymentMethod;
    }

    public FinancialReportImpl(){
        super();
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
            ", record='" + getRecord() + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> financialReportMap = record.toHashMap();
        financialReportMap.put("id", id);
        financialReportMap.put("paymentMethod", getPaymentMethod());
        return financialReportMap;
    }
}
