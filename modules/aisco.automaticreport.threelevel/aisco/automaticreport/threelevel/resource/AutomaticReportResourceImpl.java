package aisco.automaticreport.threelevel;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.automaticreport.core.AutomaticReportResourceDecorator;
import aisco.automaticreport.core.AutomaticReportResourceComponent;

public class AutomaticReportResourceImpl extends AutomaticReportResourceDecorator {

    public AutomaticReportResourceImpl(AutomaticReportResourceComponent record) {
        super(record);
    }

    @Route(url="call/automatic-report-threelevel/list")
    public List<HashMap<String,Object>> list(VMJExchange vmjExchange) {
        List<HashMap<String, Object>> allIncomes = getIncomes();
        List<HashMap<String, Object>> allExpenses = getExpenses();

        List<HashMap<String,Object>> coaLevel1 = transFormToChartOfAccount(allIncomes, allExpenses, 1);
        List<HashMap<String,Object>> coaLevel2 = transFormToChartOfAccount(allIncomes, allExpenses, 2);
        List<HashMap<String,Object>> coaLevel3 = transFormToChartOfAccount(allIncomes, allExpenses, 5);

        try {
            coaLevel1.addAll(coaLevel2);
            coaLevel1.addAll(coaLevel3);
            coaLevel1 = sort(coaLevel1);
        } catch (Exception e) {
			e.printStackTrace();
		}

        return coaLevel1;
    }
}