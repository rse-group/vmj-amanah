package aisco.withdraw.core;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.FieldValidationException;
import vmj.routing.route.exceptions.ParameterNotFoundException;

import aisco.withdraw.WithdrawFactory;

import aisco.program.core.*;
import aisco.chartofaccount.core.*;
import aisco.financialreport.core.*;
import aisco.financialreport.FinancialReportFactory;

import vmj.auth.annotations.Restricted;
import vmj.auth.core.AuthPayload;
import vmj.auth.model.core.*;
import vmj.auth.utils.*;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Claim;

public class WithdrawResourceImpl extends WithdrawResourceComponent {

    private final int DONATION_COA_CODE = 42010;

    @Restricted(permissionName = "ModifyWithdrawReportImpl")
    @Route(url = "call/withdraw/save")
    public List<HashMap<String, Object>> saveWithdraw(VMJExchange vmjExchange) {
        Withdraw withdraw = createWithdraw(vmjExchange);
        withdrawRepository.saveObject(withdraw);
        System.out.println(withdraw);
        return getAllWithdraw(vmjExchange);
    }

    public FinancialReport createExpense(VMJExchange vmjExchange) {
        Map<String, Object> payload = vmjExchange.getPayload();

        String description = (String) payload.get("description");
        String paymentMethod = (String) payload.get("paymentMethod");
        String idProgramStr = (String) payload.get("idProgram");
        String date = (String) payload.get("date");
        String amountStr = (String) payload.get("amount");
        long amount = Long.parseLong(amountStr);

        Program program = null;
        if (idProgramStr != null) {
        	UUID idProgram = UUID.fromString(idProgramStr);
        	program = programRepository.getObject(idProgram);
        }

		FinancialReport financialReport = FinancialReportFactory.createFinancialReport("aisco.financialreport.core.FinancialReportImpl");
		financialReport.setAmount(amount);
		FinancialReport expense = FinancialReportFactory.createFinancialReport("aisco.financialreport.expense.FinancialReportImpl", financialReport, paymentMethod);
		return expense;
	}
    
    public Withdraw createWithdraw(VMJExchange vmjExchange) {
        return createWithdraw(vmjExchange, UUID.randomUUID());
    }

    public Withdraw createWithdraw(VMJExchange vmjExchange, String objectName) {
        return createWithdraw(vmjExchange, UUID.randomUUID(), objectName);
    }

    public Withdraw createWithdraw(VMJExchange vmjExchange, UUID id, String objectName) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String disbursementMethod = (String) payload.get("disbursementMethod");
        String description = (String) payload.get("description");

        String idProgramStr = (String) payload.get("idprogram");
        Program program = null;
        if (idProgramStr != null) {
        	UUID idProgram = UUID.fromString(idProgramStr);
//            program = withdrawRepository.getProxyObject(ProgramComponent.class, idProgram);
            program = programRepository.getObject(idProgram);
        }
        
//        String date = (String) payload.get("date");
        // set date as today
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = currentDate.format(formatter);

      
        
        long amount = 0L;
        if (payload.get("amount") != null)  {
        	String amountStr = (String) payload.get("amount");
        	System.out.println(amountStr);
        	amount = Long.parseLong(amountStr);
        	System.out.println(amount);
        }
        if (amount < 0) {
            throw new FieldValidationException("Nilai amount harus lebih besar dari 0");
        }
        
        Withdraw withdraw = WithdrawFactory.createWithdraw("aisco.withdraw.core.WithdrawImpl", id, amount, date, program, description, disbursementMethod, objectName);
        
     // assign User
        withdraw = assignUser(vmjExchange, withdraw);
        return withdraw;
    }
    
    public Withdraw createWithdraw(VMJExchange vmjExchange, UUID id) {
        Map<String, Object> payload = vmjExchange.getPayload();
        String name = (String) payload.get("name");
        String email = (String) payload.get("email");
        String phone = (String) payload.get("phone");
        String description = (String) payload.get("description");
        String paymentMethod = "";
        if (payload.get("paymentMethod") != null) {
        	paymentMethod = (String) payload.get("paymentMethod");
        }
        String idProgramStr = (String) payload.get("idprogram");
        Program program = null;
        if (idProgramStr != null) {
        	UUID idProgram = UUID.fromString(idProgramStr);
            program = withdrawRepository.getProxyObject(ProgramComponent.class, idProgram);
        }
        String date = (String) payload.get("date");
        long amount = 0L;
        if (payload.get("amount") != null)  {
        	String amountStr = (String) payload.get("amount");
        	amount = Long.parseLong(amountStr);
        }
        if (amount < 0) {
            throw new FieldValidationException("Nilai amount harus lebih besar dari 0");
        }
        Withdraw withdraw = WithdrawFactory.createWithdraw("aisco.withdraw.core.WithdrawImpl", id, name, email, phone,
                amount, paymentMethod, date, program, description);
        
     // assign User
        withdraw = assignUser(vmjExchange, withdraw);
         
        return withdraw;
    }

    @Restricted(permissionName = "ModifyWithdrawReportImpl")
    @Route(url = "call/withdraw/update")
    public HashMap<String, Object> updateWithdraw(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID id = UUID.fromString(idStr);
        Withdraw withdraw = createWithdraw(vmjExchange, id);
        withdrawRepository.updateObject(withdraw);
        return withdraw.toHashMap();
    }

    @Route(url = "call/withdraw/detail")
    public HashMap<String, Object> getWithdraw(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("id");
        UUID id = UUID.fromString(idStr);
        Withdraw withdraw = withdrawRepository.getObject(id);
        System.out.println(withdraw);
        try {
            return withdraw.toHashMap();
        } catch (NullPointerException e) {
            HashMap<String, Object> blank = new HashMap<>();
            blank.put("error", "Missing Params");
            return blank;
        }
    }

    @Route(url = "call/withdraw/list")
    public List<HashMap<String, Object>> getAllWithdraw(VMJExchange vmjExchange) {
        List<Withdraw> withdrawList = withdrawRepository.getAllObject("withdraw_impl", WithdrawImpl.class.getName());
        return transformWithdrawListToHashMap(withdrawList);
    }

    // TODO: bisa dimasukin ke kelas util
    public List<HashMap<String, Object>> transformWithdrawListToHashMap(List<Withdraw> withdrawList) {
        System.out.println("Masuk transformWithdrawListToHashMap at WithdrawResourceImpl at Core");
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < withdrawList.size(); i++) {
            resultList.add(withdrawList.get(i).toHashMap());
        }

        return resultList;
    }
    
    public Withdraw assignUser(VMJExchange vmjExchange, Withdraw withdraw) {
    	try {
	        String token = vmjExchange.getGETParam("token");
	        DecodedJWT decodedJwt = JWTUtils.decodeJWT(token);
	        Claim emailClaim = decodedJwt.getClaim("email");
	        String emailAuth = emailClaim.asString();
	        List<User> users = userRepository.getListObject("auth_user_impl", "email", emailAuth);
	        User user = users.get(0);
	        withdraw.setUser(user);	
	        
        } catch (ParameterNotFoundException e) {
        	return withdraw;
        }
    	return withdraw;
    }

    @Restricted(permissionName = "ModifyWithdrawReportImpl")
    @Route(url = "call/withdraw/delete")
    public List<HashMap<String, Object>> deleteWithdraw(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        UUID id = UUID.fromString(idStr);
        withdrawRepository.deleteObject(id);
        return getAllWithdraw(vmjExchange);
    }
}
