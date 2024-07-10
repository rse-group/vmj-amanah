package aisco.withdraw.core;

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
public abstract class WithdrawDecorator extends WithdrawComponent {
	@OneToOne(cascade=CascadeType.ALL)
    public WithdrawComponent record;
	
	public WithdrawDecorator ()
    {
        super();
        this.record = new WithdrawImpl();
        this.id = UUID.randomUUID();
    }

    public WithdrawDecorator(UUID id, WithdrawComponent record) {
        this.id = id;
        this.record = record;
    }
	
	public WithdrawDecorator(WithdrawComponent record, String objectName) {
        this.record = record;
        this.id = UUID.randomUUID();
        this.record.objectName = objectName;
    }
	
	public WithdrawComponent getRecord() { return this.record; }
    public void setRecord(WithdrawComponent record) { this.record = record; }


    public long getAmount(){ 
        return record.getAmount();
    }
    public void setAmount(long amount){ 
        record.setAmount(amount);
    }
    
    public String getDisbursementMethod(){ 
        return record.getDisbursementMethod(); 
    }
    public void setDisbursementMethod(String disbursementMethod){ 
        record.setDisbursementMethod(disbursementMethod); 
    }
	
	public FinancialReport getExpense(){ 
        return record.getExpense(); 
    }
    public void setExpense(FinancialReport expense){ 
        record.setExpense(expense); 
    }
    
    public User getUser() {
    	return record.getUser();
    }
    public void setUser(User user) {
    	record.setUser(user);
    }
    
    public Program getProgram(){ 
        return record.getProgram();
    }
    public void setProgram(Program program){ 
        record.setProgram(program); 
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

    public HashMap<String, Object> toHashMap() {
        return record.toHashMap();
    }
}