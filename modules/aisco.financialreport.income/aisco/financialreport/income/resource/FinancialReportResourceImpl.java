package aisco.financialreport.income;
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
	
	protected FinancialReportResourceComponent record;

    public FinancialReportResourceImpl(FinancialReportResourceComponent record) {
        super(record);
    }

    @Restricted(permissionName="ModifyFinancialReportImpl")
    @Route(url="call/income/save")
    public List<HashMap<String,Object>> saveFinancialReport(VMJExchange vmjExchange) {
        FinancialReport financialReport = createFinancialReport(vmjExchange);
        financialReportRepository.saveObject(financialReport);
        System.out.println(financialReport);
        return getAllFinancialReport(vmjExchange);
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange) {
        String paymentMethod = (String) vmjExchange.getRequestBodyForm("paymentMethod");
        FinancialReport financialReport = super.createFinancialReport(vmjExchange);
        FinancialReport financialReportIncome = FinancialReportFactory.createFinancialReport("aisco.financialreport.income.FinancialReportImpl", financialReport, paymentMethod);

        return financialReportIncome;
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange, int id) {
        String paymentMethod = (String) vmjExchange.getRequestBodyForm("paymentMethod");
        FinancialReport savedFinancialReport = financialReportRepository.getObject(id);
        int recordFinancialReportId = (((FinancialReportDecorator) savedFinancialReport)).getId();
        FinancialReport financialReport = super.createFinancialReport(vmjExchange, recordFinancialReportId);
        FinancialReport financialReportIncome = FinancialReportFactory.createFinancialReport("aisco.financialreport.income.FinancialReportImpl", id, financialReport, paymentMethod);

        return financialReportIncome;
    }

    @Restricted(permissionName="ModifyFinancialReportImpl")
    @Route(url="call/income/update")
    public HashMap<String, Object> updateFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        FinancialReport financialReport = financialReportRepository.getObject(id);
        financialReport = createFinancialReport(vmjExchange, id);
        financialReportRepository.updateObject(financialReport);
        financialReport = financialReportRepository.getObject(id);
        return financialReport.toHashMap();
    }

    @Route(url="call/income/detail")
    public HashMap<String, Object> getFinancialReport(VMJExchange vmjExchange) {
        return super.getFinancialReport(vmjExchange);
    }

    @Route(url="call/income/list")
    public List<HashMap<String,Object>> getAllFinancialReport(VMJExchange vmjExchange) {
        List<FinancialReport> financialReportList = financialReportRepository.getAllObject("financialreport_income");
        return transformFinancialReportListToHashMap(financialReportList);
    }

    @Restricted(permissionName="ModifyFinancialReportImpl")
    @Route(url="call/income/delete")
    public List<HashMap<String,Object>> deleteFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        financialReportRepository.deleteObject(id);
        return getAllFinancialReport(vmjExchange);
    }
}
