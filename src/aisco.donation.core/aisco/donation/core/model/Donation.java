package aisco.donation.core;

import aisco.program.core.Program;
import aisco.financialreport.core.FinancialReport;

import vmj.auth.model.core.*;

import java.util.*;

public interface Donation {
    public UUID getId();
    public void setId(UUID id);
	
	public String getName();
    public void setName(String name);
	
	public String getPhone();
    public void setPhone(String phone);
	
	public String getEmail();
    public void setEmail(String email);

    public long getAmount();
    public void setAmount(long amount);
	
	public String getPaymentMethod();
	public void setPaymentMethod(String paymentMethod);
	
	public FinancialReport getIncome();
    public void setIncome(FinancialReport income);
	
	public Program getProgram();
    public void setProgram(Program program);
	
	public String getDate();
    public void setDate(String date);
	
	public String getDescription();
    public void setDescription(String description);
    
    public User getUser();
    public void setUser(User user);
	
	HashMap<String, Object> toHashMap();
}
