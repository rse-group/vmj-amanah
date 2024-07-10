package aisco.donation.goodsdonation;

import java.util.*;
import java.lang.RuntimeException;
import java.net.URLConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import vmj.auth.core.AuthPayload;
import vmj.auth.model.core.*;
import vmj.auth.annotations.Restricted;

import aisco.donation.DonationFactory;
import aisco.donation.core.*;
import aisco.financialreport.core.*;
import aisco.financialreport.FinancialReportFactory;
import aisco.program.core.*;
import aisco.chartofaccount.core.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import aisco.donation.goodsdonation.DonationImpl;

import vmj.hibernate.integrator.RepositoryUtil;

public class DonationResourceImpl extends DonationResourceDecorator {
    public DonationResourceImpl (DonationResourceComponent record) {
        // to do implement the method
    	super(record);
    }

    // @Restriced(permission = "")
    @Route(url="call/goodsdonation/save")
    public HashMap<String,Object> saveGoodsDonation(VMJExchange vmjExchange){
    	List<String> keys = new ArrayList<String>(Arrays.asList("proofofdonation", "nameDonation", "email", "phone",
                "descriptionDonation", "idprogram", "quantity"));
        List<Object> types = new ArrayList<Object>(
                Arrays.asList(new HashMap<String, byte[]>(), "", "", "", "", "", ""));
        vmjExchange.payloadChecker(keys, types, false);
		Donation donation = createDonation(vmjExchange);
		donationRepository.saveObject(donation);
		Donation savedDonation = donationRepository.getObject(donation.getIdDonation());
		return savedDonation.toHashMap();
	}

    public Donation createDonation(VMJExchange vmjExchange) {
    	Map<String, Object> payload = vmjExchange.getPayload();
    	String status = "PENDING";
    	String quantityStr = (String) payload.get("quantity");
    	long quantity = Long.parseLong(quantityStr);
    	String proofOfDonation = "";
    	
    	Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("proofofdonation");
        proofOfDonation = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
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
        Donation goodsDonation = DonationFactory.createDonation("aisco.donation.goodsdonation.DonationImpl", donation, quantity, 
        		proofOfDonation, status);
        
        return goodsDonation;
	}
    
    public Donation createDonation(VMJExchange vmjExchange, String objectName) {
    	Map<String, Object> payload = vmjExchange.getPayload();
    	String status = "PENDING";
    	String quantityStr = (String) payload.get("quantity");
    	long quantity = Long.parseLong(quantityStr);
    	String proofOfDonation = "";
    	
    	Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("proofofdonation");
        proofOfDonation = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
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
        Donation goodsDonation = DonationFactory.createDonation("aisco.donation.goodsdonation.DonationImpl", donation, quantity, 
        		proofOfDonation, status, objectName);
        
        return goodsDonation;
	}
    
    public Donation createDonation(VMJExchange vmjExchange, UUID idDonation) {
    	Map<String, Object> payload = vmjExchange.getPayload();
    	String status = "PENDING";
    	String quantityStr = (String) payload.get("quantity");
    	long quantity = Long.parseLong(quantityStr);
    	String proofOfDonation = "";
    	
    	Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("proofofdonation");
        proofOfDonation = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
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
        
        Donation savedDonation = donationRepository.getObject(idDonation);
        UUID recordIdDonation = (((DonationDecorator) savedDonation).getRecord()).getIdDonation();
        Donation donation = record.createDonation(vmjExchange, recordIdDonation);
        Donation goodsDonation = DonationFactory.createDonation("aisco.donation.goodsdonation.DonationImpl", idDonation, donation, quantity, 
        		proofOfDonation, status, DonationImpl.class.getName());
        
        return goodsDonation;
	}
    
    public Donation createDonation(VMJExchange vmjExchange, UUID idDonation, String objectName) {
    	Map<String, Object> payload = vmjExchange.getPayload();
    	String status = "PENDING";
    	String quantityStr = (String) payload.get("quantity");
    	long quantity = Long.parseLong(quantityStr);
    	String proofOfDonation = "";
    	
    	Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("proofofdonation");
        proofOfDonation = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
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
        
        Donation savedDonation = donationRepository.getObject(idDonation);
        UUID recordIdDonation = (((DonationDecorator) savedDonation).getRecord()).getIdDonation();
        Donation donation = record.createDonation(vmjExchange, recordIdDonation);
        Donation goodsDonation = DonationFactory.createDonation("aisco.donation.goodsdonation.DonationImpl", idDonation, donation, quantity, 
        		proofOfDonation, status, objectName);
        
        return goodsDonation;
	}
    
