package aisco.beneficiary.rumahsinggah;

import java.util.*;

import aisco.beneficiary.core.BeneficiaryDecorator;
import aisco.beneficiary.core.Beneficiary;
import aisco.beneficiary.core.BeneficiaryComponent;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="beneficiary_rumahsinggah")
@Table(name="beneficiary_rumahsinggah")
public class BeneficiaryImpl extends BeneficiaryDecorator {

	protected String checkInDate;
	protected String checkOutDate;
	protected String stayReason;

	public BeneficiaryImpl() {
		super();
		this.checkInDate = "";
		this.checkOutDate = "";
		this.stayReason = "";
		this.objectName = BeneficiaryImpl.class.getName();
	}
	
	public BeneficiaryImpl(BeneficiaryComponent record, String checkInDate, String checkOutDate, String stayReason) {
		super(record, BeneficiaryImpl.class.getName());
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.stayReason = stayReason;
		this.objectName = BeneficiaryImpl.class.getName();
	}

	public BeneficiaryImpl(UUID id, BeneficiaryComponent record, String checkInDate, String checkOutDate, String stayReason) {
		super(id, record);
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.stayReason = stayReason;
		this.objectName = BeneficiaryImpl.class.getName();

	}
	
	public BeneficiaryImpl(UUID id, BeneficiaryComponent record, String checkInDate, String checkOutDate, String stayReason, String objectName) {
		super(id, record);
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.stayReason = stayReason;
		this.objectName = objectName;

	}

	public String getCheckInDate() {
		return this.checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	public String getCheckOutDate() {
		return this.checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public String getStayReason() {
		return this.stayReason;
	}

	public void setStayReason(String stayReason) {
		this.stayReason = stayReason;
	}

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> beneficiaryMap = new HashMap<String, Object>();
		beneficiaryMap.put("id", this.id);
		beneficiaryMap.put("fullName", record.getFullName());
		beneficiaryMap.put("address", record.getAddress());
		beneficiaryMap.put("email", record.getEmail());
		beneficiaryMap.put("brith", record.getBrith());
		beneficiaryMap.put("gender", record.getGender());
		beneficiaryMap.put("phone", record.getPhone());
		beneficiaryMap.put("allowance", record.getAllowance());
		beneficiaryMap.put("checkInDate", getCheckInDate());
		beneficiaryMap.put("checkOutDate", getCheckOutDate());
		beneficiaryMap.put("stayReason", getStayReason());

		return beneficiaryMap;
	}


}
