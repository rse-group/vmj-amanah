package aisco.withdraw.core;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;

import aisco.program.core.*;
import aisco.financialreport.core.*;

@Entity(name="withdraw_impl")
@Table(name="withdraw_impl")
public class WithdrawImpl extends WithdrawComponent {
	
	public WithdrawImpl ()
    {
//        this.id = UUID.randomUUID();
//        this.amount = 0;
//		this.expense = null;
//		this.objectName = WithdrawImpl.class.getName();
//		this.user = null;
//		this.disbursementMethod = "";
		
		this.id = UUID.randomUUID();
        this.amount = 0;
        this.date = "";
		this.program = null;
		this.expense = null;
		this.description = "";
		this.objectName = WithdrawImpl.class.getName();
		this.user = null;
		this.disbursementMethod = "";
    }
    
    public WithdrawImpl (UUID id, long amount, String date, ProgramComponent program, String description, String disbursementMethod, String objectName)
    {
//        this.id = id;
//        this.amount = amount;
//		this.expense = null; // will be added later on
//		this.objectName = objectName;
//		this.user = null; //will be added later on
//		this.disbursementMethod = disbursementMethod;
		
		this.id = id;
        this.amount = amount;
        this.date = date;
		this.program = program;
		this.expense = null;
		this.description = description;
		this.objectName = objectName;
		this.user = null;
		this.disbursementMethod = disbursementMethod;
    }
	
	public WithdrawImpl (long amount, String date, ProgramComponent program, String description, String disbursementMethod)
    {
        this(UUID.randomUUID(), amount, date, program, description, disbursementMethod, WithdrawImpl.class.getName());
    }
	
	public WithdrawImpl (long amount, String date, ProgramComponent program, String description, String disbursementMethod, String objectName)
    {
        this(UUID.randomUUID(), amount, date, program, description, disbursementMethod, objectName);
    }
	
	
//	public DonationImpl (UUID id, String name, String email, String phone, long amount, String paymentMethod, String date, ProgramComponent program, String description, String objectName)
//    {
//        this.id = id;
//		this.name = name;
//        this.email = email;
//        this.phone = phone;
//        this.amount = amount;
//        this.paymentMethod = paymentMethod;
//		this.date = date;
//		this.income = null; // will be added later on
//		this.program = program;
//		this.description = description;
//		this.objectName = objectName;
//		this.user = null; //will be added later on
//    }
//	
//	public DonationImpl (String name, String email, String phone, long amount, String paymentMethod, String date, ProgramComponent program, String description)
//    {
//        this(UUID.randomUUID(), name, email, phone, amount, paymentMethod, date, program, description, DonationImpl.class.getName());
//    }
//	
//	public DonationImpl (String name, String email, String phone, long amount, String paymentMethod, String date, ProgramComponent program, String description, String objectName)
//    {
//        this(UUID.randomUUID(), name, email, phone, amount, paymentMethod, date, program, description, objectName);
//    }	
}