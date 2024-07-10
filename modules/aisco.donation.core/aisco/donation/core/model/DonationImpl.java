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
        this.idDonation = UUID.randomUUID();
		this.nameDonation = "";
        this.email = "";
        this.phone = "";
        this.amountDonation = 0;
        this.paymentMethodDonation = "";
		this.dateDonation = "";
		this.program = null;
		this.income = null;
		this.descriptionDonation = "";
		this.objectName = DonationImpl.class.getName();
		this.user = null;
    }
    
    public DonationImpl (UUID idDonation, String nameDonation, String email, String phone, long amountDonation, String paymentMethodDonation, String dateDonation, ProgramComponent program, String descriptionDonation, String objectName)
    {
        this.idDonation = idDonation;
		this.nameDonation = nameDonation;
        this.email = email;
        this.phone = phone;
        this.amountDonation = amountDonation;
        this.paymentMethodDonation = paymentMethodDonation;
		this.dateDonation = dateDonation;
		this.income = null; // will be added later on
		this.program = program;
		this.descriptionDonation = descriptionDonation;
		this.objectName = objectName;
		this.user = null; //will be added later on
    }
	
	public DonationImpl (String nameDonation, String email, String phone, long amountDonation, String paymentMethodDonation, String dateDonation, ProgramComponent program, String descriptionDonation)
    {
        this(UUID.randomUUID(), nameDonation, email, phone, amountDonation, paymentMethodDonation, dateDonation, program, descriptionDonation, DonationImpl.class.getName());
    }
	
	public DonationImpl (String nameDonation, String email, String phone, long amountDonation, String paymentMethodDonation, String dateDonation, ProgramComponent program, String descriptionDonation, String objectName)
    {
        this(UUID.randomUUID(), nameDonation, email, phone, amountDonation, paymentMethodDonation, dateDonation, program, descriptionDonation, objectName);
    }
}