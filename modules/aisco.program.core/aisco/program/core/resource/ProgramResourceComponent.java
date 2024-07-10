package aisco.program.core;

import java.util.*;

import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.RepositoryUtil;

import aisco.program.core.Program;

//import aisco.financialreport.core.FinancialReport;

public abstract class ProgramResourceComponent implements ProgramResource {
    protected RepositoryUtil<Program> programRepository;
//    protected RepositoryUtil<FinancialReport> financialReportRepository;

    public ProgramResourceComponent(){
        this.programRepository = new RepositoryUtil<Program>(aisco.program.core.ProgramComponent.class);
//        this.financialReportRepository = new 
//        		RepositoryUtil<FinancialReport>(aisco.financialreport.core.FinancialReportComponent.class);
    }

    public abstract List<HashMap<String,Object>> saveProgram(VMJExchange vmjExchange);
    public abstract Program createProgram(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> updateProgram(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> getProgram(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllProgram(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformProgramListToHashMap(List<Program> programList);
    public abstract List<HashMap<String,Object>> deleteProgram(VMJExchange vmjExchange);
//    public abstract long calculateDonation(Program program);
//    public abstract double calculatePercentage();
}
