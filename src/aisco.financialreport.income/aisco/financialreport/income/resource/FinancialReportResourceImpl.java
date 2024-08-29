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
        FinancialReport financialReport = createFinancialReport(vmjExchange);
        financialReportRepository.saveObject(financialReport);
        System.out.println("=======financialReport at saveFinancialReport=======");
        FinancialReport financialReportSaved = financialReportRepository.getObject(financialReport.getId());
        System.out.println(financialReport);
        System.out.println(financialReport.getClass().getName());
        System.out.println("financialReportSaved: ");
        System.out.println(financialReportSaved.getId());
        System.out.println("====================================================");
        System.out.println(financialReport);
        return getAllFinancialReport(vmjExchange);
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange) {
        String paymentMethod = (String) vmjExchange.getRequestBodyForm("paymentMethod");
        FinancialReport financialReport = record.createFinancialReport(vmjExchange);
        FinancialReport financialReportIncome = FinancialReportFactory.createFinancialReport("aisco.financialreport.income.FinancialReportImpl", financialReport, paymentMethod);
        System.out.println("========print at CreateFinancialReport==========");
        System.out.println("financialReport: ");
        System.out.println(financialReport);
        System.out.println(financialReport.getClass().getName());
        System.out.println("");
        System.out.println("financialReportIncome: ");
        System.out.println(financialReportIncome);
        System.out.println(financialReportIncome.getClass().getName());
        System.out.println("=================================================");
        
        return financialReportIncome;
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange, UUID id) {
        String paymentMethod = (String) vmjExchange.getRequestBodyForm("paymentMethod");
        FinancialReport savedFinancialReport = financialReportRepository.getObject(id);
        System.out.println("=======savedFinancialReport====");
        System.out.println(savedFinancialReport);
        System.out.println(savedFinancialReport.getClass().getName());
        System.out.println("===============================");
        UUID recordFinancialReportId = (((FinancialReportDecorator) savedFinancialReport).getRecord()).getId();
        FinancialReport financialReport = record.createFinancialReport(vmjExchange, recordFinancialReportId);
        FinancialReport financialReportIncome = FinancialReportFactory.createFinancialReport("aisco.financialreport.income.FinancialReportImpl", id, financialReport, paymentMethod);

        return financialReportIncome;
    }

    @Restricted(permissionName="UpdateIncome")
    @Route(url="call/income/update")
    public HashMap<String, Object> updateFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID id = UUID.fromString(idStr);
        FinancialReport financialReport = financialReportRepository.getObject(id);
        System.out.println("====financialReport at updateIncome======");
        System.out.println(financialReport);
        System.out.println(financialReport.getId());
        System.out.println(financialReport.getClass().getName());
        System.out.println("=========================================");
        financialReport = createFinancialReport(vmjExchange, id);
        financialReportRepository.updateObject(financialReport);
        financialReport = financialReportRepository.getObject(id);
        return financialReport.toHashMap();
    }

    @Route(url="call/income/detail")
    public HashMap<String, Object> getFinancialReport(VMJExchange vmjExchange) {
        return record.getFinancialReport(vmjExchange);
    }

    @Route(url="call/income/list")
    public List<HashMap<String,Object>> getAllFinancialReport(VMJExchange vmjExchange) {
        List<FinancialReport> financialReportList = financialReportRepository.getAllObject("financialreport_income");
        System.out.print("======financialReportList (at list income)===");
        for (FinancialReport fin : financialReportList) {
        	System.out.println(fin.getId());
        	System.out.println(fin.getClass().getName());
        	System.out.println("");
        }
        System.out.println("toHashMap");
        System.out.println(record.transformFinancialReportListToHashMap(financialReportList));
        System.out.print("============================================");
        return record.transformFinancialReportListToHashMap(financialReportList);
    }

    @Restricted(permissionName="DeleteIncome")
    @Route(url="call/income/delete")
    public List<HashMap<String,Object>> deleteFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID id = UUID.fromString(idStr);
        financialReportRepository.deleteObject(id);
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

