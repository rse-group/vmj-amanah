package aisco.summary.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.RepositoryUtil;

import aisco.financialreport.core.FinancialReport;
import aisco.program.core.Program;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;  
import java.text.SimpleDateFormat; 
import java.text.ParseException;

public abstract class SummaryResourceComponent implements SummaryResource {

    protected RepositoryUtil<FinancialReport> financialReportRepository;
    protected RepositoryUtil<Program> programRepository;

    public int id;

    public String datestamp;

    public String description;

    public int income;

    public int expense;

    public String programName;

    public SummaryResourceComponent() {
        this.financialReportRepository = new RepositoryUtil<FinancialReport>(aisco.financialreport.core.FinancialReportComponent.class);
        this.programRepository = new RepositoryUtil<Program>(aisco.program.core.ProgramComponent.class);
    }

    //TODO: refactor this, income and expense doesn't need to bee transformed to List<HashMap<String, Object>>
    @Route(url="call/summary/list")
    public ArrayList<HashMap<String,Object>> list(VMJExchange vmjExchange) {
        List<HashMap<String, Object>> allIncomes = this.getIncomes();
        List<HashMap<String, Object>> allExpenses = this.getExpenses();
        ArrayList<HashMap<String, Object>> summariesWithSaldo = new ArrayList<>();

        long saldo = 0;

        for (int i = 0; i < allIncomes.size(); i++) {
            HashMap<String, Object> incomeMap = allIncomes.get(i);
            HashMap<String, Object> summaryMap = new HashMap<String, Object>();
            String datestamp = incomeMap.get("datestamp").toString();
            summaryMap.put("datestamp", datestamp);
            String description = incomeMap.get("description").toString();
            summaryMap.put("description", description);
            long income = (long) incomeMap.get("amount");
            summaryMap.put("income", income);
            long expense = 0;
            summaryMap.put("expense", expense);
            // UUID idProgram = (UUID) incomeMap.get("idProgram");
            // FR with no FK
            // int idProgram = (int) incomeMap.get("idProgram");
            // String programName = programRepository.getObject(idProgram).getName();
            String programName = incomeMap.get("programName").toString();
            summaryMap.put("programName", programName);
            saldo = saldo + income;
            summaryMap.put("saldo", saldo);
            summariesWithSaldo.add(summaryMap);
        }

        for (int i = 0; i < allExpenses.size(); i++) {
            HashMap<String, Object> incomeMap = allExpenses.get(i);
            HashMap<String, Object> summaryMap = new HashMap<String, Object>();
            String datestamp = incomeMap.get("datestamp").toString();
            summaryMap.put("datestamp", datestamp);
            String description = incomeMap.get("description").toString();
            summaryMap.put("description", description);
            long income = 0;
            summaryMap.put("income", income);
            long expense = (long) incomeMap.get("amount");;
            summaryMap.put("expense", expense);
            // UUID idProgram = (UUID) incomeMap.get("idProgram"); 
            // FR with no FK
            // int idProgram = (int) incomeMap.get("idProgram"); 
            // String programName = programRepository.getObject(idProgram).getName();
            String programName = incomeMap.get("programName").toString();
            summaryMap.put("programName", programName);
            saldo = saldo + income;
            summaryMap.put("saldo", saldo);
            summariesWithSaldo.add(summaryMap);
        }

        // sort berdasarkan tanggal
        Collections.sort(summariesWithSaldo, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(final HashMap<String, Object> o1, final HashMap<String, Object> o2) {
                String sDate1 = o1.get("datestamp").toString();
                String sDate2 = o2.get("datestamp").toString();
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
                    date2 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate2);
                } catch (ParseException pe) {
                    System.out.println(pe.getMessage());
                }
                // sort descending
                return date2.compareTo(date1);
            }
        });

        return summariesWithSaldo;
    }

    private List<HashMap<String, Object>> getIncomes() {
        List<FinancialReport> financialReportList = financialReportRepository.getAllObject("aisco.financialreport.income.FinancialReportImpl");
        return transformFinancialReportListToHashMap(financialReportList);
    }

    private List<HashMap<String, Object>> getExpenses() {
        List<FinancialReport> financialReportList = financialReportRepository.getAllObject("aisco.financialreport.expense.FinancialReportImpl");
        return transformFinancialReportListToHashMap(financialReportList);
    }

    private List<HashMap<String,Object>> transformFinancialReportListToHashMap(List<FinancialReport> financialReportList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < financialReportList.size(); i++) {
            resultList.add(financialReportList.get(i).toHashMap());
        }

        return resultList;
    }
}