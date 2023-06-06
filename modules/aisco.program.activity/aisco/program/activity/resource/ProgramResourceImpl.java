package aisco.program.activity;

import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.program.ProgramFactory;
import aisco.program.core.ProgramResourceComponent;
import aisco.program.core.Program;

import prices.auth.vmj.annotations.Restricted;

public class ProgramResourceImpl extends ProgramResourceComponent {

    @Restricted(permissionName="ModifyProgramImpl")
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
        Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("logoUrl");
        logoUrl = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
                + ";base64," + Base64.getEncoder().encodeToString(uploadedFile.get("content"));
        int fileSize = uploadedFile.get("content").length;
        if (fileSize > 4000000)
            throw new RuntimeException("File size limit exceeded");
        try {
            String type = URLConnection
                    .guessContentTypeFromStream(new ByteArrayInputStream(uploadedFile.get("content")));
            if (type == null || !type.startsWith("image"))
                throw new RuntimeException("File is not image");
        } catch (IOException e) {
            throw new RuntimeException("File not found");
        }
        String executionDate = (String) payload.get("executionDate");
        Program program = ProgramFactory.createProgram("aisco.program.activity.ProgramImpl", name, description, target, partner, logoUrl, executionDate);
        return program;
    }

    @Restricted(permissionName="ModifyProgramImpl")
    @Route(url="call/activity/update")
    public HashMap<String, Object> updateProgram(VMJExchange vmjExchange) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("id");
        int id = Integer.parseInt(idStr);
        Program program = programRepository.getObject(id);
        program = updateProgram(vmjExchange, id);
        programRepository.updateObject(program);
        return program.toHashMap();
    }

    public Program updateProgram(VMJExchange vmjExchange, int id) {
        Program program = programRepository.getObject(id);
        Map<String, Object> payload = vmjExchange.getPayload();
        String logoUrl = "";
        if (payload.get("logoUrl") instanceof String) {
         logoUrl = (String) payload.get("logoUrl");
        } else {
         Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("logoUrl");
            logoUrl = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
                    + ";base64," + Base64.getEncoder().encodeToString(uploadedFile.get("content"));
            int fileSize = uploadedFile.get("content").length;
            if (fileSize > 4000000)
                throw new RuntimeException("File size limit exceeded");
            try {
                String type = URLConnection
                        .guessContentTypeFromStream(new ByteArrayInputStream(uploadedFile.get("content")));
                if (type == null || !type.startsWith("image"))
                    throw new RuntimeException("File is not image");
            } catch (IOException e) {
                throw new RuntimeException("File not found");
            }
        }
        program.setName((String) payload.get("name"));
        program.setDescription((String) payload.get("description"));
        program.setTarget((String) payload.get("target"));
        program.setPartner((String) payload.get("partner"));
        program.setLogoUrl(logoUrl);
        program.setExecutionDate((String) payload.get("executionDate"));
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

    @Restricted(permissionName="ModifyProgramImpl")
    @Route(url="call/activity/delete")
    public List<HashMap<String,Object>> deleteProgram(VMJExchange vmjExchange) {
    	Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("id");
        int id = Integer.parseInt(idStr);
        programRepository.deleteObject(id);
        return getAllProgram(vmjExchange);
    }
}
