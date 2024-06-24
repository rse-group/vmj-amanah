package aisco.financialreport.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.financialreport.FinancialReportFactory;

import aisco.program.core.Program;
import aisco.chartofaccount.core.ChartOfAccount;

import vmj.auth.annotations.Restricted;

public class FinancialReportResourceImpl extends FinancialReportResourceComponent {

    @Restricted(permissionName="CreateFinancialReport")
    @Route(url="call/financialreport/save")
    public List<HashMap<String,Object>> saveFinancialReport(VMJExchange vmjExchange) {
        FinancialReport financialReport = createFinancialReport(vmjExchange);
        financialReportRepository.saveObject(financialReport);
        System.out.println(financialReport);
        return getAllFinancialReport(vmjExchange);
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange) {
        String datestamp = (String) vmjExchange.getRequestBodyForm("datestamp");
        String amountStr = (String) vmjExchange.getRequestBodyForm("amount");
        long amount = Long.parseLong(amountStr);
        String description = (String) vmjExchange.getRequestBodyForm("description");
        Program program = null;
        String idProgramStr = (String) vmjExchange.getRequestBodyForm("idProgram");
        if (idProgramStr != null) {
            UUID idProgram = UUID.fromString(idProgramStr);
            program = financialReportRepository.getProxyObject(aisco.program.core.ProgramComponent.class, idProgram);
        }
        ChartOfAccount coa = null;
        String idCoaStr = (String) vmjExchange.getRequestBodyForm("idCoa");
        if (idCoaStr != null) {
            int idCoaUUID = Integer.parseInt(idCoaStr);
            coa = financialReportRepository.getProxyObject(aisco.chartofaccount.core.ChartOfAccountComponent.class, idCoaUUID);
        }
        FinancialReport financialReport = FinancialReportFactory.createFinancialReport("aisco.financialreport.core.FinancialReportImpl", datestamp, amount, description, program, coa);
        return financialReport;
    }

    public FinancialReport createFinancialReport(VMJExchange vmjExchange, UUID id) {
        String datestamp = (String) vmjExchange.getRequestBodyForm("datestamp");
        String amountStr = (String) vmjExchange.getRequestBodyForm("amount");
        long amount = Long.parseLong(amountStr);
        String description = (String) vmjExchange.getRequestBodyForm("description");
        Program program = null;
        String idProgramStr = (String) vmjExchange.getRequestBodyForm("idProgram");
        if (idProgramStr != null) {
            UUID idProgram = UUID.fromString(idProgramStr);
            program = financialReportRepository.getProxyObject(aisco.program.core.ProgramComponent.class, idProgram);
        }
        ChartOfAccount coa = null;
        String idCoaStr = (String) vmjExchange.getRequestBodyForm("idCoa");
        if (idCoaStr != null) {
            int idCoaUUID = Integer.parseInt(idCoaStr);
            coa = financialReportRepository.getProxyObject(aisco.chartofaccount.core.ChartOfAccountComponent.class, idCoaUUID);
        }
        FinancialReport financialReport = FinancialReportFactory.createFinancialReport("aisco.financialreport.core.FinancialReportImpl", id, datestamp, amount, description, program, coa);
        return financialReport;
    }

    @Restricted(permissionName="UpdateFinancialReport")
    @Route(url="call/financialreport/update")
    public HashMap<String, Object> updateFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID id = UUID.fromString(idStr);
        FinancialReport financialReport = financialReportRepository.getObject(id);
        financialReport.setDatestamp((String) vmjExchange.getRequestBodyForm("datestamp"));
        String amountStr = (String) vmjExchange.getRequestBodyForm("amount");
        financialReport.setAmount(Long.parseLong(amountStr));
        financialReport.setDescription((String) vmjExchange.getRequestBodyForm("description"));
        Program program = financialReport.getProgram();
        String idProgramStr = (String) vmjExchange.getRequestBodyForm("idProgram");
        if (idProgramStr != null) {
            UUID idProgram = UUID.fromString(idProgramStr);
            program = financialReportRepository.getProxyObject(aisco.program.core.ProgramComponent.class, idProgram);
        }
        financialReport.setProgram(program);

        ChartOfAccount coa = financialReport.getCoa();
        String idCoaStr = (String) vmjExchange.getRequestBodyForm("idCoa");
        if (idCoaStr != null) {
            int idCoaUUID = Integer.parseInt(idCoaStr);
            coa = financialReportRepository.getProxyObject(aisco.chartofaccount.core.ChartOfAccountComponent.class, idCoaUUID);
        }
        financialReport.setCoa(coa);

        financialReportRepository.updateObject(financialReport);

        financialReport = financialReportRepository.getObject(id);
        return financialReport.toHashMap();
    }

    @Route(url="call/financialreport/detail")
    public HashMap<String, Object> getFinancialReport(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("id");
        UUID id = UUID.fromString(idStr);
        FinancialReport financialReport = financialReportRepository.getObject(id);
        System.out.println(financialReport);
        return financialReport.toHashMap();
    }

    @Route(url="call/financialreport/list")
    public List<HashMap<String,Object>> getAllFinancialReport(VMJExchange vmjExchange) {
        List<FinancialReport> financialReportList = financialReportRepository.getAllObject("financialreport_impl", FinancialReportImpl.class.getName());
        return transformFinancialReportListToHashMap(financialReportList);
    }

    public List<HashMap<String,Object>> transformFinancialReportListToHashMap(List<FinancialReport> financialReportList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < financialReportList.size(); i++) {
            resultList.add(financialReportList.get(i).toHashMap());
        }

        return resultList;
    }

    @Restricted(permissionName="DeleteFinancialReport")
    @Route(url="call/financialreport/delete")
    public List<HashMap<String,Object>> deleteFinancialReport(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID id = UUID.fromString(idStr);
        financialReportRepository.deleteObject(id);
        return getAllFinancialReport(vmjExchange);
    }
}
