package aisco.beneficiary.pesertadidik;

import aisco.beneficiary.core.BeneficiaryDecorator;

import java.util.*;

import aisco.beneficiary.core.Beneficiary;
import aisco.beneficiary.core.BeneficiaryComponent;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="beneficiary_pesertadidik")
@Table(name="beneficiary_pesertadidik")
public class BeneficiaryImpl extends BeneficiaryDecorator {

	protected String education;
	
	public BeneficiaryImpl() {
		super();
		this.education = "";
		this.objectName = BeneficiaryImpl.class.getName();
	} 
	
	public BeneficiaryImpl(BeneficiaryComponent record, String education) {
		super(record, BeneficiaryImpl.class.getName());
		this.education = education;
		this.objectName = BeneficiaryImpl.class.getName();
	}
	
	public BeneficiaryImpl(UUID id, BeneficiaryComponent record, String education) {
		super(id, record);
		this.education = education;
		this.objectName = BeneficiaryImpl.class.getName();
	}
	
	public BeneficiaryImpl(UUID id, BeneficiaryComponent record, String education, String objectName) {
		super(id, record);
		this.education = education;
		this.objectName = objectName;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> beneficiaryMap = new HashMap<String,Object>();
		beneficiaryMap.put("id", this.id);
		beneficiaryMap.put("fullName",record.getFullName());
		beneficiaryMap.put("address",record.getAddress());
		beneficiaryMap.put("email",record.getEmail());
		beneficiaryMap.put("birth",record.getBirth());
		beneficiaryMap.put("gender",record.getGender());
		beneficiaryMap.put("phone",record.getPhone());
		beneficiaryMap.put("allowance",record.getAllowance());
		beneficiaryMap.put("education",getEducation());

        return beneficiaryMap;
    }


}
