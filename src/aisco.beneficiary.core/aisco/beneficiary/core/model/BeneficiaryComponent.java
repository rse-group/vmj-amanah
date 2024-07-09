package aisco.beneficiary.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="beneficiary_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BeneficiaryComponent implements Beneficiary{
	@Id
	protected UUID id; 
	protected String fullName;
	protected String address;
	protected String email;
	protected String brith;
	protected String gender;
	protected String Phone;
	protected int allowance;
	protected String objectName = BeneficiaryImpl.class.getName();

	public BeneficiaryComponent() {

	} 

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getBrith() {
		return this.brith;
	}

	public void setBrith(String brith) {
		this.brith = brith;
	}
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return this.Phone;
	}

	public void setPhone(String Phone) {
		this.Phone = Phone;
	}
	public int getAllowance() {
		return this.allowance;
	}

	public void setAllowance(int allowance) {
		this.allowance = allowance;
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
