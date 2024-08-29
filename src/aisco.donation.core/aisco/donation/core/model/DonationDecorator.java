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
        this.id = UUID.randomUUID();
    }

    public DonationDecorator(UUID id, DonationComponent record) {
        this.id = id;
        this.record = record;
    }
	
	public DonationDecorator(DonationComponent record, String objectName) {
        this.record = record;
        this.id = UUID.randomUUID();
        this.record.objectName = objectName;
    }
	
	public DonationComponent getRecord() { return this.record; }
    public void setRecord(DonationComponent record) { this.record = record; }

    public String getName() { 
        return record.getName(); 
    }
    public void setName(String name){ 
        record.setName(name);
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

    public long getAmount(){ 
        return record.getAmount();
    }
    public void setAmount(long amount){ 
        record.setAmount(amount);
    }
    
    public String getPaymentMethod(){ 
        return record.getPaymentMethod(); 
    }
    public void setPaymentMethod(String paymentMethod){ 
        record.setPaymentMethod(paymentMethod); 
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
	
	public String getDate(){ 
        return record.getDate(); 
    }
    public void setDate(String date){
        record.setDate(date); 
    }
	
	public String getDescription(){ 
        return record.getDescription(); 
    }
    public void setDescription(String description){ 
        record.setDescription(description); 
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