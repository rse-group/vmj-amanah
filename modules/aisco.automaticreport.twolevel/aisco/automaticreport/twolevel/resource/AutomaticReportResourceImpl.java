package aisco.automaticreport.twolevel;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.RepositoryUtil;

import aisco.automaticreport.core.AutomaticReportResourceDecorator;
import aisco.automaticreport.core.AutomaticReportResourceComponent;
import aisco.automaticreport.journalentry.JournalEntry;

public class AutomaticReportResourceImpl extends AutomaticReportResourceDecorator {
     protected RepositoryUtil<JournalEntry> journalEntryRepository;

    public AutomaticReportResourceImpl(AutomaticReportResourceComponent record) {
        super(record);
        this.journalEntryRepository = new RepositoryUtil<JournalEntry>(
            aisco.automaticreport.journalentry.JournalEntryComponent.class);
    }

    // @Route(url="call/automatic-report-twolevel/list")
    // public List<HashMap<String,Object>> list(VMJExchange vmjExchange) {
    //     List<HashMap<String, Object>> allIncomes = getIncomes();
    //     List<HashMap<String, Object>> allExpenses = getExpenses();

    //     List<HashMap<String,Object>> coaSheets = new ArrayList<HashMap<String, Object>>();
    //     List<HashMap<String,Object>> coaLevel1 = transFormToChartOfAccount(allIncomes, allExpenses, 1);
    //     List<HashMap<String,Object>> coaLevel2 = transFormToChartOfAccount(allIncomes, allExpenses, 2);

    //     try {
    //         coaSheets = getOperationalActivity(coaLevel1, coaLevel2);
    //         List<HashMap<String,Object>> investActivity = getInvestActivity();
    //         List<HashMap<String,Object>> fundingActivity = getFundingActivity();
    //         List<HashMap<String,Object>> cashFlowSummary = getCashFlowSummary(coaLevel1);
    //         HashMap<String, Object> emptyRow = this.createCoa("", 0);
    //         coaSheets = this.removeAmount(coaSheets, 1);

    //         coaSheets.addAll(investActivity);
    //         coaSheets.addAll(fundingActivity);
    //         coaSheets.add(emptyRow);
    //         coaSheets.addAll(cashFlowSummary);
    //     } catch (Exception e) {
	// 		e.printStackTrace();
	// 	}

    //     return coaSheets;
    // }

    @Route(url="call/automatic-report-twolevel/list")
    public List<HashMap<String,Object>> list(VMJExchange vmjExchange) {
        
        List<JournalEntry> entries = journalEntryRepository.getAllObject("automaticreport_journalentry_impl");

        List<HashMap<String, Object>> journals = new ArrayList<HashMap<String, Object>>();
        for (JournalEntry entry : entries) {
            journals.add(entry.toHashMap());
        }

        // TODO: refactor this, currently date is hardcoded to show current cashflow statement
        List<HashMap<String, Object>> trialBalance = getPeriodicTrialBalanceFromJournals(journals, "9999-01-01", "9999-01-01", "CASHFLOW");
        List<HashMap<String, Object>> cashflow =  transformTrialBalanceToCashflowStatement(trialBalance);
        return cashflow;
    }

     private List<HashMap<String, Object>> transformTrialBalanceToCashflowStatement(List<HashMap<String, Object>> accounts) {
        List<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();

        for (HashMap<String, Object> account : accounts) {
            HashMap<String, Object> entry = new HashMap<String, Object>(account);

            // TODO: refactor this to make all the financial report structure similar
            entry.put("amount", entry.get("amountLastYear"));

            if (entry.containsKey("amountLastYear")) {
                if (!((Long)entry.get("amountLastYear")).equals(new Long(0))) {
                    res.add(entry);
                }
            }
        }

        // Group GL accounts by Cashflow Type
        Collections.sort(res, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(final HashMap<String, Object> o1, final HashMap<String, Object> o2) {
                int type1 = (int) o1.get("cashflowType");
                int type2 = (int) o2.get("cashflowType");
                return type1 - type2;
            }
        });

        // Summarize each Cashflow Type
        HashMap<Integer, Long> cashflowSummary = new HashMap<Integer, Long>();
        cashflowSummary.put(1, new Long(0));
        cashflowSummary.put(2, new Long(0));
        cashflowSummary.put(3, new Long(0));

         for (int i = 0; i < res.size(); i++) {
            HashMap<String, Object> account = res.get(i);
            HashMap<String, Object> nextAccount = new HashMap<String, Object>();
            try {
                nextAccount = res.get(i + 1);
            } catch (IndexOutOfBoundsException e) {
                nextAccount.put("cashflowType", 4);
            }

            // Handle cashflow operations by account type
            String accountType = String.valueOf(Integer.toString((int) account.get("id")).charAt(0));
            Long amount = (Long) account.get("amount");

            if (accountType.equals("1") || accountType.equals("5")) {
                amount = amount * -1;
            }

            cashflowSummary.put((int) account.get("cashflowType"), (Long) cashflowSummary.get(account.get("cashflowType")) + amount);

            // Insert Cashflow Type summary before iterating next Cashflow Type

            if ((int) account.get("cashflowType") != (int) nextAccount.get("cashflowType")) {
                HashMap<String, Object> summaryEntry = new HashMap<String, Object>();
                summaryEntry.put("name", "Kenaikan (penurunan) saldo kas dan setara kas akibat: " + parseCashflowTypeEnumToString((int) account.get("cashflowType")));
                summaryEntry.put("amount", cashflowSummary.get((int) account.get("cashflowType")));
                summaryEntry.put("level", 1);
                res.add(i + 1, summaryEntry);
                i++;
            }
         }

        Long cashClosingBalance = ((Long) cashflowSummary.get(1)) + (Long) cashflowSummary.get(2) + (Long) cashflowSummary.get(3);
        HashMap<String, Object> summaryEntry = new HashMap<String, Object>();
        summaryEntry.put("name", "Saldo akhir Kas dan Setara Kas");
        summaryEntry.put("amount", cashClosingBalance);
        summaryEntry.put("level", 1);
        res.add(summaryEntry);

        return res;
    }

    private String parseCashflowTypeEnumToString(int cashflowType) {
        switch (cashflowType) { 
            case 1:
                return "Aktivitas Operasional";
            case 2:
                return "Aktivitas Investasi";
            case 3:
                return "Aktivitas Pendanaan";
        }
        return "";
    }

}