package aisco.program.operational;

import java.util.*;
import java.net.URLConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import aisco.program.ProgramFactory;
import aisco.program.core.ProgramResourceDecorator;
import aisco.program.core.ProgramResourceComponent;
import aisco.program.core.Program;
import aisco.program.core.ProgramDecorator;

import vmj.auth.annotations.Restricted;

public class ProgramResourceImpl extends ProgramResourceComponent {
	
//	public ProgramResourceImpl(ProgramResourceComponent record) {
//        super(record);
//    }

    @Restricted(permissionName="CreateOperational")
    @Route(url = "call/operational/save")
    public List<HashMap<String, Object>> saveProgram(VMJExchange vmjExchange) {
        List<String> keys = new ArrayList<String>(Arrays.asList("name", "description"));
        List<Object> types = new ArrayList<Object>(Arrays.asList("", ""));
        vmjExchange.payloadChecker(keys, types, false);
        Program program = createProgram(vmjExchange);
        programRepository.saveObject(program);
        return getAllProgram(vmjExchange);
    }

    public Program createProgram(VMJExchange vmjExchange) {
        String name = (String) vmjExchange.getRequestBodyForm("name");
        String description = (String) vmjExchange.getRequestBodyForm("description");
        String target = "";
        String partner = "";
        String logoUrl = "";
        String executionDate = "";
        Program program = ProgramFactory.createProgram("aisco.program.core.ProgramImpl", name, description, target, partner, logoUrl, executionDate);
        Program programOperational = ProgramFactory.createProgram("aisco.program.operational.ProgramImpl", program);
        return programOperational;
    }
    
    public Program createProgram(VMJExchange vmjExchange, UUID id) {
        Program savedProgram = programRepository.getObject(id);
        UUID recordProgramId = (((ProgramDecorator) savedProgram).getRecord()).getIdProgram();
        String name = (String) vmjExchange.getRequestBodyForm("name");
        String description = (String) vmjExchange.getRequestBodyForm("description");
        String target = "";
        String partner = "";
        String logoUrl = "";
        String executionDate = "";
        Program program = ProgramFactory.createProgram("aisco.program.core.ProgramImpl", recordProgramId, name, description, target, partner, logoUrl, executionDate);
        Program programOperational = ProgramFactory.createProgram("aisco.program.operational.ProgramImpl", id, program);

        return programOperational;
    }

    @Restricted(permissionName="UpdateOperational")
    @Route(url = "call/operational/update")
    public HashMap<String, Object> updateProgram(VMJExchange vmjExchange) {
        List<String> keys = new ArrayList<String>(Arrays.asList("id"));
        List<Object> types = new ArrayList<Object>(Arrays.asList(""));
        vmjExchange.payloadChecker(keys, types, false);

        Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("id");
        UUID id = UUID.fromString(idStr);
        
        Program savedProgram = programRepository.getObject(id);
        savedProgram = createProgram(vmjExchange, id);
        programRepository.updateObject(savedProgram);
        return savedProgram.toHashMap();
    }

    @Route(url = "call/operational/detail")
    public HashMap<String, Object> getProgram(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("id");
        UUID id = UUID.fromString(idStr);
        Program program = programRepository.getObject(id);
        return program.toHashMap();
    }

    @Route(url = "call/operational/list")
    public List<HashMap<String, Object>> getAllProgram(VMJExchange vmjExchange) {
        List<Program> programList = programRepository.getAllObject("program_operational");
        return transformProgramListToHashMap(programList);
    }

    public List<HashMap<String, Object>> transformProgramListToHashMap(List<Program> programList) {
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < programList.size(); i++) {
            resultList.add(programList.get(i).toHashMap());
        }

        return resultList;
    }
    
    @Restricted(permissionName="DeleteOperational")
    @Route(url = "call/operational/delete")
    public List<HashMap<String, Object>> deleteProgram(VMJExchange vmjExchange) {
        List<String> keys = new ArrayList<String>(Arrays.asList("id"));
        List<Object> types = new ArrayList<Object>(Arrays.asList(""));
        vmjExchange.payloadChecker(keys, types, false);
        Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("id");
        UUID id = UUID.fromString(idStr);
        programRepository.deleteObject(id);
        return getAllProgram(vmjExchange);
    }

}