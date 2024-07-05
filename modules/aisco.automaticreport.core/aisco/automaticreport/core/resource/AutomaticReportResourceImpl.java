package aisco.automaticreport.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public class AutomaticReportResourceImpl extends AutomaticReportResourceComponent {

    @Route(url="call/automatic-report/list")
    public List<HashMap<String,Object>> list(VMJExchange vmjExchange) {
        List<HashMap<String, Object>> allIncomes = this.getIncomes();
        List<HashMap<String, Object>> allExpenses = this.getExpenses();

        List<HashMap<String,Object>> coaLevel1 = transFormToChartOfAccount(allIncomes, allExpenses, 1);

        try {
        coaLevel1 = this.sort(coaLevel1);
        } catch (Exception e) {
			e.printStackTrace();
		}

        return coaLevel1;
    }
}