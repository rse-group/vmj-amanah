package aisco.withdraw.viainternationaldisbursement;

import aisco.withdraw.WithdrawFactory;
import aisco.withdraw.core.Withdraw;
import aisco.withdraw.core.WithdrawDecorator;
import aisco.withdraw.core.WithdrawResourceComponent;
import aisco.withdraw.core.WithdrawResourceDecorator;

import aisco.withdraw.viainternationaldisbursement.WithdrawImpl;

import aisco.program.core.Program;

import aisco.financialreport.core.FinancialReport;
import aisco.financialreport.core.FinancialReportComponent;
import aisco.financialreport.FinancialReportFactory;

import aisco.chartofaccount.core.ChartOfAccount;
import aisco.chartofaccount.core.ChartOfAccountComponent;

import paymentgateway.disbursement.core.DisbursementServiceComponent;
import paymentgateway.disbursement.core.Disbursement;
import paymentgateway.disbursement.internationaldisbursement.DisbursementServiceImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonObject;

import java.util.*;
import java.lang.reflect.Type;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import vmj.auth.annotations.Restricted;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import com.google.gson.Gson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class WithdrawResourceImpl extends WithdrawResourceDecorator {
	private final int DISBURSEMENT_COA_CODE = 60000;
	private final List<String> PAYMENT_SUCCESS_STATUS = new ArrayList<>(Arrays.asList("SUCCESSFUL"));
	private final List<String> PAYMENT_FAILED_STATUS = new ArrayList<>(Arrays.asList("FAILED"));
	private DisbursementServiceImpl disbursementServiceImpl;
	
    public WithdrawResourceImpl (WithdrawResourceComponent record, DisbursementServiceComponent recordService) {
		super(record);
        this.disbursementServiceImpl = new DisbursementServiceImpl(recordService);
    }


    @Route(url="call/viainternationaldisbursement/save")
    public HashMap<String,Object> saveWithdrawViaPaymentGateway(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Map<String, Object> payload = vmjExchange.getPayload();
		Withdraw withdraw = null;
		withdraw  = createWithdraw(vmjExchange);
		
		withdrawRepository.saveObject(withdraw);
		return withdraw.toHashMap();
	}
    

    public Withdraw createWithdraw(VMJExchange vmjExchange){
    	Map<String, Object> requestData = new HashMap<>();
    	    	
    	String status = "PENDING";
		String vendorName = (String) vmjExchange.getRequestBodyForm("vendor_name");
        		
		UUID idProgram = UUID.fromString((String) vmjExchange.getRequestBodyForm("idprogram"));
		Program program = programRepository.getObject(idProgram);
        String title = "Penarikan dana untuk Program " + program.getName();

		String internationalMoneyTransferExchangeRate = "";
		String internationalMoneyTransferFee = "";
		String internationalMoneyTransferSourceCountry = "";
		String internationalMoneyTransferDestinationCountry = "";
		String internationalMoneyTransferAmountInSenderCountry = "";
		String internationalMoneyTransferBeneficiaryCurrencyCode = "";
		String disbursementId = "";
        
        requestData = vmjExchange.getPayload();
       
    
        Gson gson = new Gson();
        String requestString = gson.toJson(requestData);
        
        String hostAddress = EnvUtilization.getEnvVariableHostAddress("AMANAH_HOST_BE");
        int portNum = EnvUtilization.getEnvVariablePortNumber("AMANAH_PORT_BE");
		
		try {
			Disbursement result = disbursementServiceImpl.createDisbursement(requestData);
			Map<String, Object> dataMap = result.toHashMap();
             
            disbursementId = String.valueOf(dataMap.get("id"));
            internationalMoneyTransferExchangeRate = String.valueOf(dataMap.get("exchange_rate"));
            internationalMoneyTransferFee = String.valueOf(dataMap.get("fee"));
            internationalMoneyTransferAmountInSenderCountry = String.valueOf(dataMap.get("amount_in_sender_currency"));
			internationalMoneyTransferSourceCountry = (String) dataMap.get("source_country");
			internationalMoneyTransferDestinationCountry = (String) dataMap.get("destination_country");
			internationalMoneyTransferBeneficiaryCurrencyCode = (String) dataMap.get("beneficiary_currency_code");
			
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
		

		Withdraw withdraw  = record.createWithdraw(vmjExchange, WithdrawImpl.class.getName());
		Withdraw viaDisbursement = WithdrawFactory.createWithdraw("aisco.withdraw.viainternationaldisbursement.WithdrawImpl", withdraw, status, vendorName,
				internationalMoneyTransferExchangeRate,
				internationalMoneyTransferFee,
				internationalMoneyTransferSourceCountry,
				internationalMoneyTransferDestinationCountry,
				internationalMoneyTransferAmountInSenderCountry,
				internationalMoneyTransferBeneficiaryCurrencyCode,
				disbursementId);
		
		System.out.println(withdraw);
		System.out.println(viaDisbursement);
		
		return viaDisbursement;
	}

    
    public Withdraw createWithdraw(VMJExchange vmjExchange, String objectName){
		Map<String, Object> payload = vmjExchange.getPayload();
    	String status = "PENDING";
		String title = (String) payload.get("title");
		String vendorName = (String) payload.get("vendor_name");
		String paymentLink = "";
		
		Withdraw withdraw  = record.createWithdraw(vmjExchange, WithdrawImpl.class.getName());
		Withdraw viaPaymentGateway = WithdrawFactory.createWithdraw("aisco.withdraw.viapaymentgateway.WithdrawImpl", withdraw, title, status, vendorName, paymentLink, objectName);
		
		return viaPaymentGateway;
	}
    
    public Withdraw createWithdraw(VMJExchange vmjExchange, UUID id){
		Map<String, Object> payload = vmjExchange.getPayload();
    	String status = "PENDING";
		String title = (String) payload.get("title");
		String vendorName = (String) payload.get("vendor_name");
		String paymentLink = "";

		
		Withdraw savedWithdraw = withdrawRepository.getObject(id);
        UUID recordId = (((WithdrawDecorator) savedWithdraw).getRecord()).getId();
        Withdraw withdraw = record.createWithdraw(vmjExchange, recordId);
        Withdraw viaPaymentGateway = WithdrawFactory.createWithdraw("aisco.withdraw.viapaymentgateway.WithdrawImpl", id, withdraw, title, status, vendorName, paymentLink, WithdrawImpl.class.getName());
		
		return viaPaymentGateway;
	}
    
    public Withdraw createWithdraw(VMJExchange vmjExchange, UUID id, String objectName){
		Map<String, Object> payload = vmjExchange.getPayload();
    	String status = "PENDING";
		String title = (String) payload.get("title");
		String vendorName = (String) payload.get("vendor_name");
		String paymentLink = "";

	
		
		Withdraw savedWithdraw = withdrawRepository.getObject(id);
        UUID recordId = (((WithdrawDecorator) savedWithdraw).getRecord()).getId();
        Withdraw withdraw = record.createWithdraw(vmjExchange, recordId);
        Withdraw viaPaymentGateway = WithdrawFactory.createWithdraw("aisco.withdraw.viapaymentgateway.WithdrawImpl", id, withdraw, title, status, vendorName, paymentLink, objectName);
        
		
		return viaPaymentGateway;
	}
    
    // @Restriced(permission = "")
    @Route(url="call/viapaymentgateway/update")
    public HashMap<String, Object> updateWithdraw(VMJExchange vmjExchange){
		// to do implement the method
    	Map<String, Object> payload = vmjExchange.getPayload();
        String idStr = (String) payload.get("id");
        UUID id = UUID.fromString(idStr);
        Withdraw withdraw = withdrawRepository.getObject(id);
        withdraw = createWithdraw(vmjExchange, id);
        withdrawRepository.updateObject(withdraw);
        return withdraw.toHashMap();
	}

	// @Restriced(permission = "")
    @Route(url="call/viainternationaldisbursement/detail")
    public HashMap<String, Object> getWithdraw(VMJExchange vmjExchange){
		return record.getWithdraw(vmjExchange);
	}
    
//    private String checkWithdrawPaymentStatus(WithdrawImpl withdrawViaPaymentGateway) {
//    	String paymentId = withdrawViaPaymentGateway.getPaymentId();
//        String vendorName = withdrawViaPaymentGateway.getVendorName();
//        
//        Map<String, Object> requestData = new HashMap<>();
//        requestData.put("id", paymentId);
//        requestData.put("vendor_name", vendorName);
//        
//        Gson gson = new Gson();
//        String requestString = gson.toJson(requestData);
//        
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("http://%s:%d/call/paymentstatus"))
//                .header("Content-Type", "application/json")
//                .POST(BodyPublishers.ofString(requestString))
//                .build();
//
//        try {
//            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
//            int statusCode = response.statusCode();
//            String responseBody = response.body();
//            HttpHeaders headers = response.headers();
//            System.out.println("Status Code: " + statusCode);
//            System.out.println("Response Body: " + responseBody);
//            System.out.println("Headers: " + headers);
//            
//            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
//            Map<String, Object> rawResponseMap = gson.fromJson(responseBody, mapType);
//            Map<String, Object> dataMap = (Map<String, Object>) rawResponseMap.get("data");
//            String status = (String) dataMap.get("status");
//            return status;
//           
//        } catch (Exception e) {
//            System.err.println("Error: " + e.getMessage());
//            return "PENDING";
//        }
//    }

	// @Restriced(permission = "")
    @Route(url="call/viainternationaldisbursement/list")
    public List<HashMap<String,Object>> getAllWithdraw(VMJExchange vmjExchange){
		List<Withdraw> withdrawList = withdrawRepository.getAllObject("withdraw_viainternationaldisbursement");
		System.out.println(withdrawList);
		return transformWithdrawListToHashMap(withdrawList);
	}

    public List<HashMap<String,Object>> transformWithdrawListToHashMap(List<Withdraw> WithdrawList){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < WithdrawList.size(); i++) {
            resultList.add(WithdrawList.get(i).toHashMap());
        }

        return resultList;
	}

	// @Restriced(permission = "")
    @Route(url="call/viainternationaldisbursement/delete")
    public List<HashMap<String,Object>> deleteWithdraw(VMJExchange vmjExchange){
		return getAllWithdraw(vmjExchange);
	}
    
    
    @Route(url="call/receiveinternationaldisbursementcallback")
    public void receiveDisbursementCallback(VMJExchange vmjExchange) {
    	Map<String, Object> payload = vmjExchange.getPayload();
    	String disbursementId = (String) payload.get("id");
    	String status = (String) payload.get("status");
    	
    	List<Withdraw> withdrawList = withdrawRepository.getListObject("withdraw_viainternationaldisbursement", "disbursementid", disbursementId);
    	Withdraw withdraw = withdrawList.get(0);
    	WithdrawImpl withdrawViaPaymentGateway = (WithdrawImpl) withdraw;
    	
    	//update status of withdraw
    	if (withdrawViaPaymentGateway.getStatus().equals("PENDING")) {
            if (status.equals("DONE")) {
            	withdrawViaPaymentGateway.setStatus("BERHASIL");
            	
            	//createExpense
            	String date = withdraw.getDate();
            	long amount = withdraw.getAmount();
            	String description = withdraw.getDescription();
            	Program program = withdraw.getProgram();
            	ChartOfAccount coa = withdrawRepository.getProxyObject(ChartOfAccountComponent.class, DISBURSEMENT_COA_CODE);
            	FinancialReport financialReport = FinancialReportFactory.createFinancialReport("aisco.financialreport.core.FinancialReportImpl", date, amount, description, program, coa);
        		FinancialReport expense = FinancialReportFactory.createFinancialReport("aisco.financialreport.expense.FinancialReportImpl", financialReport);
        		financialReportRepository.saveObject(expense);
                withdraw.setExpense((FinancialReportComponent) expense);
                
            } else if (status.equals("CANCELLED")) {
            	withdrawViaPaymentGateway.setStatus("DITUNDA");
            }
            withdrawRepository.updateObject(withdraw);
        }
    }
    	

}
