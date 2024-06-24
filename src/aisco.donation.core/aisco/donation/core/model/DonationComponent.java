package aisco.donation.core;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;

import aisco.program.core.*;
import aisco.financialreport.core.*;

import vmj.auth.model.core.*;

@Entity
@Table(name="donation_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DonationComponent implements Donation
{
	@Id
    protected UUID id;
    protected String name;
    protected String email;
    protected String phone;
    protected long amount;
    protected String paymentMethod;
	protected String date;
    protected String objectName = DonationImpl.class.getName();
	
	@ManyToOne(targetEntity=aisco.program.core.ProgramComponent.class)
	protected Program program;
	
	// @ManyToOne(cascade=CascadeType.ALL)
    @ManyToOne(targetEntity=aisco.financialreport.core.FinancialReportComponent.class)
	protected FinancialReport income;
    
    @ManyToOne(targetEntity=vmj.auth.model.core.UserImpl.class)
    protected User user;
	
	protected String description;
	
    public UUID getId() {
        return this.id;
    }
	
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name; 
    }
    public void setName(String name){ this.name = name; }

	public String getPhone(){ return this.phone; }
    public void setPhone(String phone){ this.phone = phone; }
	
	public String getEmail(){ return this.email; }
    public void setEmail(String email){ this.email = email; }

    public long getAmount(){ return this.amount; }
    public void setAmount(long amount){ this.amount = amount; }
	
	public String getPaymentMethod(){ return this.paymentMethod; }
    public void setPaymentMethod(String paymentMethod){ this.paymentMethod = paymentMethod; }
	
	public Program getProgram(){ return this.program; }
    public void setProgram(Program program){ this.program = program; }
	
	public FinancialReport getIncome(){ return this.income; }
    public void setIncome(FinancialReport income){ this.income = income; }
	
	public String getDate(){ return this.date; }
    public void setDate(String date){ this.date = date; }
    
	public String getDescription(){ return this.description; }
    public void setDescription(String description){this.description = description; } 
    
    public User getUser() { return this.user;}
    public void setUser(User user) {this.user = user;}

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> donationMap = new HashMap<String,Object>();
        donationMap.put("id", id);
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
		if (getIncome() != null) {
            donationMap.put("idIncome", getIncome().getId());
        }
		if (getUser() != null) {
			donationMap.put("idUser", getUser().getId());
		}
        return donationMap;
    }
}
