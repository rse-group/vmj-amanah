package aisco.automaticreport.periodic;

import java.util.*;
import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.automaticreport.core.AutomaticReportResourceDecorator;
import aisco.automaticreport.core.AutomaticReportResourceComponent;
import aisco.financialreport.core.FinancialReportComponent;

public class AutomaticReportResourceImpl extends AutomaticReportResourceDecorator {

    protected RepositoryUtil<AutomaticReportPeriodic> automaticReportPeriodicRepository;

    public AutomaticReportResourceImpl(AutomaticReportResourceComponent record) {
        super(record);
        this.automaticReportPeriodicRepository = 
            new RepositoryUtil<AutomaticReportPeriodic>(aisco.automaticreport.periodic.AutomaticReportPeriodicComponent.class);
    }

    @Route(url="call/automatic-report-periodic-model/save")
    public HashMap<String,Object> saveAutomaticReportPeriodic(VMJExchange vmjExchange) {
        AutomaticReportPeriodic automaticReportPeriodic = createAutomaticReportPeriodic(vmjExchange);
        automaticReportPeriodicRepository.saveObject(automaticReportPeriodic);
        return automaticReportPeriodic.toHashMap();
    }

    public AutomaticReportPeriodic createAutomaticReportPeriodic(VMJExchange vmjExchange) {
        String name = (String) vmjExchange.getRequestBodyForm("name");
        boolean isActive = Boolean.parseBoolean((String) vmjExchange.getRequestBodyForm("isActive"));
        AutomaticReportPeriodic automaticReportPeriodic = AutomaticReportPeriodicFactory
            .createAutomaticReportPeriodic("aisco.automaticreport.periodic.AutomaticReportPeriodicImpl", 
            name, isActive);
        return automaticReportPeriodic;
    }

    @Route(url="call/automatic-report-periodic-model/update")
    public HashMap<String,Object> updateAutomaticReportPeriodic(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        String name = (String) vmjExchange.getRequestBodyForm("name");
        boolean isActive = Boolean.parseBoolean((String) vmjExchange.getRequestBodyForm("isActive"));
        AutomaticReportPeriodic automaticReportPeriodic = automaticReportPeriodicRepository.getObject(id);
        
        automaticReportPeriodic.setName(name);
        automaticReportPeriodic.setIsActive(isActive);

        automaticReportPeriodicRepository.updateObject(automaticReportPeriodic);
        return automaticReportPeriodic.toHashMap();
    }

    @Route(url="call/automatic-report-periodic-model/detail")
    public HashMap<String,Object> getAutomaticReportPeriodic(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("id");
        int id = Integer.parseInt(idStr);
        AutomaticReportPeriodic automaticReportPeriodic = automaticReportPeriodicRepository.getObject(id);
        return automaticReportPeriodic.toHashMap();
    }

    @Route(url="call/automatic-report-periodic-model/list")
    public List<HashMap<String,Object>> listAutomaticReportPeriodic(VMJExchange vmjExchange) {
        List<AutomaticReportPeriodic> automaticReportPeriodicList = 
            automaticReportPeriodicRepository.getAllObject("automaticreport_periodic_impl");
        return transformAutomaticReportPeriodicListToHashMap(automaticReportPeriodicList);
    }

    public List<HashMap<String,Object>> transformAutomaticReportPeriodicListToHashMap(
        List<AutomaticReportPeriodic> automaticReportPeriodicList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < automaticReportPeriodicList.size(); i++) {
            AutomaticReportPeriodic automaticReportPeriodic = 
                automaticReportPeriodicList.get(i);
            resultList.add(automaticReportPeriodic.toHashMap());
        }

        return resultList;
    }

    @Route(url="call/automatic-report-periodic/list")
    public List<HashMap<String,Object>> list(VMJExchange vmjExchange) {
        // Cutoff di Desember tahun sebelumnya. Misal mendapat GET query: year=2022, berarti yang 
        // masuk ke CoASheets adalah income/expense yang tercatat dari tgl. 1 Januari 2021-31 Desember 2021
        
        // Catetan: coba cek bisa atau tidak re-use twolevel, masukkan di bab 5
        String idStr = vmjExchange.getGETParam("id");
        int id = Integer.parseInt(idStr);

        String yearStr = automaticReportPeriodicRepository.getObject(id).getName();
        int year = Integer.parseInt(yearStr) - 1;

        List<HashMap<String, Object>> allIncomes = 
            filterFinancialReportByPeriod(getIncomes(), year);
        List<HashMap<String, Object>> allExpenses = 
            filterFinancialReportByPeriod(getExpenses(), year);

        List<HashMap<String,Object>> coaSheets = new ArrayList<HashMap<String, Object>>();
        List<HashMap<String,Object>> coaLevel1 = transFormToChartOfAccount(allIncomes, allExpenses, 1);
        List<HashMap<String,Object>> coaLevel2 = transFormToChartOfAccount(allIncomes, allExpenses, 2);

        try {
            coaSheets = getOperationalActivity(coaLevel1, coaLevel2);
            List<HashMap<String,Object>> investActivity = getInvestActivity();
            List<HashMap<String,Object>> fundingActivity = getFundingActivity();
            List<HashMap<String,Object>> cashFlowSummary = getCashFlowSummary(coaLevel1);
            HashMap<String, Object> emptyRow = this.createCoa("", 0);
            coaSheets = this.removeAmount(coaSheets, 1);

            coaSheets.addAll(investActivity);
            coaSheets.addAll(fundingActivity);
            coaSheets.add(emptyRow);
            coaSheets.addAll(cashFlowSummary);
        } catch (Exception e) {
			e.printStackTrace();
		}
    
        return coaSheets;
    }

    @Route(url="call/automatic-report-periodic-model/delete")
    public HashMap<String,Object> deleteAutomaticReportPeriodic(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        automaticReportPeriodicRepository.deleteObject(id);
        return new HashMap<String, Object>();
    }

    private List<HashMap<String,Object>> filterFinancialReportByPeriod(
        List<HashMap<String, Object>> financialReports, int year) {
        List<HashMap<String, Object>> validFinancialReports = new ArrayList<HashMap<String, Object>>();
        for (HashMap<String, Object> financialReport: financialReports) {
            String datestamp = (String) financialReport.get("datestamp");
            int financialReportYear = Integer.parseInt(datestamp.split("-")[0]);
            if (year == financialReportYear) {
                validFinancialReports.add(financialReport);
            }
        }

        return validFinancialReports;
    }



    private void printAllDetails(List<HashMap<String,Object>> incomes, List<HashMap<String,Object>> expenses,
        List<HashMap<String,Object>> investActivity, List<HashMap<String,Object>> fundingActivity,
        List<HashMap<String,Object>> cashFlowSummary) {
        System.out.println("====INCOME====");
        printDetails(incomes);
        System.out.println("====EXPENSE====");
        printDetails(expenses);
        System.out.println("====INVEST ACTIVITY====");
        printDetails(investActivity);
        System.out.println("====FUNDING ACTIVITY====");
        printDetails(fundingActivity);
        System.out.println("====CASH FLOW SUMMARY====");
        printDetails(cashFlowSummary);
    }

    private void printDetails(List<HashMap<String,Object>> list) {
        for (HashMap<String,Object> detail : list) {
            System.out.println(detail);
        }
    }
}
