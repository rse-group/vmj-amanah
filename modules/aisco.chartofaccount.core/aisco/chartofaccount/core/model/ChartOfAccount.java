package aisco.chartofaccount.core;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import java.util.*;

public interface ChartOfAccount {
	int getId();
	void setId(int id);

    int getCode();
	void setCode(int code);

	String getName();
	void setName(String name);

	String getDescription();
	void setDescription(String description);

	String getIsVisible();
	void setIsVisible(String isVisible);

	Integer getParentId();
	void setParentId(int parentId);

	Integer getLevel();
	void setLevel(int level);

	boolean getIsRestricted();
	void setIsRestricted(boolean isRestricted);

	Integer getCashflowType();
	void setCashflowType(int cashflowType);

    HashMap<String, Object> toHashMap();
}