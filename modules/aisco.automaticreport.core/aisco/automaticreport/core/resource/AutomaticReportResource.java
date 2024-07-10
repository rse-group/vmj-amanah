package aisco.automaticreport.core;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import java.util.*;

public interface AutomaticReportResource {
    List<HashMap<String,Object>> list(VMJExchange vmjExchange);
}