package aisco.donation.core;

import aisco.program.core.Program;
import aisco.financialreport.core.FinancialReport;

import vmj.auth.model.core.*;

import java.util.*;

public interface Donation {
    public UUID getIdDonation();
    public void setIdDonation(UUID idDonation);
	
	public String getNameDonation();
    public void setNameDonation(String nameDonation);
	
	public String getPhone();
    public void setPhone(String phone);
	
	public String getEmail();
    public void setEmail(String email);

    public long getAmountDonation();
    public void setAmountDonation(long amountDonation);
	
	public String getPaymentMethodDonation();
	public void setPaymentMethodDonation(String paymentMethodDonation);
	
	public FinancialReport getIncome();
    public void setIncome(FinancialReport income);
	
	public Program getProgram();
    public void setProgram(Program program);
	
	public String getDateDonation();
    public void setDate(String dateDonation);
	
	public String getDescriptionDonation();
    public void setDescriptionDonation(String descriptionDonation);
    
    public User getUser();
    public void setUser(User user);
	
	HashMap<String, Object> toHashMap();
}
