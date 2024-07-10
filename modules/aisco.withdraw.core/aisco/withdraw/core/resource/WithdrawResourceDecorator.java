package aisco.withdraw.core;

import java.util.*;

import aisco.financialreport.core.FinancialReport;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class WithdrawResourceDecorator extends WithdrawResourceComponent {

    protected WithdrawResourceComponent record;

    public WithdrawResourceDecorator(WithdrawResourceComponent record) {
        this.record = record;
    }

    public List<HashMap<String,Object>> saveWithdraw(VMJExchange vmjExchange) {
        return record.saveWithdraw(vmjExchange);
    }

    public Withdraw createWithdraw(VMJExchange vmjExchange) {
        return record.createWithdraw(vmjExchange);
    }
    
    public Withdraw createWithdraw(VMJExchange vmjExchange, String objectName) {
    	return record.createWithdraw(vmjExchange, objectName);
    }

    public HashMap<String, Object> updateWithdraw(VMJExchange vmjExchange) {
        return record.updateWithdraw(vmjExchange);
    }

    public HashMap<String, Object> getWithdraw(VMJExchange vmjExchange) {
        return record.getWithdraw(vmjExchange);
    }

    public List<HashMap<String,Object>> getAllWithdraw(VMJExchange vmjExchange) {
        return record.getAllWithdraw(vmjExchange);
    }

     public List<HashMap<String,Object>> transformWithdrawListToHashMap(List<Withdraw> withdrawList) {
        return record.transformWithdrawListToHashMap(withdrawList);
     }

    public List<HashMap<String,Object>> deleteWithdraw(VMJExchange vmjExchange) {
        return record.deleteWithdraw(vmjExchange);
    }

    public FinancialReport createExpense(VMJExchange vmjExchange) {
        return record.createExpense(vmjExchange);
    }
}
