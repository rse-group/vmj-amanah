package aisco.donation.donasiloggedin;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.Tuple;

import aisco.donation.core.DonationDecorator;
import aisco.donation.core.DonationComponent;


@Entity(name="donation_donasiloggedin")
@Table(name="donation_donasiloggedin")
public class DonationImpl extends DonationDecorator {

	protected int donaturId;

	public DonationImpl() {
		super();
		this.donaturId = 0;
		this.objectName = DonationImpl.class.getName();
	}

	public DonationImpl(DonationComponent record, int donaturId) {
		super(record, DonationImpl.class.getName());
		this.donaturId = donaturId;
		this.objectName = DonationImpl.class.getName();
	}

	public DonationImpl(UUID id, DonationComponent record, int donaturId) {
		super(id, record);
		System.out.println("Masuk B");
		this.objectName = DonationImpl.class.getName();
	}


	public int getDonaturId() {
		return this.donaturId;
	}

	public void setDonaturId(int donaturId) {
		this.donaturId = donaturId;
	}

	public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> donationMap = new HashMap<String, Object>();
		donationMap.put("id", record.getId());
		
		donationMap.put("name", record.getName());
		donationMap.put("phone", record.getPhone());
		donationMap.put("email", record.getEmail());
		donationMap.put("date", record.getDate());
		donationMap.put("amount", record.getAmount());
		donationMap.put("paymentMethod", record.getPaymentMethod());
		donationMap.put("description", record.getDescription());
		donationMap.put("idProgram", record.getProgram().getIdProgram());
		if (record.getIncome() != null) {
			donationMap.put("idIncome", record.getIncome().getId());
		}
		donationMap.put("donaturId", getDonaturId());
		return donationMap;
    }

}
