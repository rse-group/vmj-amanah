package aisco.withdraw.core;

import java.util.*;

import aisco.financialreport.core.FinancialReport;

import vmj.routing.route.VMJExchange;

public interface WithdrawResource {
    List<HashMap<String,Object>> saveWithdraw(VMJExchange vmjExchange);
    HashMap<String, Object> updateWithdraw(VMJExchange vmjExchange);
    HashMap<String, Object> getWithdraw(VMJExchange vmjExchange);
    Withdraw createWithdraw(VMJExchange vmjExchange);
    Withdraw createWithdraw(VMJExchange vmjExchange, String objectName);
    Withdraw createWithdraw(VMJExchange vmjExchange, UUID id);
    Withdraw createWithdraw(VMJExchange vmjExchange, UUID id, String objectName);
    List<HashMap<String,Object>> transformWithdrawListToHashMap(List<Withdraw> withdrawList);
    List<HashMap<String,Object>> getAllWithdraw(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteWithdraw(VMJExchange vmjExchange);
    FinancialReport createExpense(VMJExchange vmjExchange);
}
