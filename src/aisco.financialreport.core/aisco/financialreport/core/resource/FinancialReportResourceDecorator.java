package aisco.financialreport.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class FinancialReportResourceDecorator extends FinancialReportResourceComponent {

    protected FinancialReportResourceComponent record;

    public FinancialReportResourceDecorator(FinancialReportResourceComponent record) {
        this.record = record;
    }

    public List<HashMap<String,Object>> saveFinancialReport(VMJExchange vmjExchange) {
        return record.saveFinancialReport(vmjExchange);
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange) {
        return record.createFinancialReport(vmjExchange);
    }
    
    public FinancialReport createFinancialReport(VMJExchange vmjExchange, UUID id) {
        return record.createFinancialReport(vmjExchange, id);
    }

    public HashMap<String, Object> updateFinancialReport(VMJExchange vmjExchange) {
        return record.updateFinancialReport(vmjExchange);
    }

    public HashMap<String, Object> getFinancialReport(VMJExchange vmjExchange) {
        return record.getFinancialReport(vmjExchange);
    }

    public List<HashMap<String,Object>> getAllFinancialReport(VMJExchange vmjExchange) {
        return record.getAllFinancialReport(vmjExchange);
    }

     public List<HashMap<String,Object>> transformFinancialReportListToHashMap(List<FinancialReport> financialReportList) {
         return record.transformFinancialReportListToHashMap(financialReportList);
     }

    public List<HashMap<String,Object>> deleteFinancialReport(VMJExchange vmjExchange) {
        return record.deleteFinancialReport(vmjExchange);
    }
}
