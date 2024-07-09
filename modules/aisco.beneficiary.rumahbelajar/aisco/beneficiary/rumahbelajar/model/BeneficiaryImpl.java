package aisco.beneficiary.rumahbelajar;

import java.util.*;

import aisco.beneficiary.core.BeneficiaryDecorator;
import aisco.beneficiary.core.Beneficiary;
import aisco.beneficiary.core.BeneficiaryComponent;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="beneficiary_rumahbelajar")
@Table(name="beneficiary_rumahbelajar")
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
	
	
	@Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            " fullName='" + getFullName() + "'" +
            " address='" + getAddress() + "'" +
            " email='" + getEmail() + "'" +
            " brith='" + getBrith() + "'" +
            " gender='" + getGender() + "'" +
            " Phone='" + getPhone() + "'" +
            " allowance='" + getAllowance() + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> beneficiaryMap = new HashMap<String,Object>();
		beneficiaryMap.put("id",getId());
		beneficiaryMap.put("fullName",getFullName());
		beneficiaryMap.put("address",getAddress());
		beneficiaryMap.put("email",getEmail());
		beneficiaryMap.put("brith",getBrith());
		beneficiaryMap.put("gender",getGender());
		beneficiaryMap.put("Phone",getPhone());
		beneficiaryMap.put("allowance",getAllowance());

        return beneficiaryMap;
    }

}
