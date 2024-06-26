package aisco.programreport.core;
import java.util.*;

import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.VMJExchange;

public abstract class ProgramReportResourceComponent implements ProgramReportResource{
	protected RepositoryUtil<ProgramReport> programReportRepository;

    public ProgramReportResourceComponent(){
        this.programReportRepository = new RepositoryUtil<>(aisco.programreport.core.ProgramReportComponent.class);
    }	

    public abstract List<HashMap<String,Object>> saveProgramReport(VMJExchange vmjExchange);
    public abstract ProgramReport createProgramReport(VMJExchange vmjExchange);
	public abstract ProgramReport createProgramReport(VMJExchange vmjExchange, UUID id, String createdAt);    
	public abstract HashMap<String, Object> updateProgramReport(VMJExchange vmjExchange);
    public abstract ProgramReport updateProgramReport(VMJExchange vmjExchange, UUID id);
    public abstract HashMap<String, Object> getProgramReport(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllProgramReport(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllProgramReportByProgramId(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformProgramReportListToHashMap(List<ProgramReport> List);
    public abstract List<HashMap<String,Object>> deleteProgramReport(VMJExchange vmjExchange);

}
