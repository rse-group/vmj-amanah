package aisco.program.core;
import java.util.*;
import vmj.routing.route.VMJExchange;

public interface Program {
    UUID getIdProgram();
    void setIdProgram(UUID idProgram);

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

    String getTarget();
    void setTarget(String target);

    String getPartner();
    void setPartner(String partner);

    String getLogoUrl();
    void setLogoUrl(String logoUrl);

    String getExecutionDate();
    void setExecutionDate(String executionDate);
    
//    long calculateDonation();
//    double calculatePercentage();ss
    
    HashMap<String, Object> toHashMap();
}