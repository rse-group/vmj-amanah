package aisco.chartofaccount.core;

import java.util.*;

import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.RepositoryUtil;

public abstract class ChartOfAccountResourceComponent implements ChartOfAccountResource {
    protected RepositoryUtil<ChartOfAccount> chartOfAccountRepository;

    public ChartOfAccountResourceComponent(){
        this.chartOfAccountRepository = new RepositoryUtil<ChartOfAccount>(aisco.chartofaccount.core.ChartOfAccountComponent.class);
    }

    public abstract List<HashMap<String,Object>> saveChartOfAccount(VMJExchange vmjExchange);
    public abstract ChartOfAccount createChartOfAccount(VMJExchange vmjExchange);
    public abstract ChartOfAccount createChartOfAccount(VMJExchange vmjExchange, int id);
    public abstract HashMap<String, Object> updateChartOfAccount(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> getChartOfAccount(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllChartOfAccount(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformChartOfAccountListToHashMap(List<ChartOfAccount> chartOfAccountList);
    public abstract List<HashMap<String,Object>> deleteChartOfAccount(VMJExchange vmjExchange);
}
