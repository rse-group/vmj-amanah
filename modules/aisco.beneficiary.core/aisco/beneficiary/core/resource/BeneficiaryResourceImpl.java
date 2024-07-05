package aisco.beneficiary.core;
import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import aisco.beneficiary.BeneficiaryFactory;
import vmj.auth.annotations.Restricted;
//add other required packages

public class BeneficiaryResourceImpl extends BeneficiaryResourceComponent{

	// @Restriced(permission = "")
    @Route(url="call/beneficiary/save")
    public List<HashMap<String,Object>> saveBeneficiary(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Beneficiary beneficiary = createBeneficiary(vmjExchange);
			beneficiaryRepository.saveObject(beneficiary);
		return getAllBeneficiary(vmjExchange);
	}

    public Beneficiary createBeneficiary(VMJExchange vmjExchange){
			return createBeneficiary(vmjExchange, UUID.randomUUID());
	}

	public Beneficiary createBeneficiary(VMJExchange vmjExchange, UUID id) {
		Map<String, Object> payload = vmjExchange.getPayload();
		String fullName = (String) payload.get("fullName");
		String address = (String) payload.get("address");
		String email = (String) payload.get("email");
		String birth = (String) payload.get("birth");
		String gender = (String) payload.get("gender");
		String phone = (String) payload.get("phone");
		String allowanceStr = (String) payload.get("allowance");
		int allowance = Integer.parseInt(allowanceStr);
		
		//to do: fix association attributes
		System.out.println("success mapping data");
		System.out.println("birth " + birth);
		System.out.println("phone " + phone);
		System.out.println("gender" + gender);
		Beneficiary beneficiary = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.core.BeneficiaryImpl", id, fullName, address, email, birth, gender, phone, allowance);
			return beneficiary;
	}

		public Beneficiary createBeneficiary(VMJExchange vmjExchange, String objectName) {
			return createBeneficiary(vmjExchange, UUID.randomUUID(), objectName);
		}

    public Beneficiary createBeneficiary(VMJExchange vmjExchange, UUID id, String objectName){
		Map<String, Object> payload = vmjExchange.getPayload();
		String fullName = (String) payload.get("fullName");
		String address = (String) payload.get("address");
		String email = (String) payload.get("email");
		String birth = (String) payload.get("birth");
		String gender = (String) payload.get("gender");
		String phone = (String) payload.get("phone");
		String allowanceStr = (String) payload.get("allowance");
		int allowance = Integer.parseInt(allowanceStr);
		
		//to do: fix association attributes
		
		Beneficiary beneficiary = BeneficiaryFactory.createBeneficiary("aisco.beneficiary.core.BeneficiaryImpl", id, fullName, address, email, birth, gender, phone, allowance, objectName);
			return beneficiary;
	}

    // @Restriced(permission = "")
    @Route(url="call/beneficiary/update")
    public HashMap<String, Object> updateBeneficiary(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Map<String, Object> payload = vmjExchange.getPayload();

		String idStr = (String) payload.get("id");
		UUID id = UUID.fromString(idStr);
		
		Beneficiary beneficiary = beneficiaryRepository.getObject(id);
		beneficiary = createBeneficiary(vmjExchange, id);
		
		beneficiaryRepository.updateObject(beneficiary);
		//to do: fix association attributes
		
		return beneficiary.toHashMap();
		
	}

	// @Restriced(permission = "")
    @Route(url="call/beneficiary/detail")
    public HashMap<String, Object> getBeneficiary(VMJExchange vmjExchange){
		Map<String, Object> payload = vmjExchange.getPayload();
		String idStr = (String) payload.get("id");
		UUID id = UUID.fromString(idStr);
		Beneficiary beneficiary = beneficiaryRepository.getObject(id);
		try {
			System.out.println(beneficiary);
			return beneficiary.toHashMap();
		} catch (NullPointerException e) {
			HashMap<String, Object> blank = new HashMap<>();
			blank.put("error", "Missing Params");
			return blank;
		}
	}

	// @Restriced(permission = "")
    @Route(url="call/beneficiary/list")
    public List<HashMap<String,Object>> getAllBeneficiary(VMJExchange vmjExchange){
    	List<Beneficiary> beneficiaryList = beneficiaryRepository.getAllObject("beneficiary_impl", BeneficiaryImpl.class.getName());
		return transformBeneficiaryListToHashMap(beneficiaryList);
	}

    public List<HashMap<String,Object>> transformBeneficiaryListToHashMap(List<Beneficiary> beneficiaryList){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < beneficiaryList.size(); i++) {
            resultList.add(beneficiaryList.get(i).toHashMap());
        }

        return resultList;
	}

	// @Restriced(permission = "")
    @Route(url="call/beneficiary/delete")
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
