package aisco.program.core;
import java.util.*;
import vmj.routing.route.VMJExchange;

public interface Program {
    UUID getIdProgram();
    void setIdProgram(UUID idProgram);

    String getNameProgram();
    void setNameProgram(String nameProgram);

    String getDescriptionProgram();
    void setDescriptionProgram(String descriptionProgram);

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