package aisco.donation.confirmation;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Lob;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.Tuple;

import aisco.donation.core.DonationDecorator;
import aisco.donation.core.DonationComponent;

@Entity(name = "donation_confirmation")
@Table(name = "donation_confirmation")
public class DonationImpl extends DonationDecorator {

    @Lob
    public String proofOfTransfer;
    public String senderAccount;
    public String recieverAccount;
    public String status;

    public DonationImpl() {
        super();
        this.proofOfTransfer = "";
        this.senderAccount = "";
        this.recieverAccount = "";
        this.status = "";
        this.objectName = DonationImpl.class.getName();
    }

    public DonationImpl(UUID idDonation, DonationComponent record, String proofOfTransfer, String senderAccount,
            String recieverAccount, String status) {
        super(idDonation, record);
        this.proofOfTransfer = proofOfTransfer;
        this.senderAccount = senderAccount;
        this.recieverAccount = recieverAccount;
        this.status = status;
        this.objectName = DonationImpl.class.getName();
    }

    public DonationImpl(DonationComponent record, String proofOfTransfer, String senderAccount,
            String recieverAccount, String status) {
        super(record, DonationImpl.class.getName());
        this.proofOfTransfer = proofOfTransfer;
        this.senderAccount = senderAccount;
        this.recieverAccount = recieverAccount;
        this.status = status;
        this.objectName = DonationImpl.class.getName();
    }

    public String getProofOfTransfer() {
        return this.proofOfTransfer;
    }

    public void setProofOfTransfer(String proofOfTransfer) {
        this.proofOfTransfer = proofOfTransfer;
    }

    public String getSenderAccount() {
        return this.senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getRecieverAccount() {
        return this.recieverAccount;
    }

    public void setRecieverAccount(String recieverAccount) {
        this.recieverAccount = recieverAccount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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
       if (record.getIncome() != null)
           donationMap.put("idIncome", record.getIncome().getIdFinancialReport());
       donationMap.put("proofOfTransfer", getProofOfTransfer());
       donationMap.put("senderAccount", getSenderAccount());
       donationMap.put("recieverAccount", getRecieverAccount());
       donationMap.put("status", getStatus());
       donationMap.put("statusId", Status.findStatusByName(getStatus()).getStatusId());
        donationMap.put("status", getStatus());
        return donationMap;
    }
}