package aisco.program.core;

import java.util.*;

import vmj.routing.route.VMJExchange;

import aisco.program.core.Program;

public interface ProgramResource {
    List<HashMap<String,Object>> saveProgram(VMJExchange vmjExchange);
    HashMap<String, Object> updateProgram(VMJExchange vmjExchange);
    HashMap<String, Object> getProgram(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllProgram(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteProgram(VMJExchange vmjExchange);
//    long calculateDonation(Program program);
//    double calculatePercentage();
}