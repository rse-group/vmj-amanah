package aisco.program.goodsprogram;

import aisco.program.core.ProgramResourceDecorator;
import aisco.program.core.ProgramImpl;
import aisco.program.core.ProgramResourceComponent;
import aisco.program.ProgramFactory;
import aisco.program.core.Program;
import aisco.program.core.ProgramDecorator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.*;
import java.lang.RuntimeException;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import vmj.auth.annotations.Restricted;

public class ProgramResourceImpl extends ProgramResourceComponent {

    @Restricted(permissionName="CreateGoodsProgram")
    @Route(url="call/goodsprogram/save")
    public List<HashMap<String,Object>> saveProgram(VMJExchange vmjExchange){
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Program program = createProgram(vmjExchange);
		programRepository.saveObject(program);
		return getAllProgram(vmjExchange);
	}

    public Program createProgram(VMJExchange vmjExchange){
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
		String goodsName = (String) payload.get("goodsName");
		String unit = (String) payload.get("unit");
		
		Program program = ProgramFactory.createProgram("aisco.program.core.ProgramImpl", name, description, target, partner, logoUrl, executionDate);
    	Program programGoodsProgram = ProgramFactory.createProgram("aisco.program.goodsprogram.ProgramImpl", program, goodsName, unit);
	    return programGoodsProgram;
	}
    
    public Program createProgram(VMJExchange vmjExchange, UUID id){
    	Program savedProgram = programRepository.getObject(id);
    	UUID recordProgramId = (((ProgramDecorator) savedProgram).getRecord()).getIdProgram();
    	
    	Map<String, Object> payload = vmjExchange.getPayload();
    	String name = (String) payload.get("name");
        String description = (String) payload.get("description");
        String target = (String) payload.get("target");
        String partner = (String) payload.get("partner");
        String executionDate = (String) payload.get("executionDate");
        
        String logoUrl = "";
        
        try {
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
        } catch (ClassCastException e) {
        	 logoUrl = (String) payload.get("logoUrl");
        }
         

//        String logoUrl = (String) payload.get("logoUrl");
		String goodsName = (String) payload.get("goodsName");
		String unit = (String) payload.get("unit");
		
		Program program = ProgramFactory.createProgram("aisco.program.core.ProgramImpl", recordProgramId, name, description, target, partner, logoUrl, executionDate);
        Program programGoodsProgram = ProgramFactory.createProgram("aisco.program.goodsprogram.ProgramImpl", id, program, goodsName, unit);
	    return programGoodsProgram;
	}

    @Restricted(permissionName="UpdateGoodsProgram")
    @Route(url="call/goodsprogram/update")
    public HashMap<String, Object> updateProgram(VMJExchange vmjExchange){
		// to do implement the method
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

	// @Restriced(permission = "")
    @Route(url="call/goodsprogram/detail")
    public HashMap<String, Object> getProgram(VMJExchange vmjExchange){
    	String idStr = vmjExchange.getGETParam("id");
        UUID id = UUID.fromString(idStr);
        Program program = programRepository.getObject(id);
        return program.toHashMap();
	}

	// @Restriced(permission = "")
    @Route(url="call/goodsprogram/list")
    public List<HashMap<String,Object>> getAllProgram(VMJExchange vmjExchange){
		List<Program> programList = programRepository.getAllObject("program_goodsprogram");
		return transformProgramListToHashMap(programList);
	}
    
    public List<HashMap<String,Object>> transformProgramListToHashMap(List<Program> programList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < programList.size(); i++) {
            resultList.add(programList.get(i).toHashMap());
        }

        return resultList;
    }

    @Restricted(permissionName="DeleteGoodsProgram")
    @Route(url="call/goodsprogram/delete")
    public List<HashMap<String,Object>> deleteProgram(VMJExchange vmjExchange){
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
