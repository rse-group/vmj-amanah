package aisco.program.activity;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.program.ProgramFactory;
import aisco.program.core.ProgramResourceComponent;
import aisco.program.core.Program;

import prices.auth.vmj.annotations.Restricted;

public class ProgramResourceImpl extends ProgramResourceComponent {

	@Restricted(permissionName="CreateProgram")
    @Route(url="call/activity/save")
    public List<HashMap<String,Object>> saveProgram(VMJExchange vmjExchange) {
        Program program = createProgram(vmjExchange);
        programRepository.saveObject(program);
        System.out.println(program);
        return getAllProgram(vmjExchange);
    }

    public Program createProgram(VMJExchange vmjExchange) {
        String name = (String) vmjExchange.getRequestBodyForm("name");
        String description = (String) vmjExchange.getRequestBodyForm("description");
        String target = (String) vmjExchange.getRequestBodyForm("target");
        String partner = (String) vmjExchange.getRequestBodyForm("partner");
        String logoUrl = (String) vmjExchange.getRequestBodyForm("logoUrl");
        String executionDate = (String) vmjExchange.getRequestBodyForm("executionDate");
        Program program = ProgramFactory.createProgram("aisco.program.activity.ProgramImpl", name, description, target, partner, logoUrl, executionDate);
        return program;
    }

    @Restricted(permissionName="UpdateProgram")
    @Route(url="call/activity/update")
    public HashMap<String, Object> updateProgram(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        Program program = programRepository.getObject(id);
        program = updateProgram(vmjExchange, id);
        programRepository.updateObject(program);
        return program.toHashMap();
    }

    public Program updateProgram(VMJExchange vmjExchange, int id) {
        Program program = programRepository.getObject(id);
        program.setName((String) vmjExchange.getRequestBodyForm("name"));
        program.setDescription((String) vmjExchange.getRequestBodyForm("description"));
        program.setTarget((String) vmjExchange.getRequestBodyForm("target"));
        program.setPartner((String) vmjExchange.getRequestBodyForm("partner"));
        program.setLogoUrl((String) vmjExchange.getRequestBodyForm("logoUrl"));
        program.setExecutionDate((String) vmjExchange.getRequestBodyForm("executionDate"));
        return program;
    }

    @Route(url="call/activity/detail")
    public HashMap<String, Object> getProgram(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("id");
        int id = Integer.parseInt(idStr);
        Program program = programRepository.getObject(id);
        System.out.println(program);
        return program.toHashMap();
    }

    @Route(url="call/activity/list")
    public List<HashMap<String,Object>> getAllProgram(VMJExchange vmjExchange) {
        List<Program> programList = programRepository.getAllObject("program_activity");

        return transformProgramListToHashMap(programList);
    }

    public List<HashMap<String,Object>> transformProgramListToHashMap(List<Program> programList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < programList.size(); i++) {
            resultList.add(programList.get(i).toHashMap());
        }

        return resultList;
    }

    @Restricted(permissionName="DeleteProgram")
    @Route(url="call/activity/delete")
    public List<HashMap<String,Object>> deleteProgram(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        programRepository.deleteObject(id);
        return getAllProgram(vmjExchange);
    }

}