    @Route(url = "call/goodsdonation/list-status")
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
    
    @Restricted(permissionName="UpdateGoodsDonation")
    @Route(url = "call/goodsdonation/updatestatus")
    public HashMap<String, Object> updateStatusDonation(VMJExchange vmjExchange) {
    	Map<String, Object> payload = vmjExchange.getPayload();
        List<String> keys = new ArrayList<String>(Arrays.asList("idDonation", "status"));
        List<Object> types = new ArrayList<Object>(Arrays.asList("", ""));
        vmjExchange.payloadChecker(keys, types, false);
        Donation donation = updateStatusGoodsDonation(vmjExchange);
        donationRepository.updateObject(donation);
        return donation.toHashMap();
    }

    private Donation updateStatusGoodsDonation(VMJExchange vmjExchange) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("idDonation");
        UUID idDonation = UUID.fromString(idStr);
        String idStatusStr = (String) payload.get("status");
        int idStatus = Integer.parseInt(idStatusStr);
        Status status = Status.findStatusById(idStatus);
        if (status == null)
            throw new BadRequestException("Status dengan id " + idStatus + " tidak ditemukan");
        Donation savedDonation = donationRepository.getObject(idDonation);
        ((DonationImpl) savedDonation).setStatus(status.getStatusName());
        return savedDonation;
    }


    // @Restriced(permission = "")
    @Route(url="call/goodsdonation/update")
    public HashMap<String, Object> updateDonation(VMJExchange vmjExchange){
		// to do implement the method
    	Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("idDonation");
        UUID idDonation = UUID.fromString(idStr);
        Donation donation = donationRepository.getObject(idDonation);
        donation = createDonation(vmjExchange, idDonation);
        donationRepository.updateObject(donation);
        return donation.toHashMap();
	}

	// @Restriced(permission = "")
    @Route(url="call/goodsdonation/detail")
    public HashMap<String, Object> getDonation(VMJExchange vmjExchange){
		return record.getDonation(vmjExchange);
	}

	@Restricted(permissionName = "ReadGoodsDonation")
    @Route(url="call/goodsdonation/list")
    public List<HashMap<String,Object>> getAllDonation(VMJExchange vmjExchange){
		AuthPayload authPayload = vmjExchange.getAuthPayload();
        String email = authPayload.getEmail();
    
        List<User> users = userRepository.getListObject("auth_user_impl", "email", email);
        User user = users.get(0);
        List<UserRole> userRoles = userRoleRepository.getListObject("auth_user_role_impl", "authuser", user.getId());
        List<Donation> donationList = donationRepository.getAllObject("donation_goodsdonation");
     
        if (checkRole(userRoles, "Donatur")) {
        	List<Donation> donationFilteredList = new ArrayList<>();
        	for (Donation donation : donationList) {    
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

	// @Restriced(permission = "")
    @Route(url="call/goodsdonation/delete")
    public List<HashMap<String, Object>> deleteDonation(VMJExchange vmjExchange) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("idDonation");
        UUID idDonation = UUID.fromString(idStr);
        donationRepository.deleteObject(idDonation);
        return getAllDonation(vmjExchange);
    }
    
    @Route(url="call/goodsdonation/accumulatedonation")
    public HashMap<String, Object> accumulateDonation(VMJExchange vmjExchange) {
    	String idProgramStr = vmjExchange.getGETParam("id");
        UUID idProgram = UUID.fromString(idProgramStr);
        List<Donation> goodsDonationList = donationRepository.getListObject("donation_goodsdonation", "status", "BERHASIL");
        
		long accumulatedDonation = 0L;
		for (Donation donation : goodsDonationList) {
			if (donation.getProgram().getIdProgram().equals(idProgram)) {
				accumulatedDonation += ((DonationImpl)donation).getQuantity();
			} 
		}
		
		HashMap<String, Object> accumulatedDonationMap = new HashMap<String,Object>();
        accumulatedDonationMap.put("accumulatedDonation", accumulatedDonation);
		
		return accumulatedDonationMap;
	}

}
