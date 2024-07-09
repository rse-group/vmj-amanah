package aisco.beneficiary.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.CascadeType;
//add other required packages

@MappedSuperclass
public abstract class BeneficiaryDecorator extends BeneficiaryComponent{
	@OneToOne(cascade=CascadeType.ALL)
	public BeneficiaryComponent record;
		
	public BeneficiaryDecorator (BeneficiaryComponent record, String objectName) {
		this.record = record;
		this.id = UUID.randomUUID();
		this.record.objectName = objectName;
	}

	public BeneficiaryDecorator (UUID id, BeneficiaryComponent record) {
		this.id =  id;
		this.record = record;
	}
	
	public BeneficiaryDecorator(){
		super();
		this.record = new BeneficiaryImpl();
		this.id =  UUID.randomUUID();
	}

	public UUID getId() {
		return record.getId();
	}
	public void setId(UUID id) {
		record.setId(id);
	}
	public String getFullName() {
		return record.getFullName();
	}
	public void setFullName(String fullName) {
		record.setFullName(fullName);
	}
	public String getAddress() {
		return record.getAddress();
	}
	public void setAddress(String address) {
		record.setAddress(address);
	}
	public String getEmail() {
		return record.getEmail();
	}
	public void setEmail(String email) {
		record.setEmail(email);
	}
	public String getBrith() {
		return record.getBrith();
	}
	public void setBrith(String brith) {
		record.setBrith(brith);
	}
	public String getGender() {
		return record.getGender();
	}
	public void setGender(String gender) {
		record.setGender(gender);
	}
	public String getPhone() {
		return record.getPhone();
	}
	public void setPhone(String Phone) {
		record.setPhone(Phone);
	}
	public int getAllowance() {
		return record.getAllowance();
	}
	public void setAllowance(int allowance) {
		record.setAllowance(allowance);
	}

	public BeneficiaryComponent getRecord() {return this.record;}
	public void setRecord(BeneficiaryComponent record) {this.record = record;}


	public HashMap<String, Object> toHashMap() {
        return record.toHashMap();
    }

}
