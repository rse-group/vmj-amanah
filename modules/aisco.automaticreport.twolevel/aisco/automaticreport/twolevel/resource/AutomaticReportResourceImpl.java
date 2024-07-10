package aisco.automaticreport.twolevel;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.automaticreport.core.AutomaticReportResourceDecorator;
import aisco.automaticreport.core.AutomaticReportResourceComponent;

public class AutomaticReportResourceImpl extends AutomaticReportResourceDecorator {

    public AutomaticReportResourceImpl(AutomaticReportResourceComponent record) {
        super(record);
    }

    @Route(url="call/automatic-report-twolevel/list")
    public List<HashMap<String,Object>> list(VMJExchange vmjExchange) {
        List<HashMap<String, Object>> allIncomes = getIncomes();
        List<HashMap<String, Object>> allExpenses = getExpenses();

        List<HashMap<String,Object>> coaSheets = new ArrayList<HashMap<String, Object>>();
        List<HashMap<String,Object>> coaLevel1 = transFormToChartOfAccount(allIncomes, allExpenses, 1);
        List<HashMap<String,Object>> coaLevel2 = transFormToChartOfAccount(allIncomes, allExpenses, 2);

        try {
            coaSheets = getOperationalActivity(coaLevel1, coaLevel2);
            List<HashMap<String,Object>> investActivity = getInvestActivity();
            List<HashMap<String,Object>> fundingActivity = getFundingActivity();
            List<HashMap<String,Object>> cashFlowSummary = getCashFlowSummary(coaLevel1);
            HashMap<String, Object> emptyRow = this.createCoa("", 0);
            coaSheets = this.removeAmount(coaSheets, 1);

            coaSheets.addAll(investActivity);
            coaSheets.addAll(fundingActivity);
            coaSheets.add(emptyRow);
            coaSheets.addAll(cashFlowSummary);
        } catch (Exception e) {
			e.printStackTrace();
		}

        return coaSheets;
    }
}