package aisco.programreport.core;
import java.util.*;
import java.lang.RuntimeException;
import java.net.URLConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import prices.auth.core.AuthPayload;
import prices.auth.core.StorageStrategy;
import prices.auth.vmj.storagestrategy.HibernateStrategy;
import prices.auth.vmj.storagestrategy.PropertiesStrategy;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;
import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import aisco.programreport.ProgramReportFactory;
import prices.auth.vmj.annotations.Restricted;
import aisco.program.core.*;

import java.text.SimpleDateFormat;  

public class ProgramReportResourceImpl extends ProgramReportResourceComponent{

	@Restricted(permissionName="CreateProgramReport")
    @Route(url="call/programreport/save")
    public List<HashMap<String,Object>> saveProgramReport(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		ProgramReport programReport = createProgramReport(vmjExchange);
		programReportRepository.saveObject(programReport);
		return getAllProgramReport(vmjExchange);
	}

    public ProgramReport createProgramReport (VMJExchange vmjExchange){
		UUID id = UUID.randomUUID();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
    	Date date = new Date(); 
		String createdAt = formatter.format(date); 
		return createProgramReport(vmjExchange, id, createdAt);
	}

    public ProgramReport createProgramReport(VMJExchange vmjExchange, UUID id, String createdAt){
    	Map<String, Object> payload = vmjExchange.getPayload();
		String title = (String) payload.get("title");
		String description = (String) payload.get("description");
		String image = "";
		Program program = null;
		String idProgramStr = (String) payload.get("id");
        if (idProgramStr != null) {
            UUID idProgram = UUID.fromString(idProgramStr);
            program = programReportRepository.getProxyObject(aisco.program.core.ProgramComponent.class, idProgram);
        }
		Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("image");
        image = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
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

		ProgramReport programReport = ProgramReportFactory.createProgramReport("aisco.programreport.core.ProgramReportImpl", id, title, description, image, createdAt, program);
			return programReport;
	}

    @Restricted(permissionName="UpdateProgramReport")
    @Route(url="call/programreport/update")
    public HashMap<String, Object> updateProgramReport(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
    	Map<String, Object> payload = vmjExchange.getPayload();
		String idStr = (String) payload.get("id");
		UUID id = UUID.fromString(idStr);
		ProgramReport programReport = programReportRepository.getObject(id);
		
		programReport = updateProgramReport(vmjExchange, id);
		programReportRepository.updateObject(programReport);
		
		return programReport.toHashMap();	
	}

	public ProgramReport updateProgramReport(VMJExchange vmjExchange, UUID id) {
        ProgramReport programReport = programReportRepository.getObject(id);
		Map<String, Object> payload = vmjExchange.getPayload();
        programReport.setTitle((String) payload.get("title"));
        programReport.setDescription((String) payload.get("description"));
		String image = "";

		Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("image");
        image = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
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
		programReport.setImage(image);
        return programReport;
    }

    @Route(url="call/programreport/detail")
    public HashMap<String, Object> getProgramReport(VMJExchange vmjExchange){
		String idStr = vmjExchange.getGETParam("id"); 
		UUID id = UUID.fromString(idStr);
		ProgramReport programReport = programReportRepository.getObject(id);
		return programReport.toHashMap();
	}

    @Route(url="call/programreport/list-all")
    public List<HashMap<String,Object>> getAllProgramReport(VMJExchange vmjExchange){
		List<ProgramReport> programReportList = programReportRepository.getAllObject("programreport_impl");
		return transformProgramReportListToHashMap(programReportList);
	}

	@Route(url="call/programreport/list")
    public List<HashMap<String,Object>> getAllProgramReportByProgramId(VMJExchange vmjExchange){
		Map<String, Object> payload = vmjExchange.getPayload();
		String idStr = vmjExchange.getGETParam("id"); 
		UUID idProgram = UUID.fromString(idStr);
		List<ProgramReport> programReportList = programReportRepository.getListObject("programreport_impl", "program_idprogram", idProgram);
		return transformProgramReportListToHashMap(programReportList);
	}

    public List<HashMap<String,Object>> transformProgramReportListToHashMap(List<ProgramReport> List){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < List.size(); i++) {
            resultList.add(List.get(i).toHashMap());
        }

        return resultList;
	}

	@Restricted(permissionName="DeleteProgramReport")
    @Route(url="call/programreport/delete")
    public List<HashMap<String,Object>> deleteProgramReport(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
    	Map<String, Object> payload = vmjExchange.getPayload();
		String idStr = (String) payload.get("id");
		UUID id = UUID.fromString(idStr);
		programReportRepository.deleteObject(id);
		return getAllProgramReport(vmjExchange);
	}

}
