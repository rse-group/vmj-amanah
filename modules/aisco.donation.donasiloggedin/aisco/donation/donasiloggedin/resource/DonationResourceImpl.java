package aisco.donation.donasiloggedin;

import java.util.*;
import java.lang.RuntimeException;
import java.net.URLConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import vmj.auth.core.AuthPayload;
import vmj.auth.model.core.*;
import vmj.auth.model.core.UserComponent;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import vmj.hibernate.integrator.RepositoryUtil;

import aisco.donation.DonationFactory;
import aisco.donation.core.DonationResourceDecorator;
import aisco.donation.core.DonationResourceComponent;
import aisco.donation.core.Donation;
import aisco.donation.core.DonationDecorator;
import aisco.donation.core.DonationComponent;
import aisco.financialreport.core.*;
import aisco.financialreport.FinancialReportFactory;
import aisco.program.core.*;
import aisco.chartofaccount.core.*;

import vmj.auth.annotations.Restricted;


public class DonationResourceImpl extends DonationResourceDecorator {
    public DonationResourceImpl (DonationResourceComponent record) {
        // to do implement the method
		super(record);
    }

    @Restricted(permissionName = "CreateDonasiLoggedIn")
    @Route(url="call/donasiloggedin/save")
    public List<HashMap<String,Object>> saveDonation(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		List<String> keys = new ArrayList<String>(Arrays.asList("name", "email", "phone",
                "description", "paymentMethod", "idprogram", "date", "amount"));
        List<Object> types = new ArrayList<Object>(
                Arrays.asList("", "", "", "", "", "", "", ""));
        vmjExchange.payloadChecker(keys, types, false);
        Donation donation = createDonation(vmjExchange);
        donationRepository.saveObject(donation);
        System.out.println(donation);
		return getAllIndividualDonation(vmjExchange);
	}

    public Donation createDonation(VMJExchange vmjExchange){
		Map<String, Object> payload = vmjExchange.getPayload();
		
        AuthPayload authPayload = vmjExchange.getAuthPayload();
        String email = authPayload.getEmail();
        System.out.println(email);
        
        RepositoryUtil<User> userDao = new RepositoryUtil<User>(vmj.auth.model.core.UserComponent.class);
        List<User> users = userDao.getListObject("auth_user_impl", "email", email);
        User user = users.get(0);
		int donaturId = user.getId();

		Donation donation = record.createDonation(vmjExchange, DonationImpl.class.getName());
		Donation donationLoggedIn = DonationFactory.createDonation("aisco.donation.donasiloggedin.DonationImpl", donation, donaturId);
		return donationLoggedIn;
	}

	public Donation createDonation(VMJExchange vmjExchange, String objectName){
		Map<String, Object> payload = vmjExchange.getPayload();
		
        AuthPayload authPayload = vmjExchange.getAuthPayload();
        String email = authPayload.getEmail();
        System.out.println(email);
        
        RepositoryUtil<User> userDao = new RepositoryUtil<User>(vmj.auth.model.core.UserComponent.class);
        List<User> users = userDao.getListObject("auth_user_impl", "email", email);
        User user = users.get(0);
		int donaturId = user.getId();

		Donation donation = record.createDonation(vmjExchange, DonationImpl.class.getName());
		Donation donationLoggedIn = DonationFactory.createDonation("aisco.donation.donasiloggedin.DonationImpl", donation, donaturId, objectName);
		return donationLoggedIn;
	}

