package aisco.program.activity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.*;
import java.lang.RuntimeException;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import aisco.program.ProgramFactory;
import aisco.program.core.ProgramResourceComponent;
import aisco.program.core.Program;
//import aisco.financialreport.core.*;

import vmj.auth.annotations.Restricted;

public class ProgramResourceImpl extends ProgramResourceComponent {

//	@Restricted(permissionName="CreateProgram")
    @Route(url="call/activity/save")
    public List<HashMap<String,Object>> saveProgram(VMJExchange vmjExchange) {
        Program program = createProgram(vmjExchange);
        programRepository.saveObject(program);
        System.out.println(program);
        return getAllProgram(vmjExchange);
    }

    public Program createProgram(VMJExchange vmjExchange) {
    	Map<String, Object> payload = vmjExchange.getPayload();
   
    	String name = (String) payload.get("name");
        String description = (String) payload.get("description");
        String target = (String) payload.get("target");
        String partner = (String) payload.get("partner");
        String logoUrl = "";
        String executionDate = (String) payload.get("executionDate");
        
        Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("logoUrl");
        logoUrl = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
                + ";base64," + Base64.getEncoder().encodeToString(uploadedFile.get("content"));
        int fileSize = uploadedFile.get("content").length;
        if (fileSize > 4000000)
            throw new FileSizeException(4.0, ((double) fileSize) / 1000000, "megabyte");
        try {
            String type = URLConnection
                    .guessContentTypeFromStream(new ByteArrayInputStream(uploadedFile.get("content")));
            if (type == null || !type.startsWith("image"))
                throw new FileTypeException("image");
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
//        if (logoUrl.length() > 255) {
//        	logoUrl = logoUrl.substring(0, Math.min(logoUrl.length(), 255));
//        }
        
//        String executionDate = (String) vmjExchange.getRequestBodyForm("executionDate");
//        String name = (String) vmjExchange.getRequestBodyForm("name");
//        String description = (String) vmjExchange.getRequestBodyForm("description");
//        String target = (String) vmjExchange.getRequestBodyForm("target");
//        String partner = (String) vmjExchange.getRequestBodyForm("partner");
//        String logoUrl = (String) vmjExchange.getRequestBodyForm("logoUrl");
//        String executionDate = (String) vmjExchange.getRequestBodyForm("executionDate");
        Program program = ProgramFactory.createProgram("aisco.program.activity.ProgramImpl", name, description, target, partner, logoUrl, executionDate);
        return program;
    }

    @Restricted(permissionName="UpdateProgram")
    @Route(url="call/activity/update")
    public HashMap<String, Object> updateProgram(VMJExchange vmjExchange) {
    	Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("id");
        UUID id = UUID.fromString(idStr);
        Program program = programRepository.getObject(id);
        program = updateProgram(vmjExchange, id);
        programRepository.updateObject(program);
        return program.toHashMap();
    }
    
    public Program updateProgram(VMJExchange vmjExchange, UUID id) {
        String logoUrl = "";
        Program program = programRepository.getObject(id);
        Map<String, Object> payload = vmjExchange.getPayload();
        System.out.println(payload);
        program.setName((String) payload.get("name"));
        program.setDescription((String) payload.get("description"));
        program.setTarget((String) payload.get("target"));
        program.setPartner((String) payload.get("partner"));
        
        if (payload.get("logoUrl") instanceof String) {
        	program.setLogoUrl((String)payload.get("logoUrl"));
        } else {
        	Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("logoUrl");
            logoUrl = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
                    + ";base64," + Base64.getEncoder().encodeToString(uploadedFile.get("content"));
            int fileSize = uploadedFile.get("content").length;
            if (fileSize > 4000000)
                throw new FileSizeException(4.0, ((double) fileSize) / 1000000, "megabyte");
            try {
                String type = URLConnection
                        .guessContentTypeFromStream(new ByteArrayInputStream(uploadedFile.get("content")));
                if (type == null || !type.startsWith("image"))
                    throw new FileTypeException("image");
            } catch (IOException e) {
                throw new FileNotFoundException();
            }
            program.setLogoUrl(logoUrl);
        }
        program.setExecutionDate((String) payload.get("executionDate"));
   
        return program;
    }

//    public Program updateProgram(VMJExchange vmjExchange, UUID id) {
//        Program program = programRepository.getObject(id);
//        Map<String, Object> payload = vmjExchange.getPayload();
//        System.out.println(payload);
//        program.setName((String) payload.get("name"));
//        program.setDescription((String) payload.get("description"));
//        program.setTarget((String) payload.get("target"));
//        program.setPartner((String) payload.get("partner"));
//        program.setLogoUrl((String) payload.get("logoUrl"));
//        program.setExecutionDate((String) payload.get("executionDate"));
//   
//        return program;
//    }

    @Route(url="call/activity/detail")
    public HashMap<String, Object> getProgram(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("id");
        UUID id = UUID.fromString(idStr);
        Program program = programRepository.getObject(id);
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
        UUID id = UUID.fromString(idStr);
        programRepository.deleteObject(id);
        return getAllProgram(vmjExchange);
    }
}
