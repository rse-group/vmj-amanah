package aisco.beneficiary.core;
import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class BeneficiaryResourceDecorator extends BeneficiaryResourceComponent{
	protected BeneficiaryResourceComponent record;

    public BeneficiaryResourceDecorator(BeneficiaryResourceComponent record) {
        this.record = record;
    }

    public List<HashMap<String,Object>> saveBeneficiary(VMJExchange vmjExchange){
		return record.saveBeneficiary(vmjExchange);
	}

    public Beneficiary createBeneficiary(VMJExchange vmjExchange){
		return record.createBeneficiary(vmjExchange);
	}

    public Beneficiary createBeneficiary(VMJExchange vmjExchange, UUID id){
		return record.createBeneficiary(vmjExchange, id);
	}

    public HashMap<String, Object> updateBeneficiary(VMJExchange vmjExchange){
		return record.updateBeneficiary(vmjExchange);
	}

    public HashMap<String, Object> getBeneficiary(VMJExchange vmjExchange){
		return record.getBeneficiary(vmjExchange);
	}

    public List<HashMap<String,Object>> getAllBeneficiary(VMJExchange vmjExchange){
		return record.getAllBeneficiary(vmjExchange);
	}

    public List<HashMap<String,Object>> transformBeneficiaryListToHashMap(List<Beneficiary> beneficiaryList){
		return record.transformBeneficiaryListToHashMap(beneficiaryList);
	}

    public List<HashMap<String,Object>> deleteBeneficiary(VMJExchange vmjExchange){
		return record.deleteBeneficiary(vmjExchange);
	}

}
