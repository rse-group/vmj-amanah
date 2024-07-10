package aisco.donation.viapaymentgateway;

import aisco.donation.core.DonationDecorator;
import aisco.donation.core.Donation;
import aisco.donation.core.DonationComponent;

import aisco.program.core.Program;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.*;

@Entity(name="donation_viapaymentgateway")
@Table(name="donation_viapaymentgateway")
public class DonationImpl extends DonationDecorator {

	protected String title;
	protected String status;
	protected String vendorName;
	
	protected String paymentId;
	protected String paymentLink;
	protected String eWalletLink;
	protected String vaNumber;
	protected String bankCode;
	protected String retailPaymentCode;
	protected String paymentCheckoutUrl;
	protected String invoiceTransactionUrl;
	protected String debitCardRedirectUrl;
	protected String creditCardRedirectUrl;

	public DonationImpl() {
		super();
		this.title = "";
		this.status = "";
		this.vendorName = "";
		this.paymentLink = "";
		this.eWalletLink = "";
		this.vaNumber = "";
		this.bankCode = "";
		this.retailPaymentCode = "";
		this.paymentCheckoutUrl = "";
		this.invoiceTransactionUrl = "";
		this.debitCardRedirectUrl = "";
		this.creditCardRedirectUrl = "";
		this.objectName = DonationImpl.class.getName();
	}
	
	public DonationImpl(DonationComponent record, String title, String status, String vendorName, 
			String paymentId, String paymentLink, String eWalletLink, String vaNumber, String bankCode,
			String retailPaymentCode, String paymentCheckoutUrl, String invoiceTransactionUrl,
			String debitCardRedirectUrl, String creditCardRedirectUrl) {

		super(record, DonationImpl.class.getName());
		this.title = title;
		this.status = status;
		this.vendorName = vendorName;
		
		this.paymentId = paymentId;
		this.paymentLink = paymentLink;
		this.eWalletLink = eWalletLink;
		this.vaNumber = vaNumber;
		this.bankCode = bankCode;
		this.retailPaymentCode = retailPaymentCode;
		this.paymentCheckoutUrl = paymentCheckoutUrl;
		this.invoiceTransactionUrl = invoiceTransactionUrl;
		this.debitCardRedirectUrl = debitCardRedirectUrl;
		this.creditCardRedirectUrl = creditCardRedirectUrl;
	}
	
	public DonationImpl(UUID id, DonationComponent record, String title, String status, String vendorName, 
			String paymentId, String paymentLink, String eWalletLink, String vaNumber, String bankCode,
			String retailPaymentCode, String paymentCheckoutUrl, String invoiceTransactionUrl,
			String debitCardRedirectUrl, String creditCardRedirectUrl){
		super(id,record);
		this.title = title;
		this.status = status;
		this.vendorName = vendorName;

		this.paymentId = paymentId;
		this.paymentLink = paymentLink;
		this.eWalletLink = eWalletLink;
		this.vaNumber = vaNumber;
		this.bankCode = bankCode;
		this.retailPaymentCode = retailPaymentCode;
		this.paymentCheckoutUrl = paymentCheckoutUrl;
		this.invoiceTransactionUrl = invoiceTransactionUrl;
		this.debitCardRedirectUrl = debitCardRedirectUrl;
		this.creditCardRedirectUrl = creditCardRedirectUrl;
		this.objectName = DonationImpl.class.getName();
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	public String getPaymentId() {
		return this.paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId; 
	}
	
	public String getPaymentLink() {
		return this.paymentLink;
	}
	public void setPaymentLink(String paymentLink) {
		this.paymentLink = paymentLink; 
	}
	
	public String getEWalletLink() {
		return this.eWalletLink;
	}
	public void setEWalletLink(String eWalletLink) {
		this.eWalletLink = eWalletLink; 
	}
	
	public String getVaNumber() {
		return this.vaNumber;
	}
	public void setVaNumber(String vaNumber) {
		this.vaNumber = vaNumber; 
	}
	
	public String getBankCode() {
		return this.bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
    public String getRetailPaymentCode() {
        return this.retailPaymentCode;
    }
    public void setRetailPaymentCode(String retailPaymentCode) {
        this.retailPaymentCode = retailPaymentCode;
    }

    public String getPaymentCheckoutUrl() {
        return this.paymentCheckoutUrl;
    }
    public void setPaymentCheckoutUrl(String paymentCheckoutUrl) {
        this.paymentCheckoutUrl = paymentCheckoutUrl;
    }

    public String getInvoiceTransactionUrl() {
        return this.invoiceTransactionUrl;
    }
    public void setInvoiceTransactionUrl(String invoiceTransactionUrl) {
        this.invoiceTransactionUrl = invoiceTransactionUrl;
    }

    public String getDebitCardRedirectUrl() {
        return this.debitCardRedirectUrl;
    }
    public void setDebitCardRedirectUrl(String debitCardRedirectUrl) {
        this.debitCardRedirectUrl = debitCardRedirectUrl;
    }

    public String getCreditCardRedirectUrl() {
        return this.creditCardRedirectUrl;
    }
    public void setCreditCardRedirectUrl(String creditCardRedirectUrl) {
        this.creditCardRedirectUrl = creditCardRedirectUrl;
    }
    

	public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> donationMap = new HashMap<String, Object>();
       donationMap.put("idDonation", idDonation);
       
       donationMap.put("nameDonation", record.getNameDonation());
       donationMap.put("phone", record.getPhone());
       donationMap.put("email", record.getEmail());
       donationMap.put("dateDonation", record.getDateDonation());
       donationMap.put("amountDonation", record.getAmountDonation());
       donationMap.put("paymentMethodDonation", record.getPaymentMethodDonation());
       donationMap.put("descriptionDonation", record.getDescriptionDonation());
       donationMap.put("idProgram", record.getProgram().getIdProgram());
       donationMap.put("title", getTitle());
       donationMap.put("vendorName", getVendorName());
       donationMap.put("status", getStatus());
       
       donationMap.put("paymentId", getPaymentId());
       donationMap.put("paymentLink", getPaymentLink());
       donationMap.put("eWalletLink", getEWalletLink());
       donationMap.put("vaNumber", getVaNumber());
       donationMap.put("bankCode", getBankCode());
       donationMap.put("retailPaymentCode", getRetailPaymentCode());
       donationMap.put("paymentCheckoutUrl", getPaymentCheckoutUrl());
       donationMap.put("invoiceTransactionUrl", getInvoiceTransactionUrl());
       donationMap.put("debitCardRedirectUrl", getDebitCardRedirectUrl());
       donationMap.put("creditCardRedirectUrl", getCreditCardRedirectUrl());
       
        return donationMap;
    }

}
