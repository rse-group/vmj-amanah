package aisco.automaticreport.journalentry;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="automaticreport_journalentry_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class JournalEntryComponent implements JournalEntry {

    @Id
    public int id;
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract int getAccountId();
    public abstract void setAccountId(int accountId);

    public abstract long getDebitAmount();
    public abstract void setDebitAmount(long debitAmount);

    public abstract long getCreditAmount();
    public abstract void setCreditAmount(long creditAmount);

    public abstract String getValueDate();
    public abstract void setValueDate(String valueDate);

    public abstract String getDescription();
    public abstract void setDescription(String description);

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