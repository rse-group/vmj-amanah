package aisco.beneficiary.core;
import java.util.*;

import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.VMJExchange;
//add other required packages

public abstract class BeneficiaryResourceComponent implements BeneficiaryResource{
	protected RepositoryUtil<Beneficiary> beneficiaryRepository;

    public BeneficiaryResourceComponent(){
        this.beneficiaryRepository = new RepositoryUtil<Beneficiary>(aisco.beneficiary.core.BeneficiaryComponent.class);
    }	

    public abstract List<HashMap<String,Object>> saveBeneficiary(VMJExchange vmjExchange);
    public abstract Beneficiary createBeneficiary(VMJExchange vmjExchange);
	public abstract Beneficiary createBeneficiary(VMJExchange vmjExchange, UUID id);   
    public abstract Beneficiary createBeneficiary(VMJExchange vmjExchange, String objectName); 
    public abstract Beneficiary createBeneficiary(VMJExchange vmjExchange, UUID id, String objectName);
	public abstract HashMap<String, Object> updateBeneficiary(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> getBeneficiary(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllBeneficiary(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformBeneficiaryListToHashMap(List<Beneficiary> BeneficiaryList);
    public abstract List<HashMap<String,Object>> deleteBeneficiary(VMJExchange vmjExchange);

}
