package aisco.withdraw.viaagentdisbursement;

import aisco.withdraw.core.WithdrawDecorator;
import aisco.withdraw.core.Withdraw;
import aisco.withdraw.core.WithdrawComponent;

import aisco.program.core.Program;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.*;

@Entity(name="withdraw_viaagentdisbursement")
@Table(name="withdraw_viaagentdisbursement")
public class WithdrawImpl extends WithdrawDecorator {

	protected String status;
	protected String vendorName;

	protected String disbursementId;
	protected String agentMoneyTransferDirection;

	public WithdrawImpl() {
		super();
		this.status = "";
		this.vendorName = "";
		
		this.agentMoneyTransferDirection = "";
		this.disbursementId = "";
		this.objectName = WithdrawImpl.class.getName();
	}
	
	public WithdrawImpl(WithdrawComponent record, String status, String vendorName,
			String agentMoneyTransferDirection,
			String disbursementId) {

		super(record, WithdrawImpl.class.getName());
		this.status = status;
		this.vendorName = vendorName;
		

		this.agentMoneyTransferDirection = agentMoneyTransferDirection;
		this.disbursementId = disbursementId;
	}
	
	public WithdrawImpl(UUID id, WithdrawComponent record, String status, String vendorName,
			String agentMoneyTransferDirection,
			String disbursementId){
		super(id,record);
		this.status = status;
		this.vendorName = vendorName;

		this.objectName = WithdrawImpl.class.getName();

		this.agentMoneyTransferDirection = agentMoneyTransferDirection;
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

    public String getAgentMoneyTransferDirection() {
        return agentMoneyTransferDirection;
    }

    public void setAgentMoneyTransferDirection(String agentMoneyTransferDirection) {
        this.agentMoneyTransferDirection = agentMoneyTransferDirection;
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
       

       withdrawMap.put("agentMoneyTransferDirection", getAgentMoneyTransferDirection());
       withdrawMap.put("disbursementId", getDisbursementId());
        return withdrawMap;
    }

}
