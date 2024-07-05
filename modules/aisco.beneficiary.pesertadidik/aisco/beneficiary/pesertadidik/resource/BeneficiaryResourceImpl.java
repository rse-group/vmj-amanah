package aisco.beneficiary.pesertadidik;

import java.util.*;
import java.lang.RuntimeException;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import aisco.beneficiary.BeneficiaryFactory;
import aisco.beneficiary.core.BeneficiaryResourceDecorator;
import aisco.beneficiary.core.BeneficiaryResourceComponent;
import aisco.beneficiary.core.Beneficiary;
import aisco.beneficiary.core.BeneficiaryDecorator;
import aisco.beneficiary.pesertadidik.BeneficiaryImpl;
import aisco.program.core.*;

import vmj.auth.core.AuthPayload;
import vmj.auth.model.core.*;
import vmj.auth.model.core.UserComponent;
import vmj.auth.annotations.Restricted;


public class BeneficiaryResourceImpl extends BeneficiaryResourceDecorator {
    public BeneficiaryResourceImpl (BeneficiaryResourceComponent record) {
        // to do implement the method
		super(record);
    }

    // @Restriced(permission = "")
    @Route(url="call/pesertadidik/save")
    public List<HashMap<String,Object>> saveBeneficiary(VMJExchange vmjExchange){
    	if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Beneficiary beneficiary = createBeneficiary(vmjExchange);
		beneficiaryRepository.saveObject(beneficiary);
		return getAllBeneficiary(vmjExchange);
	}

    public Beneficiary createBeneficiary(VMJExchange vmjExchange){
    	Map<String, Object> payload = vmjExchange.getPayload();
		String education = (String) payload.get("education");
		
		Beneficiary beneficiary = record.createBeneficiary(vmjExchange, BeneficiaryImpl.class.getName());
		Beneficiary beneficiaryPesertaDidik = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.pesertadidik.BeneficiaryImpl", beneficiary, education);
		return beneficiaryPesertaDidik;
	}
    
	public Beneficiary createBeneficiary(VMJExchange vmjExchange, String objectName) {
		Map<String, Object> payload = vmjExchange.getPayload();
		String education = (String) payload.get("education");

		Beneficiary beneficiary = record.createBeneficiary(vmjExchange, BeneficiaryImpl.class.getName());
		Beneficiary beneficiaryPesertaDidik = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.pesertadidik.BeneficiaryImpl", beneficiary, education, objectName);
		return beneficiaryPesertaDidik;
	}
	
	public Beneficiary createBeneficiary(VMJExchange vmjExchange, UUID id) {
		Map<String, Object> payload = vmjExchange.getPayload();
		String education = (String) payload.get("education");

		Beneficiary savedBeneficiary = beneficiaryRepository.getObject(id);
		System.out.println(((BeneficiaryDecorator) savedBeneficiary).getRecord().getClass());
		UUID recordId = (((BeneficiaryDecorator) savedBeneficiary).getRecord()).getId();
		Beneficiary beneficiary = record.createBeneficiary(vmjExchange, recordId);
		Beneficiary beneficiaryPesertaDidik = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.pesertadidik.BeneficiaryImpl", id, beneficiary, education, BeneficiaryImpl.class.getName());
		return beneficiaryPesertaDidik;
	}
	
	public Beneficiary createBeneficiary(VMJExchange vmjExchange, UUID id, String objectName) {
		Map<String, Object> payload = vmjExchange.getPayload();
		String education = (String) payload.get("education");

		Beneficiary savedBeneficiary = beneficiaryRepository.getObject(id);
		UUID recordId = (((BeneficiaryDecorator) savedBeneficiary).getRecord()).getId();
		Beneficiary beneficiary = record.createBeneficiary(vmjExchange, recordId);
		Beneficiary beneficiaryPesertaDidik = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.pesertadidik.BeneficiaryImpl", id, beneficiary, education, objectName);

		return beneficiaryPesertaDidik;
	}

    // @Restriced(permission = "")
    @Route(url="call/pesertadidik/update")
    public HashMap<String, Object> updateBeneficiary(VMJExchange vmjExchange){
		// to do implement the method
		Map<String, Object> payload = vmjExchange.getPayload();
		String idStr = (String) payload.get("id");
		UUID id = UUID.fromString(idStr);

		Beneficiary beneficiary = beneficiaryRepository.getObject(id);
		beneficiary = createBeneficiary(vmjExchange, id);
		beneficiaryRepository.updateObject(beneficiary);
		return beneficiary.toHashMap();
	}

	@Restricted(permissionName = "ReadPesertaDidik")
    @Route(url="call/pesertadidik/detail")
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

	@Restricted(permissionName = "ReadPesertaDidik")
    @Route(url="call/pesertadidik/list")
    public List<HashMap<String,Object>> getAllBeneficiary(VMJExchange vmjExchange){
		List<Beneficiary> beneficiaryList = beneficiaryRepository.getAllObject("beneficiary_pesertadidik");
		return record.transformBeneficiaryListToHashMap(beneficiaryList);
	}

	@Restricted(permissionName = "DeletePesertaDidik")
    @Route(url="call/pesertadidik/delete")
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
