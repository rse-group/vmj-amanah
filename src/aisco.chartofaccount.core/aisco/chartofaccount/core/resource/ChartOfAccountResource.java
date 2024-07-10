package aisco.chartofaccount.core;

import java.util.*;

import vmj.routing.route.VMJExchange;

public interface ChartOfAccountResource {
    List<HashMap<String,Object>> saveChartOfAccount(VMJExchange vmjExchange);
    HashMap<String, Object> updateChartOfAccount(VMJExchange vmjExchange);
    HashMap<String, Object> getChartOfAccount(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllChartOfAccount(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteChartOfAccount(VMJExchange vmjExchange);
}