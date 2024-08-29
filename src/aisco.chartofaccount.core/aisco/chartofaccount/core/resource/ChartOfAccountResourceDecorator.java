package aisco.chartofaccount.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class ChartOfAccountResourceDecorator extends ChartOfAccountResourceComponent {

    protected ChartOfAccountResourceComponent record;

    public ChartOfAccountResourceDecorator(ChartOfAccountResourceComponent record) {
        this.record = record;
    }

    public List<HashMap<String,Object>> saveChartOfAccount(VMJExchange vmjExchange) {
        return record.saveChartOfAccount(vmjExchange);
    }

    public ChartOfAccount createChartOfAccount(VMJExchange vmjExchange) {
        return record.createChartOfAccount(vmjExchange);
    }
    
    public ChartOfAccount createChartOfAccount(VMJExchange vmjExchange, int id) {
        return record.createChartOfAccount(vmjExchange, id);
    }

    public HashMap<String, Object> updateChartOfAccount(VMJExchange vmjExchange) {
        return record.updateChartOfAccount(vmjExchange);
    }

    public HashMap<String, Object> getChartOfAccount(VMJExchange vmjExchange) {
        return record.getChartOfAccount(vmjExchange);
    }

    public List<HashMap<String,Object>> getAllChartOfAccount(VMJExchange vmjExchange) {
        return record.getAllChartOfAccount(vmjExchange);
    }

     public List<HashMap<String,Object>> transformChartOfAccountListToHashMap(List<ChartOfAccount> financialReportList) {
         return record.transformChartOfAccountListToHashMap(financialReportList);
     }

    public List<HashMap<String,Object>> deleteChartOfAccount(VMJExchange vmjExchange) {
        return record.deleteChartOfAccount(vmjExchange);
    }
}