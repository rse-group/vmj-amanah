package aisco.program.core;

import java.util.*;

import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.RepositoryUtil;

import aisco.program.core.Program;



public abstract class ProgramResourceComponent implements ProgramResource {
    protected RepositoryUtil<Program> programRepository;

    public ProgramResourceComponent(){
        this.programRepository = new RepositoryUtil<Program>(aisco.program.core.ProgramComponent.class);
    }

    public abstract List<HashMap<String,Object>> saveProgram(VMJExchange vmjExchange);
    public abstract Program createProgram(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> updateProgram(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> getProgram(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllProgram(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformProgramListToHashMap(List<Program> programList);
    public abstract List<HashMap<String,Object>> deleteProgram(VMJExchange vmjExchange);

}

