package aisco.automaticreport.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class AutomaticReportResourceDecorator extends AutomaticReportResourceComponent {
    protected AutomaticReportResourceComponent record;

    public AutomaticReportResourceDecorator(AutomaticReportResourceComponent record) {
        this.record = record;
    }

    @Route(url = "call/automatic-report/list-decorator")
    public List<HashMap<String,Object>> list(VMJExchange vmjExchange) {
        return record.list(vmjExchange);
    }
}