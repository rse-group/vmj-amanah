package aisco.programreport.core;
import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class ProgramReportResourceDecorator extends ProgramReportResourceComponent{
	protected ProgramReportResourceComponent record;

    public ProgramReportResourceDecorator(ProgramReportResourceComponent record) {
        this.record = record;
    }

    public List<HashMap<String,Object>> saveProgramReport(VMJExchange vmjExchange){
		return record.saveProgramReport(vmjExchange);
	}

    public ProgramReport createProgramReport(VMJExchange vmjExchange){
		return record.createProgramReport(vmjExchange);
	}

    public ProgramReport createProgramReport(VMJExchange vmjExchange, UUID id, String createdAt){
		return record.createProgramReport(vmjExchange, id, createdAt);
	}

    public HashMap<String, Object> updateProgramReport(VMJExchange vmjExchange){
		return record.updateProgramReport(vmjExchange);
	}

	public ProgramReport updateProgramReport(VMJExchange vmjExchange, UUID id){
		return record.updateProgramReport(vmjExchange, id);
	}

    public HashMap<String, Object> getProgramReport(VMJExchange vmjExchange){
		return record.getProgramReport(vmjExchange);
	}

    public List<HashMap<String,Object>> getAllProgramReport(VMJExchange vmjExchange){
		return record.getAllProgramReport(vmjExchange);
	}

	public List<HashMap<String,Object>> getAllProgramReportByProgramId(VMJExchange vmjExchange){
		return record.getAllProgramReportByProgramId(vmjExchange);
	}

    public List<HashMap<String,Object>> transformProgramReportListToHashMap(List<ProgramReport> programReportList){
		return record.transformProgramReportListToHashMap(programReportList);
	}

    public List<HashMap<String,Object>> deleteProgramReport(VMJExchange vmjExchange){
		return record.deleteProgramReport(vmjExchange);
	}

}
