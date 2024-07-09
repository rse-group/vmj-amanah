package aisco.beneficiary.rumahbelajar;

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
import aisco.beneficiary.rumahbelajar.BeneficiaryImpl;
import aisco.program.core.*;

import vmj.hibernate.integrator.RepositoryUtil;
/
//import vmj.auth.core.AuthPayload;
//import vmj.auth.model.core.*;
//import vmj.auth.model.core.UserComponent;

public class BeneficiaryResourceImpl extends BeneficiaryResourceDecorator {
    public BeneficiaryResourceImpl (BeneficiaryResourceComponent record) {
        // to do implement the method
		super(record);
    }

    // @Restriced(permission = "")
    @Route(url="call/rumahbelajar/save")
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
		Beneficiary beneficiaryRumahBelajar = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.rumahbelajar.BeneficiaryImpl", beneficiary, education);
		return beneficiaryRumahBelajar;
	}

	public Beneficiary createBeneficiary(VMJExchange vmjExchange, String objectName) {
		Map<String, Object> payload = vmjExchange.getPayload();
		String education = (String) payload.get("education");

		Beneficiary beneficiary = record.createBeneficiary(vmjExchange, BeneficiaryImpl.class.getName());
		Beneficiary beneficiaryRumahBelajar = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.rumahbelajar.BeneficiaryImpl", beneficiary, education, objectName);
		return beneficiaryRumahBelajar;
	}

	public Beneficiary createBeneficiary(VMJExchange vmjExchange, UUID id) {
		Map<String, Object> payload = vmjExchange.getPayload();
		String education = (String) payload.get("education");

		Beneficiary savedBeneficiary = beneficiaryRepository.getObject(id);
		System.out.println(((BeneficiaryDecorator) savedBeneficiary).getRecord().getClass());
		UUID recordId = (((BeneficiaryDecorator) savedBeneficiary).getRecord()).getId();
		Beneficiary beneficiary = record.createBeneficiary(vmjExchange, recordId);
		Beneficiary beneficiaryRumahBelajar = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.rumahbelajar.BeneficiaryImpl", id, beneficiary, education, BeneficiaryImpl.class.getName());
		return beneficiaryRumahBelajar;
	}

	public Beneficiary createBeneficiary(VMJExchange vmjExchange, UUID id, String objectName) {
		Map<String, Object> payload = vmjExchange.getPayload();
		String education = (String) payload.get("education");

		Beneficiary savedBeneficiary = beneficiaryRepository.getObject(id);
		UUID recordId = (((BeneficiaryDecorator) savedBeneficiary).getRecord()).getId();
		Beneficiary beneficiary = record.createBeneficiary(vmjExchange, recordId);
		Beneficiary beneficiaryRumahSinggah = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.rumahbelajar.BeneficiaryImpl", id, beneficiary, education, objectName);

		return beneficiaryRumahSinggah;
	}

    // @Restriced(permission = "")
    @Route(url="call/rumahbelajar/update")
    public HashMap<String, Object> updateBeneficiary(VMJExchange vmjExchange){
		Map<String, Object> payload = vmjExchange.getPayload();
		String idStr = (String) payload.get("id");
		UUID id = UUID.fromString(idStr);

		Beneficiary beneficiary = beneficiaryRepository.getObject(id);
		beneficiary = createBeneficiary(vmjExchange, id);
		beneficiaryRepository.updateObject(beneficiary);
		return beneficiary.toHashMap();
		// to do implement the method
	}

	// @Restriced(permission = "")
    @Route(url="call/rumahbelajar/detail")
    public HashMap<String, Object> getBeneficiary(VMJExchange vmjExchange){
    	System.out.println("here");
    	return record.getBeneficiary(vmjExchange);
	}

	// @Restriced(permission = "")
    @Route(url="call/rumahbelajar/list")
    public List<HashMap<String,Object>> getAllBeneficiary(VMJExchange vmjExchange){
		List<Beneficiary> beneficiaryList = beneficiaryRepository.getAllObject("beneficiary_rumahbelajar");
		return record.transformBeneficiaryListToHashMap(beneficiaryList);
	}

	// @Restriced(permission = "")
    @Route(url="call/rumahbelajar/delete")
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
