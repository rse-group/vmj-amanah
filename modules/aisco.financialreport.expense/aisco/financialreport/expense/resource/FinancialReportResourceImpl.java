package aisco.financialreport.expense;
import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.financialreport.core.FinancialReportResourceDecorator;
import aisco.financialreport.core.FinancialReportResourceComponent;
import aisco.financialreport.core.FinancialReport;
import aisco.financialreport.core.FinancialReportDecorator;
import aisco.financialreport.FinancialReportFactory;

import vmj.auth.annotations.Restricted;

public class FinancialReportResourceImpl extends FinancialReportResourceDecorator {

	public FinancialReportResourceImpl(FinancialReportResourceComponent record) {
        super(record);
    }

    @Restricted(permissionName="CreateExpense")
    @Route(url="call/expense/save")
    public List<HashMap<String,Object>> saveFinancialReport(VMJExchange vmjExchange) {
        FinancialReport financialReport = createFinancialReport(vmjExchange);
        financialReportRepository.saveObject(financialReport);
        System.out.println(financialReport);
        return getAllFinancialReport(vmjExchange);
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange) {
        FinancialReport financialReport = record.createFinancialReport(vmjExchange);
        FinancialReport financialReportExpense = FinancialReportFactory.createFinancialReport("aisco.financialreport.expense.FinancialReportImpl", financialReport);

        return financialReportExpense;
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange, UUID id) {
        FinancialReport savedFinancialReport = financialReportRepository.getObject(id);
        UUID recordFinancialReportId = (((FinancialReportDecorator) savedFinancialReport).getRecord()).getId();
        FinancialReport financialReport = record.createFinancialReport(vmjExchange, recordFinancialReportId);
        FinancialReport financialReportExpense = FinancialReportFactory.createFinancialReport("aisco.financialreport.expense.FinancialReportImpl", id, financialReport);

        return financialReportExpense;
    }

    @Restricted(permissionName="UpdateExpense")
    @Route(url="call/expense/update")
    public HashMap<String, Object> updateFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID id = UUID.fromString(idStr);
        FinancialReport financialReport = financialReportRepository.getObject(id);
        financialReport = createFinancialReport(vmjExchange, id);
        financialReportRepository.updateObject(financialReport);

        financialReport = financialReportRepository.getObject(id);
        System.out.println(financialReport);
        return financialReport.toHashMap();
    }

    @Route(url="call/expense/detail")
    public HashMap<String, Object> getFinancialReport(VMJExchange vmjExchange) {
        return record.getFinancialReport(vmjExchange);
    }

    @Route(url="call/expense/list")
    public List<HashMap<String,Object>> getAllFinancialReport(VMJExchange vmjExchange) {
        List<FinancialReport> financialReportList = financialReportRepository.getAllObject("financialreport_expense");
        return record.transformFinancialReportListToHashMap(financialReportList);
    }

    @Restricted(permissionName="DeleteExpense")
    @Route(url="call/expense/delete")
    public List<HashMap<String,Object>> deleteFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID id = UUID.fromString(idStr);
        financialReportRepository.deleteObject(id);
        return getAllFinancialReport(vmjExchange);
    }
}
