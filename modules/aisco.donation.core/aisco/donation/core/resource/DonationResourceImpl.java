package aisco.donation.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.FieldValidationException;

import aisco.donation.DonationFactory;

import aisco.program.core.*;
import aisco.chartofaccount.core.*;
import aisco.financialreport.core.*;
import aisco.financialreport.FinancialReportFactory;

import prices.auth.vmj.annotations.Restricted;

public class DonationResourceImpl extends DonationResourceComponent {

    private final int DONATION_COA_CODE = 42010;

    @Restricted(permissionName = "ModifyDonationReportImpl")
    @Route(url = "call/donation/save")
    public List<HashMap<String, Object>> saveDonation(VMJExchange vmjExchange) {
        Donation donation = createDonation(vmjExchange);
        donationDao.saveObject(donation);
        System.out.println(donation);
        return getAllDonation(vmjExchange);
    }

    public FinancialReport createIncome(VMJExchange vmjExchange) {
        Map<String, Object> payload = vmjExchange.getPayload();

        String description = (String) payload.get("description");
        String paymentMethod = (String) payload.get("paymentMethod");
        String idProgramStr = (String) payload.get("idprogram");
        String date = (String) payload.get("date");
        String amountStr = (String) payload.get("amount");
        long amount = Long.parseLong(amountStr);

        Program program = null;
        if (idProgramStr != null) {
            int idProgram = Integer.parseInt(idProgramStr);
            program = donationDao.getProxyObject(ProgramComponent.class, idProgram);
        }
        ChartOfAccount coa = donationDao.getProxyObject(ChartOfAccountComponent.class, DONATION_COA_CODE);

		FinancialReport financialReport = FinancialReportFactory.createFinancialReport("aisco.financialreport.core.FinancialReportImpl", date, amount, description, program, coa);
		FinancialReport income = FinancialReportFactory.createFinancialReport("aisco.financialreport.income.FinancialReportImpl", financialReport, paymentMethod);
		return income;
	}

    public Donation createDonation(VMJExchange vmjExchange) {
        return createDonation(vmjExchange, (new Random()).nextInt());
    }

    public Donation createDonation(VMJExchange vmjExchange, int id) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String name = (String) payload.get("name");
        String email = (String) payload.get("email");
        String phone = (String) payload.get("phone");
        String description = (String) payload.get("description");
        String paymentMethod = (String) payload.get("paymentMethod");
        String idProgramStr = (String) payload.get("idprogram");
        Program program = null;
        if (idProgramStr != null) {
            int idProgram = Integer.parseInt(idProgramStr);
            program = donationDao.getProxyObject(ProgramComponent.class, idProgram);
        }
        String date = (String) payload.get("date");
        String amountStr = (String) payload.get("amount");
        long amount = Long.parseLong(amountStr);
        if (amount < 0) {
            throw new FieldValidationException("Nilai amount harus lebih besar dari 0");
        }
        Donation donation = DonationFactory.createDonation("aisco.donation.core.DonationImpl", id, name, email, phone,
                amount, paymentMethod, date, program, description);
        return donation;
    }

    @Restricted(permissionName = "ModifyDonationReportImpl")
    @Route(url = "call/donation/update")
    public HashMap<String, Object> updateDonation(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        Donation donation = createDonation(vmjExchange, id);
        donationDao.updateObject(donation);
        return donation.toHashMap();
    }

    @Route(url = "call/donation/detail")
    public HashMap<String, Object> getDonation(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("id");
        int id = Integer.parseInt(idStr);
        Donation donation = donationDao.getObject(id);
        System.out.println(donation);
        try {
            return donation.toHashMap();
        } catch (NullPointerException e) {
            HashMap<String, Object> blank = new HashMap<>();
            blank.put("error", "Missing Params");
            return blank;
        }
    }

    @Route(url = "call/donation/list")
    public List<HashMap<String, Object>> getAllDonation(VMJExchange vmjExchange) {
        List<Donation> donationList = donationDao.getAllObject("donation_impl");
        return transformDonationListToHashMap(donationList);
    }

    // TODO: bisa dimasukin ke kelas util
    public List<HashMap<String, Object>> transformDonationListToHashMap(List<Donation> donationList) {
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < donationList.size(); i++) {
            resultList.add(donationList.get(i).toHashMap());
        }

        return resultList;
    }

    @Restricted(permissionName = "ModifyDonationReportImpl")
    @Route(url = "call/donation/delete")
    public List<HashMap<String, Object>> deleteDonation(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        donationDao.deleteObject(id);
        return getAllDonation(vmjExchange);
    }
}
