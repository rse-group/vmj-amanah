package aisco.automaticreport.journalentry;

import java.util.*;

public interface JournalEntry {
    int getId();
    void setId(int id);
    int getAccountId();
    void setAccountId(int accountId);
    long getDebitAmount();
    void setDebitAmount(long debitAmount);
    long getCreditAmount();
    void setCreditAmount(long creditAmount);
    String getValueDate();
    void setValueDate(String valueDate);
    String getDescription();
    void setDescription(String description);
    HashMap<String, Object> toHashMap();
}
