package aisco.withdraw.viainternationaldisbursement;

import aisco.withdraw.core.WithdrawDecorator;
import aisco.withdraw.core.Withdraw;
import aisco.withdraw.core.WithdrawComponent;

import aisco.program.core.Program;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.*;

@Entity(name="withdraw_viainternationaldisbursement")
@Table(name="withdraw_viainternationaldisbursement")
public class WithdrawImpl extends WithdrawDecorator {

	protected String status;
	protected String vendorName;

	protected String disbursementId;
	protected String internationalMoneyTransferExchangeRate;
	protected String internationalMoneyTransferFee;
	protected String internationalMoneyTransferSourceCountry;
	protected String internationalMoneyTransferDestinationCountry;
	protected String internationalMoneyTransferAmountInSenderCountry;
	protected String internationalMoneyTransferBeneficiaryCurrencyCode;

	public WithdrawImpl() {
		super();
		this.status = "";
		this.vendorName = "";
		
		this.internationalMoneyTransferExchangeRate = "";
		this.internationalMoneyTransferFee = "";
		this.internationalMoneyTransferSourceCountry = "";
		this.internationalMoneyTransferDestinationCountry = "";
		this.internationalMoneyTransferAmountInSenderCountry = "";
		this.internationalMoneyTransferBeneficiaryCurrencyCode = "";
		this.disbursementId = "";
		this.objectName = WithdrawImpl.class.getName();
	}
	
	public WithdrawImpl(WithdrawComponent record, String status, String vendorName,
			String internationalMoneyTransferExchangeRate,
			String internationalMoneyTransferFee,
			String internationalMoneyTransferSourceCountry,
			String internationalMoneyTransferDestinationCountry,
			String internationalMoneyTransferAmountInSenderCountry,
			String internationalMoneyTransferBeneficiaryCurrencyCode,
			String disbursementId) {

		super(record, WithdrawImpl.class.getName());
		this.status = status;
		this.vendorName = vendorName;
		

		this.internationalMoneyTransferExchangeRate = internationalMoneyTransferExchangeRate;
		this.internationalMoneyTransferFee = internationalMoneyTransferFee;
		this.internationalMoneyTransferSourceCountry = internationalMoneyTransferSourceCountry;
		this.internationalMoneyTransferDestinationCountry = internationalMoneyTransferDestinationCountry;
		this.internationalMoneyTransferAmountInSenderCountry = internationalMoneyTransferAmountInSenderCountry;
		this.internationalMoneyTransferBeneficiaryCurrencyCode = internationalMoneyTransferBeneficiaryCurrencyCode;
		this.disbursementId = disbursementId;
	}
	
	public WithdrawImpl(UUID id, WithdrawComponent record, String status, String vendorName,
			String internationalMoneyTransferExchangeRate,
			String internationalMoneyTransferFee,
			String internationalMoneyTransferSourceCountry,
			String internationalMoneyTransferDestinationCountry,
			String internationalMoneyTransferAmountInSenderCountry,
			String internationalMoneyTransferBeneficiaryCurrencyCode,
			String disbursementId){
		super(id,record);
		this.status = status;
		this.vendorName = vendorName;

		this.objectName = WithdrawImpl.class.getName();

		this.internationalMoneyTransferExchangeRate = internationalMoneyTransferExchangeRate;
		this.internationalMoneyTransferFee = internationalMoneyTransferFee;
		this.internationalMoneyTransferSourceCountry = internationalMoneyTransferSourceCountry;
		this.internationalMoneyTransferDestinationCountry = internationalMoneyTransferDestinationCountry;
		this.internationalMoneyTransferAmountInSenderCountry = internationalMoneyTransferAmountInSenderCountry;
		this.internationalMoneyTransferBeneficiaryCurrencyCode = internationalMoneyTransferBeneficiaryCurrencyCode;
		this.disbursementId = disbursementId;
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

    public String getInternationalMoneyTransferExchangeRate() {
        return internationalMoneyTransferExchangeRate;
    }

    public void setInternationalMoneyTransferExchangeRate(String internationalMoneyTransferExchangeRate) {
        this.internationalMoneyTransferExchangeRate = internationalMoneyTransferExchangeRate;
    }

    public String getInternationalMoneyTransferFee() {
        return internationalMoneyTransferFee;
    }

    public void setInternationalMoneyTransferFee(String internationalMoneyTransferFee) {
        this.internationalMoneyTransferFee = internationalMoneyTransferFee;
    }

    public String getInternationalMoneyTransferSourceCountry() {
        return internationalMoneyTransferSourceCountry;
    }

    public void setInternationalMoneyTransferSourceCountry(String internationalMoneyTransferSourceCountry) {
        this.internationalMoneyTransferSourceCountry = internationalMoneyTransferSourceCountry;
    }

    public String getInternationalMoneyTransferDestinationCountry() {
        return internationalMoneyTransferDestinationCountry;
    }

    public void setInternationalMoneyTransferDestinationCountry(String internationalMoneyTransferDestinationCountry) {
        this.internationalMoneyTransferDestinationCountry = internationalMoneyTransferDestinationCountry;
    }

    public String getInternationalMoneyTransferAmountInSenderCountry() {
        return internationalMoneyTransferAmountInSenderCountry;
    }

    public void setInternationalMoneyTransferAmountInSenderCountry(String internationalMoneyTransferAmountInSenderCountry) {
        this.internationalMoneyTransferAmountInSenderCountry = internationalMoneyTransferAmountInSenderCountry;
    }

    public String getInternationalMoneyTransferBeneficiaryCurrencyCode() {
        return internationalMoneyTransferBeneficiaryCurrencyCode;
    }

    public void setInternationalMoneyTransferBeneficiaryCurrencyCode(String internationalMoneyTransferBeneficiaryCurrencyCode) {
        this.internationalMoneyTransferBeneficiaryCurrencyCode = internationalMoneyTransferBeneficiaryCurrencyCode;
    }

    public String getDisbursementId() {
        return disbursementId;
    }

    public void setDisbursementId(String disbursementId) {
        this.disbursementId = disbursementId;
    }

	public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> withdrawMap = new HashMap<String, Object>();
       withdrawMap.put("id", id);
       withdrawMap.put("vendorName", getVendorName());
       withdrawMap.put("status", getStatus());
       
       withdrawMap.put("date", record.getDate());
       withdrawMap.put("amount", record.getAmount());
       withdrawMap.put("disbursementMethod", record.getDisbursementMethod());
       withdrawMap.put("description", record.getDescription());
       withdrawMap.put("idProgram", record.getProgram().getIdProgram());
       

       withdrawMap.put("internationalMoneyTransferExchangeRate", getInternationalMoneyTransferExchangeRate());
       withdrawMap.put("internationalMoneyTransferFee", getInternationalMoneyTransferFee());
       withdrawMap.put("internationalMoneyTransferSourceCountry", getInternationalMoneyTransferSourceCountry());
       withdrawMap.put("internationalMoneyTransferDestinationCountry", getInternationalMoneyTransferDestinationCountry());
       withdrawMap.put("internationalMoneyTransferAmountInSenderCountry", getInternationalMoneyTransferAmountInSenderCountry());
       withdrawMap.put("internationalMoneyTransferBeneficiaryCurrencyCode", getInternationalMoneyTransferBeneficiaryCurrencyCode());
       withdrawMap.put("disbursementId", getDisbursementId());
        return withdrawMap;
    }

}
