package aisco.summary.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class SummaryResourceDecorator extends SummaryResourceComponent {

    protected SummaryResourceComponent record;

    public SummaryResourceDecorator(SummaryResourceComponent record) {
        this.record = record;
    }

    @Route(url = "call/summary/list-decorator")
    public ArrayList<HashMap<String,Object>> list(VMJExchange vmjExchange) {
        return record.list(vmjExchange);
    }
}