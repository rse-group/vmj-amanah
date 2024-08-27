package aisco.donation.core;

import aisco.program.core.ProgramComponent;
import aisco.financialreport.core.FinancialReportComponent;

import java.util.*;

public interface Donation {
    public int getId();
    public void setId(int id);
	
	public String getName();
    public void setName(String name);
	
	String getPhone();
    void setPhone(String phone);
	
	String getEmail();
    void setEmail(String email);

    long getAmount();
    void setAmount(long amount);
	
	String getPaymentMethod();
	void setPaymentMethod(String paymentMethod);
	
	FinancialReportComponent getIncome();
    void setIncome(FinancialReportComponent income);
	
	ProgramComponent getProgram();
    void setProgram(ProgramComponent program);
	
	String getDate();
    void setDate(String date);
	
	String getDescription();
    void setDescription(String description);
	
	HashMap<String, Object> toHashMap();
}
