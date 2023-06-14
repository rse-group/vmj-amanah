package aisco.automaticreport.journalentry;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "automaticreport_journalentry_impl")
@Table(name = "automaticreport_journalentry_impl")
public class JournalEntryImpl extends JournalEntryComponent {
    protected int accountId;
    protected long debitAmount;
    protected long creditAmount;
    protected String valueDate;
    protected String description;

    public JournalEntryImpl(int accountId, long debitAmount, long creditAmount) {  
        this.accountId = accountId;
        this.debitAmount = debitAmount;
        this.creditAmount = creditAmount;
    }

    public JournalEntryImpl() {
		
    }
   
    public int getAccountId() {
        return this.accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public long getDebitAmount() {
        return this.debitAmount;
    }

    public void setDebitAmount(long debitAmount) {
        this.debitAmount = debitAmount;
    }

    public long getCreditAmount() {
        return this.creditAmount;
    }

    public void setCreditAmount(long creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getValueDate() {
        return this.valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            " accountId='" + getAccountId() + "'" +
            " valueDate='" + getValueDate() + "'" +
            " debitAmount='" + getDebitAmount() + "'" +
            " creditAmount='" + getCreditAmount() + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> financialReportMap = new HashMap<String, Object>();
        financialReportMap.put("id", id);
        financialReportMap.put("accountId", getAccountId());
        financialReportMap.put("debitAmount", getDebitAmount());
        financialReportMap.put("creditAmount", getCreditAmount());
        financialReportMap.put("valueDate", getValueDate());
        financialReportMap.put("description", getDescription());
        return financialReportMap;
    }
}