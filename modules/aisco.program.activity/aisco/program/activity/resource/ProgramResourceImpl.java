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

	@Restricted(permissionName="CreateProgram")
    @Route(url="call/activity/save")
    public List<HashMap<String,Object>> saveProgram(VMJExchange vmjExchange) {
        Program program = createProgram(vmjExchange);
        programRepository.saveObject(program);
        System.out.println(program);
        return getAllProgram(vmjExchange);
    }

    public Program createProgram(VMJExchange vmjExchange) {
    	Map<String, Object> payload = vmjExchange.getPayload();
   
    	String nameProgram = (String) payload.get("nameProgram");
        String descriptionProgram = (String) payload.get("descriptionProgram");
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
        Program program = ProgramFactory.createProgram("aisco.program.activity.ProgramImpl", nameProgram, descriptionProgram, target, partner, logoUrl, executionDate);
        return program;
    }

    @Restricted(permissionName="UpdateProgram")
    @Route(url="call/activity/update")
    public HashMap<String, Object> updateProgram(VMJExchange vmjExchange) {
    	Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("idProgram");
        UUID idProgram = UUID.fromString(idStr);
        Program program = programRepository.getObject(idProgram);
        program = updateProgram(vmjExchange, idProgram);
        programRepository.updateObject(program);
        return program.toHashMap();
    }
    
    public Program updateProgram(VMJExchange vmjExchange, UUID idProgram) {
        String logoUrl = "";
        Program program = programRepository.getObject(idProgram);
        Map<String, Object> payload = vmjExchange.getPayload();
        System.out.println(payload);
        program.setNameProgram((String) payload.get("nameProgram"));
        program.setDescriptionProgram((String) payload.get("descriptionProgram"));
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

    @Route(url="call/activity/detail")
    public HashMap<String, Object> getProgram(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("idProgram");
        UUID idProgram = UUID.fromString(idStr);
        Program program = programRepository.getObject(idProgram);
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
        String idStr = (String) vmjExchange.getRequestBodyForm("idProgram");
        UUID idProgram = UUID.fromString(idStr);
        programRepository.deleteObject(idProgram);
        return getAllProgram(vmjExchange);
    }
}
