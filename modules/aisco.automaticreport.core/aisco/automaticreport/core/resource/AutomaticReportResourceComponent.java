package aisco.automaticreport.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.hibernate.integrator.RepositoryUtil;

import aisco.financialreport.core.FinancialReport;
import aisco.chartofaccount.core.ChartOfAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import java.util.stream.Collectors;
import java.text.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Font;
import com.lowagie.text.Chunk;
import com.lowagie.text.Image;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;

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

    public List<HashMap<String,Object>> chartOfAccountsToList(List<ChartOfAccount> accounts) {
        HashMap<String, HashMap<String,Object>> res = new HashMap<String, HashMap<String,Object>>();
        
        for (ChartOfAccount account : accounts) {
            HashMap<String,Object> acc = new HashMap<String,Object>();
            acc.put("amount", new Long(0));
            acc.put("idChartOfAccount", account.getId());
            acc.put("level", account.getLevel());
            acc.put("name", account.getName());
            acc.put("isRestricted", account.getIsRestricted());
            acc.put("cashflowType", account.getCashflowType());
            res.put(String.valueOf(account.getId()), acc);
        }

        return new ArrayList<HashMap<String,Object>>(res.values());
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

    public List<HashMap<String, Object>> getPeriodicTrialBalanceFromJournals(List<HashMap<String, Object>> journals, String startDate, String endDate, String reportType) {
        List<ChartOfAccount> accounts = chartOfAccountRepository.getAllObject("chartofaccount_impl");
        List<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();

        List<String> includedAccountType = new ArrayList<String>();
        switch (reportType) {
            case "BALANCESHEET":
                includedAccountType = Arrays.asList("1" , "2", "3");
                break;
            case "PROFITLOSS":
                includedAccountType = Arrays.asList("4" , "5");
                break;
            default:
                includedAccountType = Arrays.asList("1" , "2", "3", "4", "5");
                break;
        }


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
        // Filter journal up to endDate
        try {
            Date endDateFormatted = formatter.parse(endDate);

            Iterator<HashMap<String, Object>> it = journals.iterator();
            while (it.hasNext()) {
                Date valDate = formatter.parse((String) it.next().get("valueDate"));
                if (valDate.after(endDateFormatted)) {
                    it.remove();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        // Sort journals by valueDate; it will be used for memoization within balance computation
        Collections.sort(journals, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(final HashMap<String, Object> o1, final HashMap<String, Object> o2) {
                int comp = 0;
                try {
                    Date date1 = formatter.parse((String) o1.get("valueDate"));
                    Date date2 = formatter.parse((String) o2.get("valueDate"));

                    if (date1.before(date2)) {
                        comp = -1;
                    } else {
                        comp = 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                

                return comp;
            }
        });

        // Calculate Account Balance from Journal Entries
        HashMap<Integer, Long> accountBalance = new HashMap<Integer, Long>();
        // add PnL summary account by default
        accountBalance.put(3010000, new Long(0));
        accountBalance.put(3020000, new Long(0));

        HashMap<Integer, Long> accountBalancePerStartDate = new HashMap<Integer, Long>();

        boolean balanceHasCopied = false;
        String currentFiscalYear = "";

        for (HashMap<String, Object> entry : journals) {
            // Check if the entry has passed startDate, so store the current balance to *accountBalancePerStartDate*
             try {
                Date currentValDate = formatter.parse((String) entry.get("valueDate"));
                Date startDateFormatted = formatter.parse(startDate);
                if (currentValDate.after(startDateFormatted) && !balanceHasCopied) {
                    accountBalancePerStartDate = new HashMap<Integer, Long>(accountBalance);
                    balanceHasCopied = true;
                }

                // Check if it is an end of fiscal period, create a closing entry (nullify the income and expense account)
                // NOTE: fiscal year closure is neglected for Cashflow Statement, because the report only shows current balance
                if (!currentFiscalYear.equals(((String) entry.get("valueDate")).split("-")[0]) && !currentFiscalYear.equals("") && !reportType.equals("CASHFLOW")) {
                    for (Map.Entry<Integer,Long> mapElement : accountBalance.entrySet()) {
                        String key = String.valueOf(mapElement.getKey());
                        if (key.substring(0,1).equals("4") || key.substring(0,1).equals("5")) {
                            accountBalance.put(Integer.parseInt(key), new Long(0));
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            String accountType = String.valueOf(Integer.toString((int) entry.get("accountId")).charAt(0));
            Long amount = (long) entry.get("debitAmount") != 0 ? (long) entry.get("debitAmount") : (long) entry.get("creditAmount");
            String entryOps = (long) entry.get("debitAmount") != 0 ? "D" : "C";
            String accountDefaultOps = accountType.equals("1") || accountType.equals("5") ? "D" : "C";
            if (!entryOps.equals(accountDefaultOps)) {
                amount = amount * -1;
            }
            
            if (!accountBalance.containsKey((int) entry.get("accountId"))) {
                accountBalance.put((int) entry.get("accountId"), amount);
            } else {
                Long currentBalance = accountBalance.get((int) entry.get("accountId"));
                accountBalance.put((int) entry.get("accountId"), currentBalance + amount);
            }

            // Compute the PnL summary right away if Income / Expense account was on the ledger
            if (accountType.equals("4")) {
                accountBalance.put(Integer.valueOf("3010000"), accountBalance.get(Integer.valueOf("3010000")) + amount);
            } else if (accountType.equals("5")) {
                accountBalance.put(Integer.valueOf("3010000"), accountBalance.get(Integer.valueOf("3010000")) - amount);
            }

            currentFiscalYear = ((String) entry.get("valueDate")).split("-")[0];
        }

        List<HashMap<String,Object>> trialBalance = new ArrayList<HashMap<String,Object>>();

        // Get all GL Accounts and sort
        List<HashMap<String,Object>> glAccounts = chartOfAccountsToList(accounts);
        glAccounts = this.sort(glAccounts);
        
        // Store balance summary of each CoA classes
        HashMap<String, Long> startBalanceSummary = new HashMap<String, Long>();
        HashMap<String, Long> endBalanceSummary = new HashMap<String, Long>();

        startBalanceSummary.put("ASSET", new Long(0));
        startBalanceSummary.put("LIABILITY", new Long(0));
        startBalanceSummary.put("EQUITY", new Long(0));
        startBalanceSummary.put("INCOME-REST", new Long(0));
        startBalanceSummary.put("INCOME-UNREST", new Long(0));
        startBalanceSummary.put("EXPENSE-REST", new Long(0));
        startBalanceSummary.put("EXPENSE-UNREST", new Long(0));

        endBalanceSummary = new HashMap<String, Long>(startBalanceSummary);

        // Iterate CoA and insert account balance to CoA
        for (int i = 0; i < glAccounts.size(); i++) {
            HashMap<String, Object> account = glAccounts.get(i);
            HashMap<String, Object> nextAccount = new HashMap<String, Object>();
            try {
                nextAccount = glAccounts.get(i + 1);
            } catch (IndexOutOfBoundsException e) {
                nextAccount.put("level", 0);
            }

            Long accBalanceStart = new Long(0);
            Long accBalanceEnd = new Long(0);
             if (accountBalance.containsKey(account.get("idChartOfAccount"))) {
                accBalanceEnd = accountBalance.get(account.get("idChartOfAccount"));
            }

            if (accountBalancePerStartDate.containsKey(account.get("idChartOfAccount"))) {
                accBalanceStart = accountBalancePerStartDate.get(account.get("idChartOfAccount"));
            }

            HashMap<String, Object> glBalanceEntry = new HashMap<String, Object>();
            glBalanceEntry.put("id", account.get("idChartOfAccount"));
            glBalanceEntry.put("name", account.get("name"));
            glBalanceEntry.put("level", account.get("level"));

            if (reportType.equals("CASHFLOW")) {
                glBalanceEntry.put("cashflowType", account.get("cashflowType"));
            }


            if ((int) account.get("level") != 0 && (int) nextAccount.get("level") <= (int) account.get("level")) {
                glBalanceEntry.put("amountLastYear", accBalanceEnd);
                glBalanceEntry.put("amountTwoYearsBefore", accBalanceStart); 
            }

            String accountType = String.valueOf(Integer.toString((int) account.get("idChartOfAccount")).charAt(0));
            boolean isIncluded = false;

            for (String c : includedAccountType) {
                if (accountType.equals(c)) {
                    isIncluded = true;
                }
            }
            

            if ((isIncluded && !reportType.equals("CASHFLOW")) || (reportType.equals("CASHFLOW") && account.get("cashflowType") != null)) {
                trialBalance.add(glBalanceEntry);
            }

            // Add balance to balance summary
            // Map accountType to balance summary code
            String accSummaryKey = "";
            
            switch (accountType) {
                case "1":
                    accSummaryKey = "ASSET";
                    break;
                case "2":
                    accSummaryKey = "LIABILITY";
                    break;
                case "3":
                    accSummaryKey = "EQUITY";
                    break;
                case "4":
                    accSummaryKey = "INCOME-UNREST";
                    break;
                case "5":
                    accSummaryKey = "EXPENSE-UNREST";
                    break;
            }


            // Handle equity restriction
            if (accountType.equals("4") && (boolean) account.get("isRestricted")) {
                accSummaryKey = "INCOME-REST";
            }

            if (accountType.equals("5") && (boolean) account.get("isRestricted")) {
                accSummaryKey = "EXPENSE-REST";
            }

            Long startBalance = startBalanceSummary.get(accSummaryKey);
            startBalance = startBalance + accBalanceStart;

            Long endBalance = endBalanceSummary.get(accSummaryKey);
            endBalance = endBalance + accBalanceEnd;
            
            startBalanceSummary.put(accSummaryKey, startBalanceSummary.get(accSummaryKey) + accBalanceStart); 
            endBalanceSummary.put(accSummaryKey, endBalanceSummary.get(accSummaryKey) + accBalanceEnd);
 
        }

        // Set summary balance to header accounts


        res = trialBalance;

        return res;
    }

    public byte[] generateTabularPDFReport(List<HashMap<String, Object>> data, String reportName) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + ".pdf";

        Path pdfPath = Paths.get(fileName);
        Document pdfDoc = new Document();

        try {
            FileOutputStream pdfOutputFile = new FileOutputStream(fileName);
            final PdfWriter pdfWriter = PdfWriter.getInstance(pdfDoc, pdfOutputFile);
            pdfDoc.open();  // Open the Document
            
            // Document Title
            Paragraph orgName = new Paragraph("Yayasan Dompet Peduli");
            orgName.setAlignment(Element.ALIGN_CENTER);
            pdfDoc.add(orgName);

            Paragraph title = new Paragraph(reportName);
            title.setAlignment(Element.ALIGN_CENTER);
            pdfDoc.add(title);

            // Document Body
            PdfPTable table = new PdfPTable(3);
            table.addCell("");
            table.addCell("Saldo berjalan");
            table.addCell("Saldo tahun lalu");

            for (HashMap<String, Object> account : data) {
                table.addCell(account.get("name").toString());
                table.addCell(account.get("currentBalance").toString());
                table.addCell(account.get("previousBalance").toString());
            }

            pdfDoc.add(table);

            pdfDoc.close();
            pdfWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] res = Files.readAllBytes(pdfPath);
        return res;
    }
}