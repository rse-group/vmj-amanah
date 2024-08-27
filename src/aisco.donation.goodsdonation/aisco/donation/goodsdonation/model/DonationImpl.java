package aisco.donation.goodsdonation;

import aisco.donation.core.DonationDecorator;
import aisco.donation.core.Donation;
import aisco.donation.core.DonationComponent;

import aisco.program.goodsprogram.ProgramImpl;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Lob;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.Tuple;

import vmj.routing.route.exceptions.DeltaPropertyException;


@Entity(name="donation_goodsdonation")
@Table(name="donation_goodsdonation")
public class DonationImpl extends DonationDecorator {

	public long quantity;
	@Lob
	public String proofOfDonation;
	public String status;
	
	public DonationImpl() {
		super();
		this.quantity = 0L;
		this.proofOfDonation = "";
		this.status = "";
		this.objectName = DonationImpl.class.getName();
	}
	public DonationImpl(UUID id, DonationComponent record, long quantity, String proofOfDonation, String status) {
		super(id,record);
		this.quantity = quantity;
		this.proofOfDonation = proofOfDonation;
		this.status = status;
		this.objectName = DonationImpl.class.getName();
	}
	public DonationImpl(DonationComponent record, long quantity, String proofOfDonation, String status) {
		super(record, DonationImpl.class.getName());
		this.quantity = quantity;
		this.proofOfDonation = proofOfDonation;
		this.status = status;
		this.objectName = DonationImpl.class.getName();
	}

	@Override
	public long getAmount() {
		throw new DeltaPropertyException("Amount dihapus dari delta goodsdonation");
	}
	@Override
	public void setAmount(long amount) {
		throw new DeltaPropertyException("Amount dihapus dari delta goodsdonation");
	}
	
	@Override
	public String getPaymentMethod() {
		throw new DeltaPropertyException("Payment Method dihapus dari delta goodsdonation");
	}
	@Override
	public void setPaymentMethod(String paymentMethod) {
		throw new DeltaPropertyException("Payment Method dihapus dari delta goodsdonation");
	}
	
	public long getQuantity() {
		return this.quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
	public String getProofOfDonation() {
		return this.proofOfDonation;
	}
	public void setProofOfDonation(String proofOfDonation) {
		this.proofOfDonation = proofOfDonation;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> donationMap = new HashMap<String, Object>();
		donationMap.put("id", id);
       	
		donationMap.put("name", record.getName());
		donationMap.put("phone", record.getPhone());
		donationMap.put("email", record.getEmail());
		donationMap.put("date", record.getDate());
		donationMap.put("description", record.getDescription());
		donationMap.put("idProgram", record.getProgram().getIdProgram());
		donationMap.put("program", record.getProgram().getName());
		donationMap.put("quantity", getQuantity());
		donationMap.put("proofOfDonation", getProofOfDonation());
		donationMap.put("status", getStatus());
		donationMap.put("statusId", Status.findStatusByName(getStatus()).getStatusId());
		
		ProgramImpl goodsProgram = ((ProgramImpl)record.getProgram());
		donationMap.put("goodsName", goodsProgram.getGoodsName());
		donationMap.put("unit", goodsProgram.getUnit());
        
        return donationMap;
	}

}