	public Donation createDonation(VMJExchange vmjExchange, UUID id){
		Map<String, Object> payload = vmjExchange.getPayload();
		
        AuthPayload authPayload = vmjExchange.getAuthPayload();
        String email = authPayload.getEmail();
        System.out.println(email);
        
        RepositoryUtil<User> userDao = new RepositoryUtil<User>(vmj.auth.model.core.UserComponent.class);
        List<User> users = userDao.getListObject("auth_user_impl", "email", email);
        User user = users.get(0);
		int donaturId = user.getId();

		Donation savedDonation = donationRepository.getObject(id);
        UUID recordId = (((DonationDecorator) savedDonation).getRecord()).getId();
		Donation donation = record.createDonation(vmjExchange, recordId);
		Donation donationLoggedIn = DonationFactory.createDonation("aisco.donation.donasiloggedin.DonationImpl", id, donation, donaturId, DonationImpl.class.getName());
		return donationLoggedIn;
	}

	public Donation createDonation(VMJExchange vmjExchange, UUID id, String objectName){
		Map<String, Object> payload = vmjExchange.getPayload();
		
        AuthPayload authPayload = vmjExchange.getAuthPayload();
        String email = authPayload.getEmail();
        System.out.println(email);
        
        RepositoryUtil<User> userDao = new RepositoryUtil<User>(vmj.auth.model.core.UserComponent.class);
        List<User> users = userDao.getListObject("auth_user_impl", "email", email);
        User user = users.get(0);
		int donaturId = user.getId();

		Donation savedDonation = donationRepository.getObject(id);
        UUID recordId = (((DonationDecorator) savedDonation).getRecord()).getId();
		Donation donation = record.createDonation(vmjExchange, recordId);
		Donation donationLoggedIn = DonationFactory.createDonation("aisco.donation.donasiloggedin.DonationImpl", id, donation, donaturId, objectName);
		return donationLoggedIn;
	}

    @Restricted(permissionName = "UpdateDonasiLoggedIn")
    @Route(url="call/donasiloggedin/update")
    public HashMap<String, Object> updateDonation(VMJExchange vmjExchange){
		// to do implement the method
		Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("id");
        UUID id = UUID.fromString(idStr);
        Donation donation = donationRepository.getObject(id);
        donation = createDonation(vmjExchange, id);
        donationRepository.updateObject(donation);
        return donation.toHashMap();
	}

	// @Restriced(permission = "")
    @Route(url="call/donasiloggedin/detail")
    public HashMap<String, Object> getDonation(VMJExchange vmjExchange){
		return record.getDonation(vmjExchange);
	}

	@Restricted(permissionName = "administrator")
    @Route(url="call/donasiloggedin/listall")
    public List<HashMap<String,Object>> getAllDonation(VMJExchange vmjExchange){
		List<Donation> donationList = donationRepository.getAllObject("donation_donasiloggedin");
		return transformDonationListToHashMap(donationList);
	}

	@Restricted(permissionName = "ReadDonasiLoggedIn")
    @Route(url="call/donasiloggedin/list")
    public List<HashMap<String,Object>> getAllIndividualDonation(VMJExchange vmjExchange){
		AuthPayload authPayload = vmjExchange.getAuthPayload();
        String email = authPayload.getEmail();
        
        RepositoryUtil<User> userDao = new RepositoryUtil<User>(vmj.auth.model.core.UserComponent.class);
        List<User> users = userDao.getListObject("auth_user_impl", "email", email);
        User user = users.get(0);
		int donaturId = user.getId();

		List<Donation> donationList = donationRepository.getListObject("donation_donasiloggedin", "donaturid", donaturId);
		return transformDonationListToHashMap(donationList);
	}

    // public List<HashMap<String,Object>> transformDonationListToHashMap(List<Donation> DonationList){
	// 	List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
    //     for(int i = 0; i < DonationList.size(); i++) {
    //         resultList.add(DonationList.get(i).toHashMap());
    //     }

    //     return resultList;
	// }

	// @Restriced(permission = "")
    @Route(url="call/donasiloggedin/delete")
    public List<HashMap<String,Object>> deleteDonation(VMJExchange vmjExchange){
		Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("id");
        UUID id = UUID.fromString(idStr);
        donationRepository.deleteObject(id);
        return getAllIndividualDonation(vmjExchange);
	}

}
