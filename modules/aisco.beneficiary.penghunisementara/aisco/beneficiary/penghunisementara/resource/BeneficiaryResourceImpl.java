package aisco.beneficiary.penghunisementara;

import aisco.beneficiary.core.BeneficiaryResourceDecorator;

import aisco.beneficiary.BeneficiaryFactory;
import aisco.beneficiary.core.BeneficiaryResourceComponent;
import aisco.beneficiary.core.Beneficiary;
import aisco.beneficiary.core.BeneficiaryDecorator;
import aisco.beneficiary.penghunisementara.BeneficiaryImpl;
import aisco.program.core.*;

import java.util.*;
import java.lang.RuntimeException;
import java.net.URLConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import vmj.auth.core.AuthPayload;
import vmj.auth.model.core.*;
import vmj.auth.model.core.UserComponent;
import vmj.auth.annotations.Restricted;


public class BeneficiaryResourceImpl extends BeneficiaryResourceDecorator {
    public BeneficiaryResourceImpl (BeneficiaryResourceComponent record) {
    	super(record);
        // to do implement the method
    }

//    @Restricted(permissionName = "")
    @Route(url="call/penghunisementara/save")
    public List<HashMap<String,Object>> saveBeneficiary(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Beneficiary beneficiary  = createBeneficiary(vmjExchange);
		beneficiaryRepository.saveObject(beneficiary);
		return getAllBeneficiary(vmjExchange);
	}
    
    public Beneficiary createBeneficiary(VMJExchange vmjExchange){
    	Map<String, Object> payload = vmjExchange.getPayload();
		String checkInDate = (String) payload.get("checkInDate");
		String checkOutDate = (String) payload.get("checkOutDate");
		String stayReason = (String) payload.get("stayReason");
		
		Beneficiary beneficiary = record.createBeneficiary(vmjExchange, BeneficiaryImpl.class.getName());;
		Beneficiary beneficiaryPenghuniSementara = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.penghunisementara.BeneficiaryImpl", beneficiary, checkInDate, checkOutDate, stayReason);
		return beneficiaryPenghuniSementara;
	}
    
	public Beneficiary createBeneficiary(VMJExchange vmjExchange, String objectName) {
		Map<String, Object> payload = vmjExchange.getPayload();
		String checkInDate = (String) payload.get("checkInDate");
		String checkOutDate = (String) payload.get("checkOutDate");
		String stayReason = (String) payload.get("stayReason");

		Beneficiary beneficiary = record.createBeneficiary(vmjExchange, BeneficiaryImpl.class.getName());
		Beneficiary beneficiaryPenghuniSementara = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.penghunisementara.BeneficiaryImpl", beneficiary, checkInDate, checkOutDate, stayReason, objectName);

		return beneficiaryPenghuniSementara;
	}
	
	public Beneficiary createBeneficiary(VMJExchange vmjExchange, UUID id) {
		Map<String, Object> payload = vmjExchange.getPayload();
		String checkInDate = (String) payload.get("checkInDate");
		String checkOutDate = (String) payload.get("checkOutDate");
		String stayReason = (String) payload.get("stayReason");

		Beneficiary savedBeneficiary = beneficiaryRepository.getObject(id);
		System.out.println(savedBeneficiary.getClass());
		UUID recordId = (((BeneficiaryDecorator) savedBeneficiary).getRecord()).getId();
		System.out.println(recordId);
		Beneficiary beneficiary = record.createBeneficiary(vmjExchange, recordId);
		System.out.println("here");
		Beneficiary beneficiaryPenghuniSementara = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.penghunisementara.BeneficiaryImpl",id, beneficiary, checkInDate, checkOutDate, stayReason, BeneficiaryImpl.class.getName());

		return beneficiaryPenghuniSementara;
	}
	
	public Beneficiary createBeneficiary(VMJExchange vmjExchange, UUID id, String objectName) {
		Map<String, Object> payload = vmjExchange.getPayload();
		String checkInDate = (String) payload.get("checkInDate");
		String checkOutDate = (String) payload.get("checkOutDate");
		String stayReason = (String) payload.get("stayReason");

		Beneficiary savedBeneficiary = beneficiaryRepository.getObject(id);
		UUID recordId = (((BeneficiaryDecorator) savedBeneficiary).getRecord()).getId();
		Beneficiary beneficiary = record.createBeneficiary(vmjExchange, recordId);
		Beneficiary beneficiaryPenghuniSementara = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.penghunisementara.BeneficiaryImpl",id,  beneficiary, checkInDate, checkOutDate, stayReason, objectName);

		return beneficiaryPenghuniSementara;
	}

    // @Restricted(permission = "")
    @Route(url="call/penghunisementara/update")
    public HashMap<String, Object> updateBeneficiary(VMJExchange vmjExchange){
		// to do implement the method
    	Map<String, Object> payload = vmjExchange.getPayload();
    	String idStr = (String) payload.get("id");
    	UUID id = UUID.fromString(idStr);
    	
		Beneficiary beneficiary = createBeneficiary(vmjExchange, id);
		beneficiaryRepository.updateObject(beneficiary);
		return beneficiary.toHashMap();
	}

	@Restricted(permissionName = "ReadPenghuniSementara")
    @Route(url="call/penghunisementara/detail")
    public HashMap<String, Object> getBeneficiary(VMJExchange vmjExchange){
		String idStr = vmjExchange.getGETParam("id");
		UUID id = UUID.fromString(idStr);
		Beneficiary beneficiary = beneficiaryRepository.getObject(id);
		try {
			return ((BeneficiaryImpl) beneficiary).toHashMap();
		} catch (NullPointerException e) {
			HashMap<String, Object> blank = new HashMap<>();
			blank.put("error", "Missing Params");
			return blank;
		}
	}

	@Restricted(permissionName = "ReadPenghuniSementara")
    @Route(url="call/penghunisementara/list")
    public List<HashMap<String,Object>> getAllBeneficiary(VMJExchange vmjExchange){
		List<Beneficiary> beneficiaryList = beneficiaryRepository.getAllObject("beneficiary_penghunisementara");
		return transformBeneficiaryListToHashMap(beneficiaryList);
	}

    public List<HashMap<String,Object>> transformBeneficiaryListToHashMap(List<Beneficiary> BeneficiaryList){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < BeneficiaryList.size(); i++) {
            resultList.add(BeneficiaryList.get(i).toHashMap());
        }

        return resultList;
	}

	@Restricted(permissionName = "DeletePenghuniSementara")
    @Route(url="call/penghunisementara/delete")
    public List<HashMap<String,Object>> deleteBeneficiary(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		
		Map<String, Object> payload = vmjExchange.getPayload();
		String idStr = (String) payload.get("id");
    	UUID id = UUID.fromString(idStr);
		beneficiaryRepository.deleteObject(id);
		return getAllBeneficiary(vmjExchange);
	}

}
