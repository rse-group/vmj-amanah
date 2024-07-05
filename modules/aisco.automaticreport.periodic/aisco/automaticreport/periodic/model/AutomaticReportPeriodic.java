package aisco.automaticreport.periodic;
import java.util.*;
import vmj.routing.route.VMJExchange;

public interface AutomaticReportPeriodic {
    int getId();
    void setId(int id);

    String getName();
    void setName(String name);

    boolean getIsActive();
    void setIsActive(boolean isActive);

    HashMap<String, Object> toHashMap();
}
