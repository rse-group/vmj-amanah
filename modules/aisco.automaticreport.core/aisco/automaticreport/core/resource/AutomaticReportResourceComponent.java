package aisco.automaticreport.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.RepositoryUtil;

import aisco.financialreport.core.FinancialReport;
import aisco.chartofaccount.core.ChartOfAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AutomaticReportResourceComponent implements AutomaticReportResource {
    protected RepositoryUtil<FinancialReport> financialReportRepository;
    protected RepositoryUtil<ChartOfAccount> chartOfAccountRepository;

    public AutomaticReportResourceComponent() {
        this.financialReportRepository = new RepositoryUtil<FinancialReport>(aisco.financialreport.core.FinancialReportComponent.class);
        this.chartOfAccountRepository = new RepositoryUtil<ChartOfAccount>(aisco.chartofaccount.core.ChartOfAccountComponent.class);
    }

    public abstract List<HashMap<String,Object>> list(VMJExchange vmjExchange);

    public List<HashMap<String,Object>> transFormToChartOfAccount(List<HashMap<String, Object>> incomes, List<HashMap<String, Object>> expenses, int codeLength) {
        HashMap<String, HashMap<String,Object>> entries = new HashMap<String, HashMap<String,Object>>();

        try {
            for (int i = 0; i < incomes.size(); i++) {
                HashMap<String, Object> incomeMap = incomes.get(i);
                // UUID idCoa = (UUID) incomeMap.get("idCoa");
                // FR with no FK
                // int idCoa = (int) incomeMap.get("idCoa");
                // ChartOfAccount coa = chartOfAccountRepository.getObject(idCoa);
                // int code = coa.getCode();
                // String codeStr = (Integer.toString(code)).substring(0, codeLength);
                String idCoa = incomeMap.get("idCoa").toString();
                String codeStr = idCoa.substring(0, codeLength);
                HashMap<String,Object> entry = !entries.isEmpty() ? entries.get(codeStr) : null;
                if(entry == null) {
                    HashMap<String,Object> newEntry = new HashMap<String,Object>();
                    newEntry.put("amount", incomeMap.get("amount"));
                    newEntry.put("idChartOfAccount", Integer.parseInt(codeStr));
                    newEntry.put("level", codeLength);
                    entries.put(codeStr, newEntry);
                } else {
                    long entryAmount = (long) entry.get("amount");
                    long incomeAmount = (long) incomeMap.get("amount");
                    long total = entryAmount + incomeAmount;
                    entry.put("amount", total);
                }
            }

            for (int i = 0; i < expenses.size(); i++) {
                HashMap<String, Object> expenseMap = expenses.get(i);
                // UUID idCoa = (UUID) expenseMap.get("idCoa");
                // FR with no FK
                // int idCoa = (int) expenseMap.get("idCoa");
                // ChartOfAccount coa = chartOfAccountRepository.getObject(idCoa);
                // int code = coa.getCode();
                // String codeStr = (Integer.toString(code)).substring(0, codeLength);
                String idCoa = expenseMap.get("idCoa").toString();
                String codeStr = idCoa.substring(0, codeLength);
                HashMap<String,Object> entry = !entries.isEmpty() ? entries.get(codeStr) : null;
                if(entry == null) {
                    HashMap<String,Object> newEntry = new HashMap<String,Object>();
                    newEntry.put("amount", expenseMap.get("amount"));
                    newEntry.put("idChartOfAccount", Integer.parseInt(codeStr));
                    newEntry.put("level", codeLength);
                    entries.put(codeStr, newEntry);
                } else {
                    long entryAmount = (long) entry.get("amount");
                    long expenseAmount = (long) expenseMap.get("amount");
                    long total = entryAmount + expenseAmount;
                    entry.put("amount", total);
                }
            }
        } catch (Exception e) {
			e.printStackTrace();
		}

        ArrayList<HashMap<String,Object>> chartOfAccount = new ArrayList<HashMap<String,Object>>(entries.values());
        return chartOfAccount;
    }

    public List<HashMap<String, Object>> addDetail(List<HashMap<String,Object>> coaSheets) {
        for (int i = 0; i < coaSheets.size(); i++) {
            HashMap<String, Object> entry = coaSheets.get(i);
            String id = entry.get("idChartOfAccount").toString();
            id = id + "0000000";
            id = id.substring(0, 5);

            ArrayList<String> requiredFields = new ArrayList<>();
            requiredFields.add("description");
            requiredFields.add("name");
            List<ChartOfAccount> chartOfAccountList = chartOfAccountRepository.<Integer>getListObject("chartofaccount_impl", "code", Integer.parseInt(id));
            ChartOfAccount chartOfAccount = chartOfAccountList.get(0);
            entry.put("description", chartOfAccount.getDescription());
            entry.put("name", chartOfAccount.getName());
        }

        return coaSheets;
    }

    public List<HashMap<String, Object>> removeAmount(List<HashMap<String,Object>> coaSheets, int level) {
        for (int i = 0; i < coaSheets.size(); i++) {
            HashMap<String, Object> entry = coaSheets.get(i);
            if ( entry.containsKey("level") && ((int) entry.get("level")) == level) {
                entry.remove("amount");
            }
        }

        return coaSheets;
    }

    public List<HashMap<String, Object>> sort(List<HashMap<String,Object>> coaSheets) {
        // sort based on id
        Collections.sort(coaSheets, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(final HashMap<String, Object> o1, final HashMap<String, Object> o2) {
                String id1 = o1.get("idChartOfAccount").toString();
                id1 = id1 + "0000000";
                id1 = id1.substring(0, 5);
                int idInt1 = Integer.parseInt(id1);
                String id2 = o2.get("idChartOfAccount").toString();
                id2 = id2 + "0000000";
                id2 = id2.substring(0, 5);
                int idInt2 = Integer.parseInt(id2);

                return idInt1 - idInt2;
            }
        });

        return coaSheets;
    }

    public List<HashMap<String, Object>> getOperationalActivity(List<HashMap<String, Object>> coaLevel1, List<HashMap<String, Object>> coaLevel2) {
        HashMap<String, Object> header = this.createCoa("Aktivitas Operasional", 91000);

        // exclude coa entry with head id coa 1 or 2
        List<HashMap<String, Object>> coaSheets = new ArrayList<HashMap<String, Object>>();
        coaSheets.addAll(coaLevel1);
        coaSheets.addAll(coaLevel2);
        Set<String> restrictedHeadId = new HashSet<String>();
        restrictedHeadId.add("1");
        restrictedHeadId.add("2");
        coaSheets = this.removeEntry(coaSheets, restrictedHeadId);
        coaSheets = this.addDetail(coaSheets);

        // to show amount of every level 1
        List<HashMap<String, Object>> coaLevel1Updated = this.removeEntry(coaLevel1, restrictedHeadId);
        coaLevel1Updated = this.addDetail(coaLevel1Updated);
        coaLevel1Updated = this.updateCoas(coaLevel1Updated);

        coaSheets.addAll(coaLevel1Updated);
        coaSheets = sort(coaSheets);

        // Net Cash for Operational Activity
        long netAmount = this.getAmount(coaLevel1Updated);
        HashMap<String, Object> netEntry = this.createCoa("Kas Bersih yang diterima (digunakan) untuk Aktivitas Operasi", 91000);
        netEntry.put("amount", netAmount);

        coaSheets.add(netEntry);
        coaSheets.add(0, header);

        return coaSheets;
    }

    public HashMap<String, Object> createCoa(String coaName, int idCoa) {
        HashMap<String, Object> coa = new HashMap<String, Object>();
        coa.put("idChartOfAccount", idCoa);
        coa.put("name", coaName);
        return coa;
    }

    public List<HashMap<String, Object>> removeEntry(List<HashMap<String, Object>> entries, Set<String> headIdCoas) {
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < entries.size(); i++) {
            HashMap<String, Object> entry = entries.get(i);
            String strHeadIdCoa = entry.get("idChartOfAccount").toString().substring(0,1);
            boolean isEntryRestricted = headIdCoas.contains(strHeadIdCoa);

            if (!isEntryRestricted) {
                result.add(entry);
            }
        }

        return result;
    }

    public List<HashMap<String, Object>> updateCoas(List<HashMap<String, Object>> entries) {
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < entries.size(); i++) {
            HashMap<String, Object> newEntry = new HashMap<String, Object>(entries.get(i));
            String name = newEntry.get("name").toString();
            String newName = "Total " + name;
            int newLevel = 2;
            String strIdCoa = newEntry.get("idChartOfAccount").toString();
            String newStrIdCoa = strIdCoa + "99";
            newEntry.put("name", newName);
            newEntry.put("level", newLevel);
            newEntry.put("idChartOfAccount", Integer.parseInt(newStrIdCoa));

            result.add(newEntry);
        }
        return result;
    }

    public long getAmount(List<HashMap<String, Object>> coaLevel1) {
        long amount = 0;

        for (int i = 0; i < coaLevel1.size(); i++) {
            HashMap<String, Object> entry = coaLevel1.get(i);
            String strHeadIdCoa = entry.get("idChartOfAccount").toString().substring(0,1);
            long coaAmount = Long.parseLong(entry.get("amount").toString());

            if (strHeadIdCoa.equals("6")) {
                amount = amount - coaAmount;
            } else {
                amount = amount + coaAmount;
            }
        }

        return amount;
    }

    public List<HashMap<String, Object>> getInvestActivity() {
        HashMap<String, Object> header = this.createCoa("Aktivitas Investasi", 92000);
        HashMap<String, Object> netEntry = this.createCoa("Kas Bersih yang diterima (digunakan) untuk Aktivitas Investasi", 92000);
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        result.add(header);
        result.add(netEntry);
        return result;
    }

    public List<HashMap<String, Object>> getFundingActivity() {
        HashMap<String, Object> header = this.createCoa("Aktivitas Pendanaan", 93000);
        HashMap<String, Object> netEntry = this.createCoa("Kas Bersih yang diterima (digunakan) untuk Aktivitas Pendanaan", 93000);
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        result.add(header);
        result.add(netEntry);
        return result;
    }

    public List<HashMap<String, Object>> getCashFlowSummary(List<HashMap<String, Object>> coaLevel1) {
        HashMap<String, Object> netto = this.createCoa("Kenaikan (Penurunan) netto dalam kas dan setara kas", 94000);

        Set<String> notStartYearHead = new HashSet<String>();
        notStartYearHead.add("3");
        notStartYearHead.add("4");
        notStartYearHead.add("5");
        notStartYearHead.add("6");
        List<HashMap<String, Object>> beginEntries = this.removeEntry(coaLevel1, notStartYearHead);
        long beginAmount = this.getAmount(beginEntries);
        HashMap<String, Object> beginningEntry = this.createCoa("Kas pada awal tahun", 95000);
        beginningEntry.put("amount", beginAmount);

        Set<String> notEndYearHead = new HashSet<String>();
        notEndYearHead.add("1");
        notEndYearHead.add("2");
        List<HashMap<String, Object>> netEntries = this.removeEntry(coaLevel1, notEndYearHead);
        long netAmount = this.getAmount(netEntries);
        long endingAmount = netAmount + beginAmount;
        HashMap<String, Object> endingEntry = this.createCoa("Kas pada akhir tahun", 96000);
        endingEntry.put("amount", endingAmount);

        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        result.add(netto);
        result.add(beginningEntry);
        result.add(endingEntry);
        return result;
    }

    protected List<HashMap<String, Object>> getIncomes() {
        List<FinancialReport> financialReportList = financialReportRepository.getAllObject("financialreport_income");
        return transformFinancialReportListToHashMap(financialReportList);
    }

    protected List<HashMap<String, Object>> getExpenses() {
        List<FinancialReport> financialReportList = financialReportRepository.getAllObject("financialreport_expense");
        return transformFinancialReportListToHashMap(financialReportList);
    }

    protected List<HashMap<String,Object>> transformFinancialReportListToHashMap(List<FinancialReport> objectList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < objectList.size(); i++) {
            resultList.add(objectList.get(i).toHashMap());
        }

        return resultList;
    }
}