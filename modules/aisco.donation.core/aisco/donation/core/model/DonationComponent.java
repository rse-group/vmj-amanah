package aisco.donation.core;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="donation_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DonationComponent implements Donation
{
	@Id
    protected UUID id;
	
    public UUID getId() {
        return this.id;
    }
	
    public void setId(UUID id) {
        this.id = id;
    }


    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> donationMap = new HashMap<String,Object>();
        donationMap.put("id", getId());
		donationMap.put("name", getName());
		donationMap.put("phone", getPhone());
		donationMap.put("email", getEmail());
        donationMap.put("date", getDate());
        donationMap.put("amount", getAmount());
		donationMap.put("description", getDescription());
		if (getProgram() != null) {
            donationMap.put("idProgram", getProgram().getIdProgram());
            donationMap.put("programName", getProgram().getName());
        }
        donationMap.put("paymentMethod", getPaymentMethod());
		if (getIncome() != null) donationMap.put("idIncome", getIncome().getId());
		
        return donationMap;
    }
}
