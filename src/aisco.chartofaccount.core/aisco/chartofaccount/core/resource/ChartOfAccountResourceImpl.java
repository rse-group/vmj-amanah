package aisco.chartofaccount.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.chartofaccount.ChartOfAccountFactory;

import vmj.auth.annotations.Restricted;

public class ChartOfAccountResourceImpl extends ChartOfAccountResourceComponent {

    @Restricted(permissionName="administrator")
    @Route(url="call/chart-of-account/save")
    public List<HashMap<String,Object>> saveChartOfAccount(VMJExchange vmjExchange) {

        ChartOfAccount chartOfAccount = createChartOfAccount(vmjExchange);
        chartOfAccountRepository.saveObject(chartOfAccount);
        System.out.println(chartOfAccount);
        return getAllChartOfAccount(vmjExchange);
    }

    public ChartOfAccount createChartOfAccount(VMJExchange vmjExchange) {
        String codeStr = (String) vmjExchange.getRequestBodyForm("code");
        int code = Integer.parseInt(codeStr);
        String name = (String) vmjExchange.getRequestBodyForm("name");
        String description = (String) vmjExchange.getRequestBodyForm("description");
        String isVisible = (String) vmjExchange.getRequestBodyForm("isVisible");
        ChartOfAccount chartOfAccount = ChartOfAccountFactory.createChartOfAccount("aisco.chartofaccount.core.ChartOfAccountImpl", code, name, description, isVisible);
        return chartOfAccount;
    }

    public ChartOfAccount createChartOfAccount(VMJExchange vmjExchange, int id) {
        String codeStr = (String) vmjExchange.getRequestBodyForm("code");
        int code = Integer.parseInt(codeStr);
        String name = (String) vmjExchange.getRequestBodyForm("name");
        String description = (String) vmjExchange.getRequestBodyForm("description");
        String isVisible = (String) vmjExchange.getRequestBodyForm("isVisible");
        ChartOfAccount chartOfAccount = ChartOfAccountFactory.createChartOfAccount("aisco.chartofaccount.core.ChartOfAccountImpl", id, code, name, description, isVisible);
        return chartOfAccount;
    }

    @Restricted(permissionName="administrator")
    @Route(url="call/chart-of-account/update")
    public HashMap<String, Object> updateChartOfAccount(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        ChartOfAccount chartOfAccount = chartOfAccountRepository.getObject(id);
        chartOfAccount = createChartOfAccount(vmjExchange, id);
        chartOfAccountRepository.updateObject(chartOfAccount);
        return chartOfAccount.toHashMap();
    }

    @Route(url="call/chart-of-account/detail")
    public HashMap<String, Object> getChartOfAccount(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("id");
        int id = Integer.parseInt(idStr);
        ChartOfAccount chartOfAccount = chartOfAccountRepository.getObject(id);
        System.out.println(chartOfAccount);
        return chartOfAccount.toHashMap();
    }

    @Route(url="call/chart-of-account/list")
    public List<HashMap<String,Object>> getAllChartOfAccount(VMJExchange vmjExchange) {
        List<ChartOfAccount> chartOfAccountList = chartOfAccountRepository.getAllObject("chartofaccount_impl", ChartOfAccountImpl.class.getName());
        return transformChartOfAccountListToHashMap(chartOfAccountList);
    }

    public List<HashMap<String,Object>> transformChartOfAccountListToHashMap(List<ChartOfAccount> chartOfAccountList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < chartOfAccountList.size(); i++) {
            resultList.add(chartOfAccountList.get(i).toHashMap());
        }

        return resultList;
    }

    @Restricted(permissionName="administrator")
    @Route(url="call/chart-of-account/delete")
    public List<HashMap<String,Object>> deleteChartOfAccount(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        chartOfAccountRepository.deleteObject(id);
        return getAllChartOfAccount(vmjExchange);
    }
}
