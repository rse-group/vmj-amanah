package aisco.programreport.core;
import java.util.*;

import vmj.routing.route.VMJExchange;

public interface ProgramReportResource {
    List<HashMap<String,Object>> saveProgramReport(VMJExchange vmjExchange);
    HashMap<String, Object> updateProgramReport(VMJExchange vmjExchange);
    HashMap<String, Object> getProgramReport(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllProgramReport(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllProgramReportByProgramId(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteProgramReport(VMJExchange vmjExchange);
}
