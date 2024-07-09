package aisco.program.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class ProgramResourceDecorator extends ProgramResourceComponent {

	protected ProgramResourceComponent record;

    public ProgramResourceDecorator(ProgramResourceComponent record) {
        this.record = record;
    }

    public List<HashMap<String,Object>> saveProgram(VMJExchange vmjExchange) {
        return record.saveProgram(vmjExchange);
    }

    public Program createProgram(VMJExchange vmjExchange) {
        return record.createProgram(vmjExchange);
    }

    public HashMap<String, Object> updateProgram(VMJExchange vmjExchange) {
        return record.updateProgram(vmjExchange);
    }

    public HashMap<String, Object> getProgram(VMJExchange vmjExchange) {
        return record.getProgram(vmjExchange);
    }

    public List<HashMap<String,Object>> getAllProgram(VMJExchange vmjExchange) {
        return record.getAllProgram(vmjExchange);
    }

     public List<HashMap<String,Object>> transformProgramListToHashMap(List<Program> financialReportList) {
         return record.transformProgramListToHashMap(financialReportList);
     }

    public List<HashMap<String,Object>> deleteProgram(VMJExchange vmjExchange) {
        return record.deleteProgram(vmjExchange);
    }
}