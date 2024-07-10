package aisco.donation.core;

import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.persistence.CascadeType;

import aisco.program.core.*;
import aisco.financialreport.core.*;

import vmj.auth.model.core.*;

@MappedSuperclass
public abstract class DonationDecorator extends DonationComponent {
	@OneToOne(cascade=CascadeType.ALL)
    public DonationComponent record;
	
	public DonationDecorator ()
    {
        super();
        this.record = new DonationImpl();
        this.idDonation = UUID.randomUUID();
    }

    public DonationDecorator(UUID idDonation, DonationComponent record) {
        this.idDonation = idDonation;
        this.record = record;
    }
	
	public DonationDecorator(DonationComponent record, String objectName) {
        this.record = record;
        this.idDonation = UUID.randomUUID();
        this.record.objectName = objectName;
    }
	
	public DonationComponent getRecord() { return this.record; }
    public void setRecord(DonationComponent record) { this.record = record; }

    public String getNameDonation() { 
        return record.getNameDonation(); 
    }
    public void setNameDonation(String nameDonation){ 
        record.setNameDonation(nameDonation);
    }
	
	public String getPhone(){ 
        return record.getPhone(); 
    }
    public void setPhone(String phone){ 
        record.setPhone(phone);
    }
	
	public String getEmail(){ 
        return record.getEmail(); 
    }
    public void setEmail(String email){ 
        record.setEmail(email); 
    }

    public long getAmountDonation(){ 
        return record.getAmountDonation();
    }
    public void setAmountDonation(long amountDonation){ 
        record.setAmountDonation(amountDonation);
    }
    
    public String getPaymentMethodDonation(){ 
        return record.getPaymentMethodDonation(); 
    }
    public void setPaymentMethodDonation(String paymentMethodDonation){ 
        record.setPaymentMethodDonation(paymentMethodDonation); 
    }
	
	public Program getProgram(){ 
        return record.getProgram();
    }
    public void setProgram(Program program){ 
        record.setProgram(program); 
    }
	
	public FinancialReport getIncome(){ 
        return record.getIncome(); 
    }
    public void setIncome(FinancialReport income){ 
        record.setIncome(income); 
    }
	
	public String getDateDonation(){ 
        return record.getDateDonation(); 
    }
    public void setDateDonation(String dateDonation){
        record.setDateDonation(dateDonation); 
    }
	
	public String getDescriptionDonation(){ 
        return record.getDescriptionDonation(); 
    }
    public void setDescriptionDonation(String descriptionDonation){ 
        record.setDescriptionDonation(descriptionDonation); 
    }
    
    public User getUser() {
    	return record.getUser();
    }
    public void setUser(User user) {
    	record.setUser(user);
    }

    public HashMap<String, Object> toHashMap() {
        return record.toHashMap();
    }
}