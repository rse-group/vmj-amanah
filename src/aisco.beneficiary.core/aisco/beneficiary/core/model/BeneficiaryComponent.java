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
	protected String birth;
	protected String gender;
	protected String phone;
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
	public String getBirth() {
		return this.birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
            " birth='" + getBirth() + "'" +
            " gender='" + getGender() + "'" +
            " phone='" + getPhone() + "'" +
            " allowance='" + getAllowance() + "'" +
            "}";
    }
	
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> beneficiaryMap = new HashMap<String,Object>();
		beneficiaryMap.put("id",getId());
		beneficiaryMap.put("fullName",getFullName());
		beneficiaryMap.put("address",getAddress());
		beneficiaryMap.put("email",getEmail());
		beneficiaryMap.put("birth",getBirth());
		beneficiaryMap.put("gender",getGender());
		beneficiaryMap.put("phone",getPhone());
		beneficiaryMap.put("allowance",getAllowance());

        return beneficiaryMap;
    }
}
