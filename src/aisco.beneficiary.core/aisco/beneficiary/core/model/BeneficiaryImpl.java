package aisco.beneficiary.core;

import java.lang.Math;
import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity(name="beneficiary_impl")
@Table(name="beneficiary_impl")
public class BeneficiaryImpl extends BeneficiaryComponent {

	public BeneficiaryImpl(UUID id, String fullName, String address, String email, String birth, String gender, String phone, int allowance, String objectName) {
		this.id = id;
		this.fullName = fullName;
		this.address = address;
		this.email = email;
		this.birth = birth;
		this.gender = gender;
		this.phone = phone;
		this.allowance = allowance;
		this.objectName = objectName;
	}

	public BeneficiaryImpl(String fullName, String address, String email, String birth, String gender, String phone, int allowance, String objectName) {
		this.id =  UUID.randomUUID();
		this.fullName = fullName;
		this.address = address;
		this.email = email;
		this.birth = birth;
		this.gender = gender;
		this.phone = phone;
		this.allowance = allowance;
		this.objectName = objectName;
	}

	public BeneficiaryImpl(String fullName, String address, String email, String birth, String gender, String phone, int allowance) {
		this.id =  UUID.randomUUID();
		this.fullName = fullName;
		this.address = address;
		this.email = email;
		this.birth = birth;
		this.gender = gender;
		this.phone = phone;
		this.allowance = allowance;
		this.objectName = BeneficiaryImpl.class.getName();
	}
		
	public BeneficiaryImpl(UUID id, String fullName, String address, String email, String birth, String gender, String phone, int allowance) {
		this.id =  id;
		this.fullName = fullName;
		this.address = address;
		this.email = email;
		this.birth = birth;
		this.gender = gender;
		this.phone = phone;
		this.allowance = allowance;
		this.objectName = BeneficiaryImpl.class.getName();
	}

	public BeneficiaryImpl() {
		this.id = this.id =  UUID.randomUUID();
		this.fullName = "";
		this.address = "";
		this.email = "";
		this.birth = "";
		this.gender = "";
		this.phone = "";
		this.allowance = 0;
		this.objectName = BeneficiaryImpl.class.getName();
	}


}
