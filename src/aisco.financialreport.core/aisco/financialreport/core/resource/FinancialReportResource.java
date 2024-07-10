package aisco.financialreport.core;

import java.util.*;

import vmj.routing.route.VMJExchange;

public interface FinancialReportResource {
    List<HashMap<String,Object>> saveFinancialReport(VMJExchange vmjExchange);
    HashMap<String, Object> updateFinancialReport(VMJExchange vmjExchange);
    HashMap<String, Object> getFinancialReport(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllFinancialReport(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteFinancialReport(VMJExchange vmjExchange);
}