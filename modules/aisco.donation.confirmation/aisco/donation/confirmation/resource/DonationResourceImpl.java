package aisco.donation.confirmation;

import java.util.*;
import java.lang.RuntimeException;
import java.net.URLConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import prices.auth.core.AuthPayload;
import prices.auth.core.StorageStrategy;
import prices.auth.vmj.storagestrategy.HibernateStrategy;
import prices.auth.vmj.storagestrategy.PropertiesStrategy;
import prices.auth.vmj.model.core.*;
import prices.auth.vmj.model.core.UserComponent;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import vmj.hibernate.integrator.RepositoryUtil;

import aisco.donation.DonationFactory;
import aisco.donation.core.DonationResourceDecorator;
import aisco.donation.core.DonationResourceComponent;
import aisco.donation.core.Donation;
import aisco.donation.core.DonationDecorator;
import aisco.financialreport.core.*;
import aisco.financialreport.FinancialReportFactory;
import aisco.program.core.*;
import aisco.chartofaccount.core.*;


import aisco.donation.confirmation.DonationImpl;

import aisco.donation.confirmation.validator.ProofOfTransferValidator;

import prices.auth.vmj.annotations.Restricted;

public class DonationResourceImpl extends DonationResourceDecorator {
    public DonationResourceImpl(DonationResourceComponent record) {
        super(record);
    }

    // @Restricted(permissionName="ModifyDonationReportImpl")
    @Route(url = "call/confirmation/save-full")
    public List<HashMap<String, Object>> saveDonation(VMJExchange vmjExchange) {
        Donation donation = createDonation(vmjExchange);
        donationRepository.saveObject(donation);
        System.out.println(donation);
        return getAllDonation(vmjExchange);
    }

    @Route(url = "call/confirmation/save")
    public HashMap<String, Object> saveLiteDonation(VMJExchange vmjExchange) {
        List<String> keys = new ArrayList<String>(Arrays.asList("proofoftransfer", "name", "email", "phone",
                "description", "paymentMethod", "idprogram", "date", "amount"));
        List<Object> types = new ArrayList<Object>(
                Arrays.asList(new HashMap<String, byte[]>(), "", "", "", "", "", "", "", ""));
        vmjExchange.payloadChecker(keys, types, false);
        Donation donation = createDonation(vmjExchange);
        donationRepository.saveObject(donation);
        System.out.println("======donation at saveLitDonation (save)============");
        System.out.println(donation);
        System.out.println(donation.getClass().getName());
        System.out.println("trying to get saved Donation: ");
        Donation donationAfterSave = donationRepository.getObject(donation.getId());
        System.out.println(donationAfterSave);
        System.out.println(donationAfterSave.getClass().getName());
        System.out.println(donationAfterSave.getId());
        System.out.println("=============================================");
        return donation.toHashMap();
    }

    public Donation createDonation(VMJExchange vmjExchange) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String status = "PENDING";
        String senderAccount = "";
        String recieverAccount = "";
        String proofOfTransfer = "";

        Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("proofoftransfer");
        proofOfTransfer = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
                + ";base64," + Base64.getEncoder().encodeToString(uploadedFile.get("content"));
        int fileSize = uploadedFile.get("content").length;
        if (fileSize > 4000000)
            throw new FileSizeException(4.0, ((double) fileSize) / 1000000, "megabyte");
        try {
            String type = URLConnection
                    .guessContentTypeFromStream(new ByteArrayInputStream(uploadedFile.get("content")));
            if (type == null || !type.startsWith("image"))
                throw new FileTypeException("image");
        } catch (IOException e) {
            throw new FileNotFoundException();
        }

