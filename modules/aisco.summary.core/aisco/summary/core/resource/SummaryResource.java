package aisco.summary.core;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import java.util.*;

public interface SummaryResource {
    ArrayList<HashMap<String,Object>> list(VMJExchange vmjExchange);
}