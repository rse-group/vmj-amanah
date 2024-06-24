package aisco.program.withvolunteer;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.program.ProgramFactory;
import aisco.program.core.ProgramResourceComponent;
import aisco.program.core.ProgramResourceDecorator;
import aisco.program.core.Program;
import volunteers.volunteer.core.*;

import vmj.auth.annotations.Restricted;

public class ProgramResourceImpl extends ProgramResourceDecorator {
	
	public ProgramResourceImpl(ProgramResourceComponent record) {
        super(record);
    }
	
	@Route(url="call/program-with-volunteer/add-volunteer")
	public HashMap<String, Object> addCommunityMember(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
		String volunteerIdStr = (String) vmjExchange.getRequestBodyForm("volunteerId");
        int volunteerId = Integer.parseInt(volunteerIdStr);
		Program program = programDao.getObject(id);
		VolunteerComponent volunteer = programDao.getProxyObject(VolunteerComponent.class, volunteerId);
		((ProgramImpl)program).addMember(volunteer);
		programDao.updateObject(program);
        return program.toHashMap();
    }
	
	//@Restricted(permissionName="ModifyProgramImpl")
    @Route(url="call/program-with-volunteer/save")
    public List<HashMap<String,Object>> saveProgram(VMJExchange vmjExchange) {
        Program program = createProgram(vmjExchange);
        programDao.saveObject(program);
        System.out.println(program);
        return getAllProgram(vmjExchange);
    }

    public Program createProgram(VMJExchange vmjExchange) {
    	Program program = record.createProgram(vmjExchange);
        Program programWithVolunteer = ProgramFactory.createProgram("aisco.program.withvolunteer.ProgramImpl", program);
        return programWithVolunteer;
    }
    
    public Program updateProgram(VMJExchange vmjExchange, int id) {
        Program program = programDao.getObject(id);
        program.setName((String) vmjExchange.getRequestBodyForm("name"));
        program.setDescription((String) vmjExchange.getRequestBodyForm("description"));
        program.setTarget((String) vmjExchange.getRequestBodyForm("target"));
        program.setPartner((String) vmjExchange.getRequestBodyForm("partner"));
        program.setLogoUrl((String) vmjExchange.getRequestBodyForm("logoUrl"));
        program.setExecutionDate((String) vmjExchange.getRequestBodyForm("executionDate"));
        return program;
    }

    //@Restricted(permissionName="ModifyProgramImpl")
    @Route(url="call/program-with-volunteer/update")
    public HashMap<String, Object> updateProgram(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);

        Program program = updateProgram(vmjExchange, id);
        programDao.updateObject(program);
        return program.toHashMap();
    }

    //@Route(url="call/program-with-volunteer/detail")
    public HashMap<String, Object> getProgram(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("id");
        // UUID id = UUID.fromString(idStr);
        int id = Integer.parseInt(idStr);
        Program program = programDao.getObject(id);
        System.out.println(program);
        return program.toHashMap();
    }

    @Route(url="call/program-with-volunteer/list")
    public List<HashMap<String,Object>> getAllProgram(VMJExchange vmjExchange) {
        List<Program> programList = programDao.getAllObject("program_with_volunteer");
        return transformProgramListToHashMap(programList);
    }

    public List<HashMap<String,Object>> transformProgramListToHashMap(List<Program> programList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < programList.size(); i++) {
            resultList.add(programList.get(i).toHashMap());
        }

        return resultList;
    }

    //@Restricted(permissionName="ModifyProgramImpl")
    @Route(url="call/program-with-volunteer/delete")
    public List<HashMap<String,Object>> deleteProgram(VMJExchange vmjExchange) {
        return record.deleteProgram(vmjExchange);
    }
}
