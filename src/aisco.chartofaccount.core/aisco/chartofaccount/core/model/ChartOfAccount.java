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

    HashMap<String, Object> toHashMap();
}