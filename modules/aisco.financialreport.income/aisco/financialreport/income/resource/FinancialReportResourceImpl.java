package aisco.financialreport.income;
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

    @Restricted(permissionName="CreateIncome")
    @Route(url="call/income/save")
    public List<HashMap<String,Object>> saveFinancialReport(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
        FinancialReport financialReport = createFinancialReport(vmjExchange);
        financialReportRepository.saveObject(financialReport);
        FinancialReport financialReportSaved = financialReportRepository.getObject(financialReport.getIdFinancialReport());
        return getAllFinancialReport(vmjExchange);
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange) {
        String paymentMethodIncome = (String) vmjExchange.getRequestBodyForm("paymentMethodIncome");
        FinancialReport financialReport = record.createFinancialReport(vmjExchange);
        FinancialReport financialReportIncome = FinancialReportFactory.createFinancialReport("aisco.financialreport.income.FinancialReportImpl", financialReport, paymentMethodIncome);
        return financialReportIncome;
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange, UUID idFinancialReport) {
        String paymentMethodIncome = (String) vmjExchange.getRequestBodyForm("paymentMethodIncome");
        FinancialReport savedFinancialReport = financialReportRepository.getObject(idFinancialReport);
        UUID recordFinancialReportId = (((FinancialReportDecorator) savedFinancialReport).getRecord()).getIdFinancialReport();
        FinancialReport financialReport = record.createFinancialReport(vmjExchange, recordFinancialReportId);
        FinancialReport financialReportIncome = FinancialReportFactory.createFinancialReport("aisco.financialreport.income.FinancialReportImpl", idFinancialReport, financialReport, paymentMethodIncome);

        return financialReportIncome;
    }

    @Restricted(permissionName="UpdateIncome")
    @Route(url="call/income/update")
    public HashMap<String, Object> updateFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("idFinancialReport");
        UUID idFinancialReport = UUID.fromString(idStr);
        FinancialReport financialReport = financialReportRepository.getObject(idFinancialReport);
        financialReport = createFinancialReport(vmjExchange, idFinancialReport);
        financialReportRepository.updateObject(financialReport);
        financialReport = financialReportRepository.getObject(idFinancialReport);
        return financialReport.toHashMap();
    }

    @Route(url="call/income/detail")
    public HashMap<String, Object> getFinancialReport(VMJExchange vmjExchange) {
        return record.getFinancialReport(vmjExchange);
    }

    @Route(url="call/income/list")
    public List<HashMap<String,Object>> getAllFinancialReport(VMJExchange vmjExchange) {
        List<FinancialReport> financialReportList = financialReportRepository.getAllObject("financialreport_income");
        return record.transformFinancialReportListToHashMap(financialReportList);
    }

    @Restricted(permissionName="DeleteIncome")
    @Route(url="call/income/delete")
    public List<HashMap<String,Object>> deleteFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("idFinancialReport");
        UUID idFinancialReport = UUID.fromString(idStr);
        financialReportRepository.deleteObject(idFinancialReport);
        return getAllFinancialReport(vmjExchange);
    }
    
    @Route(url="call/income/accumulatedonation")
    public HashMap<String, Object> accumulateDonation(VMJExchange vmjExchange) {

    	String idProgramStr = vmjExchange.getGETParam("id");
        UUID idProgram = UUID.fromString(idProgramStr);
        List<FinancialReport> incomeList = financialReportRepository.getListObject("financialreport_comp", "coa_id", 42010);
        
		long accumulatedDonation = 0L;
		for (FinancialReport income : incomeList) {
			if (income.getProgram().getIdProgram().equals(idProgram)) {
				accumulatedDonation += income.getAmount();
			} 
		}
		
		HashMap<String, Object> accumulatedDonationMap = new HashMap<String,Object>();
        accumulatedDonationMap.put("accumulatedDonation", accumulatedDonation);
		
		return accumulatedDonationMap;
	}
}

