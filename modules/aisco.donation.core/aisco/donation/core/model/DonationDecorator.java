package aisco.donation.core;

import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;

import java.util.Random;

import javax.persistence.CascadeType;

import aisco.program.core.*;
import aisco.financialreport.core.*;

@MappedSuperclass
public abstract class DonationDecorator extends DonationComponent {
	@OneToOne(cascade=CascadeType.ALL)
    protected DonationComponent record;
	
	public DonationDecorator ()
    {
        this.id = (new Random()).nextInt();
    }

    public DonationDecorator(int id, DonationComponent record) {
        this.id = id;
        this.record = record;
    }
	
	public DonationDecorator(DonationComponent record) {
        this(( new Random()).nextInt(), record);
    }
	
	public DonationComponent getRecord() { return this.record; }
    public void setRecord(DonationComponent record) { this.record = record; }

    public String getName() { return record.getName(); }
    public void setName(String name){ record.setName(name); }
	
	public String getPhone(){ return record.getPhone(); }
    public void setPhone(String phone){ record.setPhone(phone); }
	
	public String getEmail(){ return record.getEmail(); }
    public void setEmail(String email){ record.setEmail(email); }

    public long getAmount(){ return record.getAmount(); }
    public void setAmount(long amount){ record.setAmount(amount); }
    
    public String getPaymentMethod(){ return record.getPaymentMethod(); }
    public void setPaymentMethod(String paymentMethod){ record.setPaymentMethod(paymentMethod); }
	
	public ProgramComponent getProgram(){ return record.getProgram(); }
    public void setProgram(ProgramComponent program){ record.setProgram(program); }
	
	public FinancialReportComponent getIncome(){ return record.getIncome(); }
    public void setIncome(FinancialReportComponent income){ record.setIncome(income); }
	
	public String getDate(){ return record.getDate(); }
    public void setDate(String date){ record.setDate(date); }
	
	public String getDescription(){ return record.getDescription(); }
    public void setDescription(String description){ record.setDescription(description); }
}