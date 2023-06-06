package aisco.financialreport.expense;
import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.financialreport.core.FinancialReportResourceDecorator;
import aisco.financialreport.core.FinancialReportResourceComponent;
import aisco.financialreport.core.FinancialReport;
import aisco.financialreport.core.FinancialReportDecorator;
import aisco.financialreport.FinancialReportFactory;

import prices.auth.vmj.annotations.Restricted;

public class FinancialReportResourceImpl extends FinancialReportResourceDecorator {

    public FinancialReportResourceImpl(FinancialReportResourceComponent record) {
        super(record);
    }

    @Restricted(permissionName="ModifyFinancialReportImpl")
    @Route(url="call/expense/save")
    public List<HashMap<String,Object>> saveFinancialReport(VMJExchange vmjExchange) {
        FinancialReport financialReport = createFinancialReport(vmjExchange);
        financialReportRepository.saveObject(financialReport);
        System.out.println(financialReport);
        return getAllFinancialReport(vmjExchange);
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange) {
        FinancialReport financialReport = super.createFinancialReport(vmjExchange);
        FinancialReport financialReportExpense = FinancialReportFactory.createFinancialReport("aisco.financialreport.expense.FinancialReportImpl", financialReport);

        return financialReportExpense;
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange, int id) {
        FinancialReport savedFinancialReport = financialReportRepository.getObject(id);
        int recordFinancialReportId = (((FinancialReportDecorator) savedFinancialReport)).getId();
        FinancialReport financialReport = super.createFinancialReport(vmjExchange, recordFinancialReportId);
        FinancialReport financialReportExpense = FinancialReportFactory.createFinancialReport("aisco.financialreport.expense.FinancialReportImpl", id, financialReport);

        return financialReportExpense;
    }

    @Restricted(permissionName="ModifyFinancialReportImpl")
    @Route(url="call/expense/update")
    public HashMap<String, Object> updateFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        FinancialReport financialReport = financialReportRepository.getObject(id);
        financialReport = createFinancialReport(vmjExchange, id);
        financialReportRepository.updateObject(financialReport);

        financialReport = financialReportRepository.getObject(id);
        System.out.println(financialReport);
        return financialReport.toHashMap();
    }

    @Route(url="call/expense/detail")
    public HashMap<String, Object> getFinancialReport(VMJExchange vmjExchange) {
        return super.getFinancialReport(vmjExchange);
    }

    @Route(url="call/expense/list")
    public List<HashMap<String,Object>> getAllFinancialReport(VMJExchange vmjExchange) {
        List<FinancialReport> financialReportList = financialReportRepository.getAllObject("financialreport_expense");
        return transformFinancialReportListToHashMap(financialReportList);
    }

    @Restricted(permissionName="ModifyFinancialReportImpl")
    @Route(url="call/expense/delete")
    public List<HashMap<String,Object>> deleteFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        financialReportRepository.deleteObject(id);
        return getAllFinancialReport(vmjExchange);
    }
}
