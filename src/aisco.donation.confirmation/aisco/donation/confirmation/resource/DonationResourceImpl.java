package aisco.donation.confirmation;

import java.util.*;
import java.lang.RuntimeException;
import java.net.URLConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import vmj.auth.core.AuthPayload;
// import vmj.auth.core.StorageStrategy;
// import vmj.auth.storagestrategy.HibernateStrategy;
// import vmj.auth.storagestrategy.PropertiesStrategy;
import vmj.auth.model.core.*;
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

import vmj.auth.annotations.Restricted;

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
                "description", "paymentMethod", "idprogram", "amount"));
        List<Object> types = new ArrayList<Object>(
                Arrays.asList(new HashMap<String, byte[]>(), "", "", "", "", "", "", ""));
        vmjExchange.payloadChecker(keys, types, false);
        Donation donation = createDonation(vmjExchange);
        donationRepository.saveObject(donation);
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
        Donation donationConfirmation = DonationFactory.createDonation(
                "aisco.donation.confirmation.DonationImpl", donation, proofOfTransfer, senderAccount,
                recieverAccount, status);
        
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

     @Restricted(permissionName="UpdateCOD")
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
            throw new BadRequestException("Status dengan id " + idStatus + " tidak ditemukan");
        Donation savedDonation = donationRepository.getObject(id);
        if (savedDonation.getIncome() != null)
            throw new BadRequestException("Konfirmasi donasi dengan id " + id + " sudah mempunyai income");
        if (status.getStatusName().equals("BERHASIL")) {
            FinancialReport income = record.createIncome(vmjExchange);
            financialReportRepository.saveObject(income);
            savedDonation.setIncome((FinancialReportComponent) income);
        }
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
        AuthPayload authPayload = vmjExchange.getAuthPayload();
        String email = authPayload.getEmail();
        
        List<User> users = userRepository.getListObject("auth_user_impl", "email", email);
        User user = users.get(0);
        List<UserRole> userRoles = userRoleRepository.getListObject("auth_user_role_impl", "authuser", user.getId());
        
        List<Donation> donationList = donationRepository.getAllObject("donation_confirmation");
        
      
        if (checkRole(userRoles, "Donatur")) {
        	List<Donation> donationFilteredList = new ArrayList<>();
        	for (Donation donation : donationList) {
                System.out.println(donation.getUser().getId());
                
        		if (donation.getUser() == null) {
        			continue;
        		} else {
        			if (donation.getUser().getId().equals(user.getId())) {
	        			donationFilteredList.add(donation);
	        		}
        		}
        		
        	}
        	return transformDonationListToHashMap(donationFilteredList);
        }
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
    
    public boolean checkRole(List<UserRole> userRoles, String roleName) {
    	for (UserRole userRole : userRoles) {
    		if (userRole.getRole().getName().equals(roleName)) {
    			return true;
    		}
    	}
    	return false;
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
