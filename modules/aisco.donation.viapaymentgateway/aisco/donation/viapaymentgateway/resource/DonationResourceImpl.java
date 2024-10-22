package aisco.donation.viapaymentgateway;

import aisco.donation.DonationFactory;
import aisco.donation.core.Donation;
import aisco.donation.core.DonationDecorator;
//import aisco.donation.core.DonationImpl;
import aisco.donation.core.DonationResourceComponent;
import aisco.donation.core.DonationResourceDecorator;

import aisco.donation.viapaymentgateway.DonationImpl;

import aisco.program.core.Program;

import aisco.financialreport.core.FinancialReport;
import aisco.financialreport.core.FinancialReportComponent;
import aisco.financialreport.FinancialReportFactory;

import aisco.chartofaccount.core.ChartOfAccount;
import aisco.chartofaccount.core.ChartOfAccountComponent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonObject;

import java.util.*;
import java.lang.reflect.Type;
//import java.lang.RuntimeException;
//import java.net.URLConnection;
//import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

//import vmj.auth.core.AuthPayload;
//import vmj.auth.model.core.*;
import vmj.auth.annotations.Restricted;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
//import vmj.routing.route.exceptions.*;

import com.google.gson.Gson;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class DonationResourceImpl extends DonationResourceDecorator {
	private final int DONATION_COA_CODE = 42010;
	private final List<String> PAYMENT_SUCCESS_STATUS = new ArrayList<>(Arrays.asList("SUCCESSFUL"));
	private final List<String> PAYMENT_FAILED_STATUS = new ArrayList<>(Arrays.asList("FAILED"));
	PaymentService creditcardPaymentService; 
	
	
    public DonationResourceImpl (DonationResourceComponent record) {
		super(record);
		creditcardPaymentService = 
				PaymentServiceFactory.createPaymentService(
					"paymentgateway.payment.creditcard.PaymentServiceImpl", 
						PaymentServiceFactory.createPaymentService(
							"paymentgateway.payment.core.PaymentServiceImpl"
						);		
				);	
    }


    @Route(url="call/viapaymentgateway/save")
    public HashMap<String,Object> saveDonationViaPaymentGateway(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Map<String, Object> payload = vmjExchange.getPayload();
		Donation donation = null;
		if (!payload.containsKey("vendor_name")) {
			donation = createDonationViaFlipPaymentLink(vmjExchange);
		} else {
			donation  = createDonation(vmjExchange);
		}

		donationRepository.saveObject(donation);
		return donation.toHashMap();
	}
    
    public Donation createDonationViaFlipPaymentLink(VMJExchange vmjExchange) {
    	Map<String, Object> requestData = new HashMap<>();
    	
    	String status = "PENDING";
    	String paymentMethod = "Payment Link";
		
		UUID idProgram = UUID.fromString((String) vmjExchange.getRequestBodyForm("idprogram"));
		Program program = programRepository.getObject(idProgram);
        String title = "Donasi untuk Program " + program.getName();
        		
        String amount = (String) vmjExchange.getRequestBodyForm("amount");
        String senderEmail = (String) vmjExchange.getRequestBodyForm("email");
        String senderName = (String) vmjExchange.getRequestBodyForm("name");
        String vendorName = "Flip";
        
        String paymentId = "";
        
        String paymentLink = "";
        
        String eWalletLink = "";
        String vaNumber = "";
        String retailPaymentCode = "";
        String retailOutlet = "";
        String paymentCheckoutUrl = "";
        String invoiceTransactionUrl = "";
        String debitCardRedirectUrl = "";
        String creditCardRedirectUrl = "";
        
        requestData.put("title", title);
        requestData.put("name", senderName);
        requestData.put("email",senderEmail);
        requestData.put("amount",amount);
        requestData.put("vendor_name", vendorName);

		String hostAddress = EnvUtilization.getEnvVariableHostAddress("AMANAH_HOST_BE");
        int portNum = EnvUtilization.getEnvVariablePortNumber("AMANAH_PORT_BE");

    
        Gson gson = new Gson();
        String requestString = gson.toJson(requestData);
		String url = String.format("http://%s:%d/call/paymentlink", hostAddress, portNum);
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(requestString))
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();
            HttpHeaders headers = response.headers();
            System.out.println("Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
            System.out.println("Headers: " + headers);
         
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            System.out.println(mapType);
            Map<String, Object> rawResponseMap = gson.fromJson(responseBody, mapType);
            System.out.println(rawResponseMap);
            Map<String, Object> dataMap = (Map<String, Object>) rawResponseMap.get("data");
            System.out.println(dataMap);
            
            paymentLink = (String) dataMap.get("paymentLink");
            
            int paymentIdInt = ((Double) dataMap.get("id")).intValue();
        	paymentId = String.valueOf(paymentIdInt);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        Donation donation  = record.createDonation(vmjExchange, DonationImpl.class.getName());
		Donation viaPaymentGateway = DonationFactory.createDonation("aisco.donation.viapaymentgateway.DonationImpl", donation, title, status, vendorName, 
				paymentId, paymentLink, eWalletLink, vaNumber,
				retailPaymentCode, paymentCheckoutUrl, invoiceTransactionUrl,
				debitCardRedirectUrl, creditCardRedirectUrl);

		return viaPaymentGateway;
    }
    
    public Donation createDonation(VMJExchange vmjExchange){
    	Map<String, Object> requestData = new HashMap<>();
    	
    	String status = "PENDING";
		String vendorName = (String) vmjExchange.getRequestBodyForm("vendor_name");
		String paymentMethod = (String) vmjExchange.getRequestBodyForm("paymentMethod");
		
		UUID idProgram = UUID.fromString((String) vmjExchange.getRequestBodyForm("idprogram"));
		Program program = programRepository.getObject(idProgram);
        String title = "Donasi " + program.getName();
        
        Map<String, Object> payload = vmjExchange.getPayload();
        payload.put("title", title);
        
        String paymentId = "";
        
        String paymentLink = "";
        String eWalletLink = "";
        String vaNumber = "";
        String bankCode = "";
        String retailPaymentCode = "";
        String paymentCheckoutUrl = "";
        String invoiceTransactionUrl = "";
        String debitCardRedirectUrl = "";
        String creditCardRedirectUrl = "";
    
        requestData = payload;
        Gson gson = new Gson();
        String requestString = gson.toJson(requestData);

		String hostAddress = EnvUtilization.getEnvVariableHostAddress("AMANAH_HOST_BE");
        int portNum = EnvUtilization.getEnvVariablePortNumber("AMANAH_PORT_BE");
		
		if(paymentMethod.equals("paymentlink")){
	        HttpClient client = HttpClient.newHttpClient();
			String url = String.format("http://%s:%d/call/paymentlink", hostAddress, portNum);
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .header("Content-Type", "application/json")
	                .POST(BodyPublishers.ofString(requestString))
	                .build();

	        try {
	            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	            int statusCode = response.statusCode();
	            String responseBody = response.body();
	            HttpHeaders headers = response.headers();
	            System.out.println("Status Code: " + statusCode);
	            System.out.println("Response Body: " + responseBody);
	            System.out.println("Headers: " + headers);
	         
	            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
	            System.out.println(mapType);
	            Map<String, Object> rawResponseMap = gson.fromJson(responseBody, mapType);
	            System.out.println(rawResponseMap);
	            Map<String, Object> dataMap = (Map<String, Object>) rawResponseMap.get("data");
	            System.out.println(dataMap);
	            
	            paymentLink = (String) dataMap.get("paymentLink");
	            
	            int paymentIdInt = ((Double) dataMap.get("id")).intValue();
            	paymentId = String.valueOf(paymentIdInt);
	            
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	        }
			
		}else if (paymentMethod.equals("ewallet")) {
	        HttpClient client = HttpClient.newHttpClient();
			String url = String.format("http://%s:%d/call/ewallet", hostAddress, portNum);
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .header("Content-Type", "application/json")
	                .POST(BodyPublishers.ofString(requestString))
	                .build();

	        try {
	            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	            int statusCode = response.statusCode();
	            String responseBody = response.body();
	            HttpHeaders headers = response.headers();
	            System.out.println("Status Code: " + statusCode);
	            System.out.println("Response Body: " + responseBody);
	            System.out.println("Headers: " + headers);
	            
	            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
	            Map<String, Object> rawResponseMap = gson.fromJson(responseBody, mapType);
	            Map<String, Object> dataMap = (Map<String, Object>) rawResponseMap.get("data");
	            eWalletLink = (String) dataMap.get("eWalletUrl");

	            int paymentIdInt = ((Double) dataMap.get("id")).intValue();
            	paymentId = String.valueOf(paymentIdInt);
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	        }
			
		}else if (paymentMethod.equals("virtualaccount")) {
	        HttpClient client = HttpClient.newHttpClient();
			String url = String.format("http://%s:%d/call/virtualaccount", hostAddress, portNum);
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .header("Content-Type", "application/json")
	                .POST(BodyPublishers.ofString(requestString))
	                .build();

	        try {
	            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	            int statusCode = response.statusCode();
	            String responseBody = response.body();
	            HttpHeaders headers = response.headers();
	            
	            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
	            Map<String, Object> rawResponseMap = gson.fromJson(responseBody, mapType);
	            Map<String, Object> dataMap = (Map<String, Object>) rawResponseMap.get("data");
	            bankCode = (String) dataMap.get("bankCode");
	            vaNumber = (String) dataMap.get("vaAccountNumber");

	            int paymentIdInt = ((Double) dataMap.get("id")).intValue();
            	paymentId = String.valueOf(paymentIdInt);
	            
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	        }
			
		}else if (paymentMethod.equals("paymentrouting")) {
	        HttpClient client = HttpClient.newHttpClient();
			String url = String.format("http://%s:%d/call/paymentrouting", hostAddress, portNum);
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .header("Content-Type", "application/json")
	                .POST(BodyPublishers.ofString(requestString))
	                .build();

	        try {
	            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	            int statusCode = response.statusCode();
	            String responseBody = response.body();
	            HttpHeaders headers = response.headers();
	            
	            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
	            Map<String, Object> rawResponseMap = gson.fromJson(responseBody, mapType);
	            Map<String, Object> dataMap = (Map<String, Object>) rawResponseMap.get("data");
	            paymentCheckoutUrl = (String) dataMap.get("payment_checkout_url");

	            int paymentIdInt = ((Double) dataMap.get("id")).intValue();
            	paymentId = String.valueOf(paymentIdInt);
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	        }
			
		}else if (paymentMethod.equals("invoice")) {
	        HttpClient client = HttpClient.newHttpClient();
			String url = String.format("http://%s:%d/call/invoice", hostAddress, portNum);
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .header("Content-Type", "application/json")
	                .POST(BodyPublishers.ofString(requestString))
	                .build();

	        try {
	            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	            int statusCode = response.statusCode();
	            String responseBody = response.body();
	            HttpHeaders headers = response.headers();
	            
	            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
	            Map<String, Object> rawResponseMap = gson.fromJson(responseBody, mapType);
	            Map<String, Object> dataMap = (Map<String, Object>) rawResponseMap.get("data");
	            invoiceTransactionUrl = (String) dataMap.get("transactionUrl");

	            int paymentIdInt = ((Double) dataMap.get("id")).intValue();
            	paymentId = String.valueOf(paymentIdInt);
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	        }
			
		}else if (paymentMethod.equals("retailoutlet")) {
	        HttpClient client = HttpClient.newHttpClient();
			String url = String.format("http://%s:%d/call/retailoutlet", hostAddress, portNum);
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .header("Content-Type", "application/json")
	                .POST(BodyPublishers.ofString(requestString))
	                .build();

	        try {
	            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	            int statusCode = response.statusCode();
	            String responseBody = response.body();
	            HttpHeaders headers = response.headers();
	            
	            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
	            Map<String, Object> rawResponseMap = gson.fromJson(responseBody, mapType);
	            Map<String, Object> dataMap = (Map<String, Object>) rawResponseMap.get("data");
	            retailPaymentCode = (String) dataMap.get("retailPaymentCode");

	            int paymentIdInt = ((Double) dataMap.get("id")).intValue();
            	paymentId = String.valueOf(paymentIdInt);
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	        }
			
		}else if (paymentMethod.equals("debitcard")) {
	        HttpClient client = HttpClient.newHttpClient();
			String url = String.format("http://%s:%d/call/debitcard", hostAddress, portNum);
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .header("Content-Type", "application/json")
	                .POST(BodyPublishers.ofString(requestString))
	                .build();

	        try {
	            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	            int statusCode = response.statusCode();
	            String responseBody = response.body();
	            HttpHeaders headers = response.headers();
	           
	            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
	            Map<String, Object> rawResponseMap = gson.fromJson(responseBody, mapType);
	            Map<String, Object> dataMap = (Map<String, Object>) rawResponseMap.get("data");
	            debitCardRedirectUrl = (String) dataMap.get("directDebitUrl");

	            int paymentIdInt = ((Double) dataMap.get("id")).intValue();
            	paymentId = String.valueOf(paymentIdInt);
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	        }
	        
			
		}else if (paymentMethod.equals("creditcard")) {
	        try {
	        	Payment result = creditcardPaymentService.createPayment(payload);
				Map<String, Object> dataMap = result.toHashMap();
	            
				creditCardRedirectUrl = (String) dataMap.get("redirect_url");
	            status = (String) dataMap.get("statusCreditPayment");
	            
	            int paymentIdInt = ((Double) dataMap.get("id")).intValue();
            	paymentId = String.valueOf(paymentIdInt);
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	        }
		}
		
		Donation donation  = record.createDonation(vmjExchange, DonationImpl.class.getName());
		Donation viaPaymentGateway = DonationFactory.createDonation("aisco.donation.viapaymentgateway.DonationImpl", donation, title, status, vendorName, 
				paymentId, paymentLink, eWalletLink, vaNumber, bankCode,
				retailPaymentCode, paymentCheckoutUrl, invoiceTransactionUrl,
				debitCardRedirectUrl, creditCardRedirectUrl);
		return viaPaymentGateway;

	}
    
    public Donation createDonation(VMJExchange vmjExchange, String objectName){
		Map<String, Object> payload = vmjExchange.getPayload();
    	String status = "PENDING";
		String title = (String) payload.get("title");
		String vendorName = (String) payload.get("vendor_name");
		String paymentLink = "";
		
		Donation donation  = record.createDonation(vmjExchange, DonationImpl.class.getName());
		Donation viaPaymentGateway = DonationFactory.createDonation("aisco.donation.viapaymentgateway.DonationImpl", donation, title, status, vendorName, paymentLink, objectName);
		
		return viaPaymentGateway;
	}
    
    public Donation createDonation(VMJExchange vmjExchange, UUID id){
		Map<String, Object> payload = vmjExchange.getPayload();
    	String status = "PENDING";
		String title = (String) payload.get("title");
		String vendorName = (String) payload.get("vendor_name");
		String paymentLink = "";

		
		Donation savedDonation = donationRepository.getObject(id);
        UUID recordId = (((DonationDecorator) savedDonation).getRecord()).getId();
        Donation donation = record.createDonation(vmjExchange, recordId);
        Donation viaPaymentGateway = DonationFactory.createDonation("aisco.donation.viapaymentgateway.DonationImpl", id, donation, title, status, vendorName, paymentLink, DonationImpl.class.getName());
		
		return viaPaymentGateway;
	}
    
    public Donation createDonation(VMJExchange vmjExchange, UUID id, String objectName){
		Map<String, Object> payload = vmjExchange.getPayload();
    	String status = "PENDING";
		String title = (String) payload.get("title");
		String vendorName = (String) payload.get("vendor_name");
		String paymentLink = "";

	
		
		Donation savedDonation = donationRepository.getObject(id);
        UUID recordId = (((DonationDecorator) savedDonation).getRecord()).getId();
        Donation donation = record.createDonation(vmjExchange, recordId);
        Donation viaPaymentGateway = DonationFactory.createDonation("aisco.donation.viapaymentgateway.DonationImpl", id, donation, title, status, vendorName, paymentLink, objectName);
        
		
		return viaPaymentGateway;
	}
    // @Restriced(permission = "")
    @Route(url="call/viapaymentgateway/update")
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
    @Route(url="call/viapaymentgateway/detail")
    public HashMap<String, Object> getDonation(VMJExchange vmjExchange){
		return record.getDonation(vmjExchange);
	}
    
    private String checkDonationPaymentStatus(DonationImpl donationViaPaymentGateway) {
    	String paymentId = donationViaPaymentGateway.getPaymentId();
        String vendorName = donationViaPaymentGateway.getVendorName();
        
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("id", paymentId);
        requestData.put("vendor_name", vendorName);
        
        Gson gson = new Gson();
        String requestString = gson.toJson(requestData);

		String hostAddress = EnvUtilization.getEnvVariableHostAddress("AMANAH_HOST_BE");
        int portNum = EnvUtilization.getEnvVariablePortNumber("AMANAH_PORT_BE");
        
        HttpClient client = HttpClient.newHttpClient();
		String url = String.format("http://%s:%d/call/paymentstatus", hostAddress, portNum);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(requestString))
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();
            HttpHeaders headers = response.headers();
            System.out.println("Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
            System.out.println("Headers: " + headers);
            
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            Map<String, Object> rawResponseMap = gson.fromJson(responseBody, mapType);
            Map<String, Object> dataMap = (Map<String, Object>) rawResponseMap.get("data");
            String status = (String) dataMap.get("status");
            return status;
           
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return "PENDING";
        }
    }

	// @Restriced(permission = "")
    @Route(url="call/viapaymentgateway/list")
    public List<HashMap<String,Object>> getAllDonation(VMJExchange vmjExchange){
		List<Donation> donationList = donationRepository.getAllObject("donation_viapaymentgateway");
		System.out.println(donationList);
		return transformDonationListToHashMap(donationList);
	}

    public List<HashMap<String,Object>> transformDonationListToHashMap(List<Donation> DonationList){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < DonationList.size(); i++) {
            resultList.add(DonationList.get(i).toHashMap());
        }

        return resultList;
	}

	// @Restriced(permission = "")
    @Route(url="call/viapaymentgateway/delete")
    public List<HashMap<String,Object>> deleteDonation(VMJExchange vmjExchange){
		return getAllDonation(vmjExchange);
	}
    
    @Route(url="call/receivecallback")
    public void receiveCallback(VMJExchange vmjExchange) {
    	Map<String, Object> payload = vmjExchange.getPayload();
    	String paymentId = (String) payload.get("id");
    	String status = (String) payload.get("status");
    	
    	//getDonation with given paymentId
    	List<Donation> donationList = donationRepository.getListObject("donation_viapaymentgateway", "paymentid", paymentId);
    	Donation donation = donationList.get(0);
    	DonationImpl donationViaPaymentGateway = (DonationImpl) donation;
    	
    	//update status of donation
    	if (donationViaPaymentGateway.getStatus().equals("PENDING")) {
            if (status.equals("SUCCESSFUL")) {
            	donationViaPaymentGateway.setStatus("BERHASIL");
            	
            	//createIncome
            	String date = donation.getDate();
            	long amount = donation.getAmount();
            	String description = donation.getDescription();
            	Program program = donation.getProgram();
            	String paymentMethod = donation.getPaymentMethod();
            	ChartOfAccount coa = donationRepository.getProxyObject(ChartOfAccountComponent.class, DONATION_COA_CODE);
            	FinancialReport financialReport = FinancialReportFactory.createFinancialReport("aisco.financialreport.core.FinancialReportImpl", date, amount, description, program, coa);
        		FinancialReport income = FinancialReportFactory.createFinancialReport("aisco.financialreport.income.FinancialReportImpl", financialReport, paymentMethod);
        		financialReportRepository.saveObject(income);
                donation.setIncome((FinancialReportComponent) income);
                
            } else if (status.equals("FAILED")) {
            	donationViaPaymentGateway.setStatus("DITOLAK");
            }
            donationRepository.updateObject(donation);
        }
    }
    	

}