        Donation donation = record.createDonation(vmjExchange, DonationImpl.class.getName());
//        Donation donation = record.createDonation(vmjExchange);
        Donation donationConfirmation = DonationFactory.createDonation(
                "aisco.donation.confirmation.DonationImpl", donation, proofOfTransfer, senderAccount,
                recieverAccount, status);
        System.out.println("==========print at createDonation=============");
        System.out.println("donation: ");
        System.out.println(donation);
        System.out.println(donation.getClass().getName());
        System.out.println("");
        System.out.println("donationConfirmation: ");
        System.out.println(donationConfirmation);
        System.out.println(donationConfirmation.getClass().getName());
        System.out.println("==============================================");
        return donationConfirmation;
    }
    
    public Donation createDonation(VMJExchange vmjExchange, String objectName) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String status = "PENDING";
        String senderAccount = "";
        String recieverAccount = "";
        String proofOfTransfer = "";

        Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("proofoftransfer");
        proofOfTransfer = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
                + ";base64," + Base64.getEncoder().encodeToString(uploadedFile.get("content"));
        int fileSize = uploadedFile.get("content").length;
        if (fileSize > 4000000)
            throw new FileSizeException(4.0, ((double) fileSize) / 1000000, "megabyte");
        try {
            String type = URLConnection
                    .guessContentTypeFromStream(new ByteArrayInputStream(uploadedFile.get("content")));
            if (type == null || !type.startsWith("image"))
                throw new FileTypeException("image");
        } catch (IOException e) {
            throw new FileNotFoundException();
        }

        Donation donation = record.createDonation(vmjExchange, DonationImpl.class.getName());
        Donation donationConfirmation = DonationFactory.createDonation(
                "aisco.donation.confirmation.DonationImpl", donation, proofOfTransfer, senderAccount,
                recieverAccount, status, objectName);
        return donationConfirmation;
    }

    public Donation createDonation(VMJExchange vmjExchange, UUID id) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String status = "PENDING";
        String senderAccount = "";
        String recieverAccount = "";
        String proofOfTransfer = "";

        Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("proofoftransfer");
        proofOfTransfer = Base64.getEncoder().encodeToString(uploadedFile.get("content"));
        int fileSize = uploadedFile.get("content").length;
        if (fileSize > 4000000)
            throw new FileSizeException(4.0, ((double) fileSize) / 1000000, "megabyte");
        try {
            String type = URLConnection
                    .guessContentTypeFromStream(new ByteArrayInputStream(uploadedFile.get("content")));
            if (!type.startsWith("image"))
                throw new FileTypeException("image");
        } catch (IOException e) {
            throw new FileNotFoundException();
        }

        Donation savedDonation = donationRepository.getObject(id);
        UUID recordId = (((DonationDecorator) savedDonation).getRecord()).getId();
        Donation donation = record.createDonation(vmjExchange, recordId);
        Donation donationConfirmation = DonationFactory.createDonation(
                "aisco.donation.confirmation.DonationImpl", id, donation, proofOfTransfer,
                senderAccount, recieverAccount, status, DonationImpl.class.getName());
        return donationConfirmation;
    }
    
    public Donation createDonation(VMJExchange vmjExchange, UUID id, String objectName) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String status = "PENDING";
        String senderAccount = "";
        String recieverAccount = "";
        String proofOfTransfer = "";

        Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("proofoftransfer");
        proofOfTransfer = Base64.getEncoder().encodeToString(uploadedFile.get("content"));
        int fileSize = uploadedFile.get("content").length;
        if (fileSize > 4000000)
            throw new FileSizeException(4.0, ((double) fileSize) / 1000000, "megabyte");
        try {
            String type = URLConnection
                    .guessContentTypeFromStream(new ByteArrayInputStream(uploadedFile.get("content")));
            if (!type.startsWith("image"))
                throw new FileTypeException("image");
        } catch (IOException e) {
            throw new FileNotFoundException();
        }

        Donation savedDonation = donationRepository.getObject(id);
        UUID recordId = (((DonationDecorator) savedDonation).getRecord()).getId();
        Donation donation = record.createDonation(vmjExchange, recordId);
        Donation donationConfirmation = DonationFactory.createDonation(
                "aisco.donation.confirmation.DonationImpl", id, donation, proofOfTransfer,
                senderAccount, recieverAccount, status, objectName);
        return donationConfirmation;
    }

    @Route(url = "call/confirmation/list-status")
    public List<HashMap<String, Object>> listStatus(VMJExchange vmjExchange) {
        List<HashMap<String, Object>> listStatus = new ArrayList<HashMap<String, Object>>();
        for (Status status : Status.values()) {
            HashMap<String, Object> currentStatus = new HashMap<String, Object>();
            currentStatus.put("id", status.getStatusId());
            currentStatus.put("name", status.getStatusName());
            listStatus.add(currentStatus);
        }
        return listStatus;
    }

    // @Restricted(permissionName="ModifyDonationReportImpl")
    @Route(url = "call/confirmation/updatestatus")
    public HashMap<String, Object> updateStatusDonation(VMJExchange vmjExchange) {
        List<String> keys = new ArrayList<String>(Arrays.asList("id", "status"));
        List<Object> types = new ArrayList<Object>(Arrays.asList("", ""));
        vmjExchange.payloadChecker(keys, types, false);
        Donation donation = updateStatusConfirmationDonation(vmjExchange);
        donationRepository.updateObject(donation);
        return donation.toHashMap();
    }

    private Donation updateStatusConfirmationDonation(VMJExchange vmjExchange) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("id");
        UUID id = UUID.fromString(idStr);
        String idStatusStr = (String) payload.get("status");
        int idStatus = Integer.parseInt(idStatusStr);
        Status status = Status.findStatusById(idStatus);
        if (status == null)
            throw new ExchangeException("Status dengan id " + idStatus + " tidak ditemukan");
        Donation savedDonation = donationRepository.getObject(id);
        if (savedDonation.getIncome() != null)
            throw new ExchangeException("Konfirmasi donasi dengan id " + id + " sudah mempunyai income");
        if (status.getStatusName().equals("BERHASIL")) {
            FinancialReport income = record.createIncome(vmjExchange);
            financialReportRepository.saveObject(income);
            savedDonation.setIncome((FinancialReportComponent) income);
        }
        System.out.println("========record di updatestatus======");
        System.out.println(status.getStatusName());
        System.out.println(status.getStatusId());
        System.out.println("savedDonation: ");
        System.out.println(savedDonation.getId());
        System.out.println(savedDonation.getEmail());
        System.out.println(savedDonation.getAmount());
        System.out.println(savedDonation);
        System.out.println(savedDonation.getClass().getName());
        System.out.println("====================================");
        ((DonationImpl) savedDonation).setStatus(status.getStatusName());
        return savedDonation;
    }

    // @Restricted(permissionName="ModifyDonationReportImpl")
    @Route(url = "call/confirmation/update")
    public HashMap<String, Object> updateDonation(VMJExchange vmjExchange) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("id");
        UUID id = UUID.fromString(idStr);
        Donation donation = donationRepository.getObject(id);
        donation = createDonation(vmjExchange, id);
        donationRepository.updateObject(donation);
        return donation.toHashMap();
    }

    @Route(url = "call/confirmation/detail")
    public HashMap<String, Object> getDonation(VMJExchange vmjExchange) {
        return record.getDonation(vmjExchange);
    }

    @Restricted(permissionName="ReadCOD")
    @Route(url = "call/confirmation/list")
    public List<HashMap<String, Object>> getAllDonation(VMJExchange vmjExchange) {
        System.out.println("Masuk call/confirmation/list at DonationResourceImpl at confirmation");
        AuthPayload authPayload = vmjExchange.getAuthPayload();
        System.out.println("====print authPayload====");
        System.out.println(authPayload);
        StorageStrategy storagestrategy = new HibernateStrategy();
        Map<String, Object> userData = storagestrategy.getUserData(authPayload);
        System.out.println(userData);
        String email = authPayload.getEmail();
        System.out.println(email);
        
        RepositoryUtil<User> userDao = new RepositoryUtil<User>(prices.auth.vmj.model.core.UserComponent.class);
        List<User> users = userDao.getListObject("auth_user_impl", "email", email);
        System.out.println(users);
        for (User user : users) {
        	System.out.println(user.getName());
        	System.out.println(user.getId());
        }
        System.out.println("======================");
        List<Donation> donationList = donationRepository.getAllObject("donation_confirmation");
        System.out.println("======donationList (at list confirmation)===");
        for (Donation don : donationList) {
        	System.out.println(don.getId());
        	System.out.println(don.getClass().getName());
        	System.out.println("");
        }
        System.out.println("donationList HashMap: ");
        System.out.print(transformDonationListToHashMap(donationList));
        System.out.print("============================================");
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

    // @Restricted(permissionName="ModifyDonationReportImpl")
    @Route(url = "call/confirmation/delete")
    public List<HashMap<String, Object>> deleteDonation(VMJExchange vmjExchange) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("id");
        UUID id = UUID.fromString(idStr);
        donationRepository.deleteObject(id);
        return getAllDonation(vmjExchange);
    }
}
