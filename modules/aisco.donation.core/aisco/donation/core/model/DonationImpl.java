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
		this.user = null;
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
		this.user = null; //will be added later on
    }
	
	public DonationImpl (String name, String email, String phone, long amount, String paymentMethod, String date, ProgramComponent program, String description)
    {
        this(UUID.randomUUID(), name, email, phone, amount, paymentMethod, date, program, description, DonationImpl.class.getName());
    }
	
	public DonationImpl (String name, String email, String phone, long amount, String paymentMethod, String date, ProgramComponent program, String description, String objectName)
    {
        this(UUID.randomUUID(), name, email, phone, amount, paymentMethod, date, program, description, objectName);
    }
}