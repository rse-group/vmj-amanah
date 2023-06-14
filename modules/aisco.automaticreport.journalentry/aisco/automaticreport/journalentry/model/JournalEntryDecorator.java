package aisco.automaticreport.journalentry;

import java.lang.Math;
import java.util.*;

public abstract class JournalEntryDecorator extends JournalEntryComponent {

    public JournalEntryComponent record;

    public JournalEntryDecorator(JournalEntryComponent record) {
        this.record = record;
        Random r = new Random();
        this.id = Math.abs(r.nextInt());
    }

    public JournalEntryDecorator() {
        super();
        this.record = new JournalEntryImpl();
        Random r = new Random();
		this.id = Math.abs(r.nextInt());
    }

    public JournalEntryComponent getRecord() {
        return this.record;
    }

    public void setRecord(JournalEntryComponent record) {
        this.record = record;
    }

    @Override
    public int getAccountId() {
        return this.record.getAccountId();
    }

    @Override
    public void setAccountId(int accountId) {
        this.record.setAccountId(accountId);
    }

    @Override
    public long getDebitAmount() {
        return this.record.getDebitAmount();
    }

    @Override
    public void setDebitAmount(long debitAmount) {
        this.record.setDebitAmount(debitAmount);
    }

    @Override
    public long getCreditAmount() {
        return this.record.getCreditAmount();
    }

    @Override
    public void setCreditAmount(long creditAmount) {
        this.record.setCreditAmount(creditAmount);
    }

    @Override
    public String getValueDate() {
        return this.record.getValueDate();
    }

    @Override
    public void setValueDate(String valueDate) {
        this.record.setValueDate(valueDate);
    }

    @Override
    public String getDescription() {
        return this.record.getDescription();
    }

    @Override
    public void setDescription(String description) {
        this.record.setDescription(description);
    }
}
