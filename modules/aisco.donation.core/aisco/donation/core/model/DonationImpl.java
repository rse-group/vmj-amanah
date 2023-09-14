package aisco.donation.core;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;

import aisco.program.core.*;
import aisco.financialreport.core.*;

@Entity(name="donation_impl")
@Table(name="donation_impl")
public class DonationImpl extends DonationComponent {
	
	protected String name;
    protected String email;
    protected String phone;
    protected long amount;
    protected String paymentMethod;
	protected String date;
	protected String objectName;
	
	@ManyToOne
	protected ProgramComponent program;
	
	@ManyToOne(cascade=CascadeType.ALL)
	protected FinancialReportComponent income;
	
	protected String description;
	
	public DonationImpl ()
    {
        this.id = UUID.randomUUID();
		this.name = "";
        this.email = "";
        this.phone = "";
        this.amount = 0;
        this.paymentMethod = "";
		this.date = "";
		this.program = null;
		this.income = null;
		this.description = "";
		this.objectName = DonationImpl.class.getName();
    }
    
    public DonationImpl (UUID id, String name, String email, String phone, long amount, String paymentMethod, String date, ProgramComponent program, String description, String objectName)
    {
        this.id = id;
		this.name = name;
        this.email = email;
        this.phone = phone;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
		this.date = date;
		this.income = null; // will be added later on
		this.program = program;
		this.description = description;
		this.objectName = objectName;
    }
	
	public DonationImpl (String name, String email, String phone, long amount, String paymentMethod, String date, ProgramComponent program, String description)
    {
        this(UUID.randomUUID(), name, email, phone, amount, paymentMethod, date, program, description, DonationImpl.class.getName());
    }
	
	public DonationImpl (String name, String email, String phone, long amount, String paymentMethod, String date, ProgramComponent program, String description, String objectName)
    {
        this(UUID.randomUUID(), name, email, phone, amount, paymentMethod, date, program, description, objectName);
    }
	
    public String getName() { return name; }
    public void setName(String name){ this.name = name; }
	
	public String getPhone(){ return phone; }
    public void setPhone(String phone){ this.phone = phone; }
	
	public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public long getAmount(){ return amount; }
    public void setAmount(long amount){ this.amount = amount; }
	
	public String getPaymentMethod(){ return paymentMethod; }
    public void setPaymentMethod(String paymentMethod){ this.paymentMethod = paymentMethod; }
	
	public ProgramComponent getProgram(){ return program; }
    public void setProgram(ProgramComponent program){ this.program = program; }
	
	public FinancialReportComponent getIncome(){ return income; }
    public void setIncome(FinancialReportComponent income){ this.income = income; }
	
	public String getDate(){ return date; }
    public void setDate(String date){ this.date = date; }
    
	public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }

}