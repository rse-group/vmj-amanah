package aisco.withdraw.viaspecialdisbursement;

import aisco.withdraw.core.WithdrawDecorator;
import aisco.withdraw.core.Withdraw;
import aisco.withdraw.core.WithdrawComponent;

import aisco.program.core.Program;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.*;

@Entity(name="withdraw_viaspecialdisbursement")
@Table(name="withdraw_viaspecialdisbursement")
public class WithdrawImpl extends WithdrawDecorator {

	protected String status;
	protected String vendorName;

	protected String disbursementId;
	protected String specialMoneyTransferSenderCountry;
	protected String specialMoneyTransferSenderName;
	protected String specialMoneyTransferSenderAddress;
	protected String specialMoneyTransferSenderJob;
	protected String specialMoneyTransferDirection;

	public WithdrawImpl() {
		super();
		this.status = "";
		this.vendorName = "";
		
		this.specialMoneyTransferSenderCountry = "";
		this.specialMoneyTransferSenderName = "";
		this.specialMoneyTransferSenderAddress = "";
		this.specialMoneyTransferSenderJob = "";
		this.specialMoneyTransferDirection = "";
		this.disbursementId = "";
		this.objectName = WithdrawImpl.class.getName();
	}
	
	public WithdrawImpl(WithdrawComponent record, String status, String vendorName,
			String specialMoneyTransferSenderCountry,
			String specialMoneyTransferSenderName,
			String specialMoneyTransferSenderAddress,
			String specialMoneyTransferSenderJob,
			String specialMoneyTransferDirection,
			String disbursementId) {

		super(record, WithdrawImpl.class.getName());
		this.status = status;
		this.vendorName = vendorName;
		
		this.specialMoneyTransferSenderCountry = specialMoneyTransferSenderCountry;
		this.specialMoneyTransferSenderName = specialMoneyTransferSenderName;
		this.specialMoneyTransferSenderAddress = specialMoneyTransferSenderAddress;
		this.specialMoneyTransferSenderJob = specialMoneyTransferSenderJob;
		this.specialMoneyTransferDirection = specialMoneyTransferDirection;
		this.disbursementId = disbursementId;
	}
	
	public WithdrawImpl(UUID id, WithdrawComponent record, String status, String vendorName,
			String specialMoneyTransferSenderCountry,
			String specialMoneyTransferSenderName,
			String specialMoneyTransferSenderAddress,
			String specialMoneyTransferSenderJob,
			String specialMoneyTransferDirection,
			String disbursementId){
		super(id,record);
		this.status = status;
		this.vendorName = vendorName;

		this.objectName = WithdrawImpl.class.getName();

		this.specialMoneyTransferSenderCountry = specialMoneyTransferSenderCountry;
		this.specialMoneyTransferSenderName = specialMoneyTransferSenderName;
		this.specialMoneyTransferSenderAddress = specialMoneyTransferSenderAddress;
		this.specialMoneyTransferSenderJob = specialMoneyTransferSenderJob;
		this.specialMoneyTransferDirection = specialMoneyTransferDirection;
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

    public String getSpecialMoneyTransferSenderCountry() {
        return specialMoneyTransferSenderCountry;
    }

    public void setSpecialMoneyTransferSenderCountry(String specialMoneyTransferSenderCountry) {
        this.specialMoneyTransferSenderCountry = specialMoneyTransferSenderCountry;
    }

    public String getSpecialMoneyTransferSenderName() {
        return specialMoneyTransferSenderName;
    }

    public void setSpecialMoneyTransferSenderName(String specialMoneyTransferSenderName) {
        this.specialMoneyTransferSenderName = specialMoneyTransferSenderName;
    }

    public String getSpecialMoneyTransferSenderAddress() {
        return specialMoneyTransferSenderAddress;
    }

    public void setSpecialMoneyTransferSenderAddress(String specialMoneyTransferSenderAddress) {
        this.specialMoneyTransferSenderAddress = specialMoneyTransferSenderAddress;
    }

    public String getSpecialMoneyTransferSenderJob() {
        return specialMoneyTransferSenderJob;
    }

    public void setSpecialMoneyTransferSenderJob(String specialMoneyTransferSenderJob) {
        this.specialMoneyTransferSenderJob = specialMoneyTransferSenderJob;
    }

    public String getSpecialMoneyTransferDirection() {
        return specialMoneyTransferDirection;
    }

    public void setSpecialMoneyTransferDirection(String specialMoneyTransferDirection) {
        this.specialMoneyTransferDirection = specialMoneyTransferDirection;
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
       

       withdrawMap.put("specialMoneyTransferSenderCountry", getSpecialMoneyTransferSenderCountry());
       withdrawMap.put("specialMoneyTransferSenderName", getSpecialMoneyTransferSenderName());
       withdrawMap.put("specialMoneyTransferSenderAddress", getSpecialMoneyTransferSenderAddress());
       withdrawMap.put("specialMoneyTransferSenderJob", getSpecialMoneyTransferSenderJob());
       withdrawMap.put("specialMoneyTransferDirection", getSpecialMoneyTransferDirection());
       withdrawMap.put("disbursementId", getDisbursementId());
        return withdrawMap;
    }

}
