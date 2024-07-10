package aisco.withdraw.core;

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
@Table(name="withdraw_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class WithdrawComponent implements Withdraw
{
	@Id
    protected UUID id;
    protected long amount;
    protected String disbursementMethod;
    protected String date;
    protected String objectName = WithdrawImpl.class.getName();
    
    @ManyToOne(targetEntity=aisco.program.core.ProgramComponent.class)
	protected Program program;
	
	// @ManyToOne(cascade=CascadeType.ALL)
    @ManyToOne(targetEntity=aisco.financialreport.core.FinancialReportComponent.class)
	protected FinancialReport expense;
    
    @ManyToOne(targetEntity=vmj.auth.model.core.UserImpl.class)
    protected User user;
	
    protected String description;
	
    public UUID getId() {
        return this.id;
    }
	
    public void setId(UUID id) {
        this.id = id;
    }


    public long getAmount(){ return this.amount; }
    public void setAmount(long amount){ this.amount = amount; }
	
	public FinancialReport getExpense(){ return this.expense; }
    public void setExpense(FinancialReport expense){ this.expense = expense; }
    
    public String getDisbursementMethod() {return this.disbursementMethod;};
	public void setDisbursementMethod(String disbursementMethod) {
		this.disbursementMethod = disbursementMethod;
	};
	    
    public User getUser() { return this.user;}
    public void setUser(User user) {this.user = user;}
    
    public Program getProgram(){ return this.program; }
    public void setProgram(Program program){ this.program = program; }
	
	public String getDate(){ return this.date; }
    public void setDate(String date){ this.date = date; }
    
	public String getDescription(){ return this.description; }
    public void setDescription(String description){this.description = description; }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> withdrawMap = new HashMap<String,Object>();
        withdrawMap.put("id", id);
        withdrawMap.put("amount", getAmount());
        withdrawMap.put("date", getDate());
        withdrawMap.put("disbursementMethod", getDisbursementMethod());
        withdrawMap.put("description", getDescription());
        if (getProgram() != null) {
        	withdrawMap.put("idProgram", getProgram().getIdProgram());
        	withdrawMap.put("programName", getProgram().getName());
        }
		
		if (getExpense() != null) {
            withdrawMap.put("idExpense", getExpense().getId());
        }
		if (getUser() != null) {
			withdrawMap.put("idUser", getUser().getId());
		}
        return withdrawMap;
    }
}
