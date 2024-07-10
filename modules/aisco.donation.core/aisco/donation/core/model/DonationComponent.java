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
    protected UUID idDonation;
    protected String nameDonation;
    protected String email;
    protected String phone;
    protected long amountDonation;
    protected String paymentMethodDonation;
	protected String dateDonation;
    protected String objectName = DonationImpl.class.getName();
	
	@ManyToOne(targetEntity=aisco.program.core.ProgramComponent.class)
	protected Program program;
	
    @ManyToOne(targetEntity=aisco.financialreport.core.FinancialReportComponent.class)
	protected FinancialReport income;
    
    @ManyToOne(targetEntity=vmj.auth.model.core.UserImpl.class)
    protected User user;
	
	protected String descriptionDonation;
	
    public UUID getIdDonation() {
        return this.idDonation;
    }
	
    public void setIdDonation(UUID idDonation) {
        this.idDonation = idDonation;
    }

    public String getNameDonation() {
        return this.nameDonation; 
    }
    public void setNameDonation(String nameDonation){ this.nameDonation = nameDonation; }

	public String getPhone(){ return this.phone; }
    public void setPhone(String phone){ this.phone = phone; }
	
	public String getEmail(){ return this.email; }
    public void setEmail(String email){ this.email = email; }

    public long getAmountDonation(){ return this.amountDonation; }
    public void setAmountDonation(long amountDonation){ this.amountDonation = amountDonation; }
	
	public String getPaymentMethodDonation(){ return this.paymentMethodDonation; }
    public void setPaymentMethodDonation(String paymentMethodDonation){ this.paymentMethodDonation = paymentMethodDonation; }
	
	public Program getProgram(){ return this.program; }
    public void setProgram(Program program){ this.program = program; }
	
	public FinancialReport getIncome(){ return this.income; }
    public void setIncome(FinancialReport income){ this.income = income; }
	
	public String getDateDonation(){ return this.dateDonation; }
    public void setDateDonation(String dateDonation){ this.dateDonation = dateDonation; }
    
	public String getDescriptionDonation(){ return this.descriptionDonation; }
    public void setDescriptionDonation(String descriptionDonation){this.descriptionDonation = descriptionDonation; } 
    
    public User getUser() { return this.user;}
    public void setUser(User user) {this.user = user;}

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> donationMap = new HashMap<String,Object>();
        donationMap.put("idDonation", idDonation);
		donationMap.put("nameDonation", getNameDonation());
		donationMap.put("phone", getPhone());
		donationMap.put("email", getEmail());
        donationMap.put("dateDonation", getDateDonation());
        donationMap.put("amountDonation", getAmountDonation());
		donationMap.put("descriptionDonation", getDescriptionDonation());
		if (getProgram() != null) {
            donationMap.put("idProgram", getProgram().getIdProgram());
            donationMap.put("programName", getProgram().getNameProgram());
        }
        donationMap.put("paymentMethodDonation", getPaymentMethodDonation());
		if (getIncome() != null) {
            donationMap.put("idIncome", getIncome().getIdFinancialReport());
        }
		if (getUser() != null) {
			donationMap.put("idUser", getUser().getId());
		}
        return donationMap;
    }
}
