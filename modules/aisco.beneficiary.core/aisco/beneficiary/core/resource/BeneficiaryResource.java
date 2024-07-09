package aisco.beneficiary.core;
import java.util.*;

import vmj.routing.route.VMJExchange;

public interface BeneficiaryResource {
    List<HashMap<String,Object>> saveBeneficiary(VMJExchange vmjExchange);
    HashMap<String, Object> updateBeneficiary(VMJExchange vmjExchange);
    HashMap<String, Object> getBeneficiary(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllBeneficiary(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteBeneficiary(VMJExchange vmjExchange);
}
