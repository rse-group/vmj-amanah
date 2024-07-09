package aisco.beneficiary.core;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import java.util.*;

public interface Beneficiary {
	public UUID getId();
	public void setId(UUID id);
	public String getFullName();
	public void setFullName(String fullName);
	public String getAddress();
	public void setAddress(String address);
	public String getEmail();
	public void setEmail(String email);
	public String getBrith();
	public void setBrith(String brith);
	public String getGender();
	public void setGender(String gender);
	public String getPhone();
	public void setPhone(String Phone);
	public int getAllowance();
	public void setAllowance(int allowance);
	HashMap<String, Object> toHashMap();
}
