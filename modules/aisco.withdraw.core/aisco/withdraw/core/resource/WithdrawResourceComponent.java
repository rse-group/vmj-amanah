package aisco.withdraw.core;

import java.util.*;

import aisco.financialreport.core.*;

import aisco.program.core.*;

import vmj.auth.model.core.*;

import vmj.hibernate.integrator.DaoUtil;
import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.VMJExchange;

public abstract class WithdrawResourceComponent implements WithdrawResource {
    protected RepositoryUtil<FinancialReport> financialReportRepository;
    protected RepositoryUtil<Withdraw> withdrawRepository;
    protected RepositoryUtil<User> userRepository;
    protected RepositoryUtil<UserRole> userRoleRepository;
    protected RepositoryUtil<Program> programRepository;

    public WithdrawResourceComponent(){
        this.financialReportRepository = new 
        		RepositoryUtil<FinancialReport>(aisco.financialreport.core.FinancialReportComponent.class);
        this.withdrawRepository = new 
        		RepositoryUtil<Withdraw>(aisco.withdraw.core.WithdrawComponent.class);
        this.userRepository = new
        		RepositoryUtil<User>(vmj.auth.model.core.UserImpl.class);
        this.userRoleRepository = new
        		RepositoryUtil<UserRole>(vmj.auth.model.core.UserRoleImpl.class);
        this.programRepository = new
                RepositoryUtil<Program>(aisco.program.core.ProgramComponent.class);
    }

    public abstract List<HashMap<String,Object>> saveWithdraw(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> updateWithdraw(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> getWithdraw(VMJExchange vmjExchange);
    public abstract Withdraw createWithdraw(VMJExchange vmjExchange);
    public abstract Withdraw createWithdraw(VMJExchange vmjExchange, String objectName);
    public abstract Withdraw createWithdraw(VMJExchange vmjExchange, UUID id);
    public abstract Withdraw createWithdraw(VMJExchange vmjExchange, UUID id, String objectName);
    public abstract List<HashMap<String,Object>> transformWithdrawListToHashMap(List<Withdraw> withdrawList);
    public abstract List<HashMap<String,Object>> getAllWithdraw(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> deleteWithdraw(VMJExchange vmjExchange);
    public abstract FinancialReport createExpense(VMJExchange vmjExchange);
}
