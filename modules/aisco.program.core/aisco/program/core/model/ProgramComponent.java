package aisco.program.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import prices.auth.vmj.annotations.Restricted;
// import aisco.financialreport.core.*;

@Entity
@Table(name="program_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProgramComponent implements Program {

    @Id
    public int idProgram;

    public int getIdProgram() {
        return this.idProgram;
    }

    public void setIdProgram(int idProgram) {
        this.idProgram = idProgram;
    }

    public abstract String getName();
    public abstract void setName(String name);

    public abstract String getDescription();
    public abstract void setDescription(String description);

    public abstract String getTarget();
    public abstract void setTarget(String target);

    public abstract String getPartner();
    public abstract void setPartner(String partner);

    public abstract String getLogoUrl();
    public abstract void setLogoUrl(String logoUrl);

    public abstract String getExecutionDate();
    public abstract void setExecutionDate(String executionDate);

    @Override
    public String toString() {
        return "Program  [id=" + getIdProgram() + ", name=" + getName() + "]";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> programMap = new HashMap<String,Object>();
        programMap.put("id", getIdProgram());
        programMap.put("name", getName());
        programMap.put("description", getDescription());
        programMap.put("target", getTarget());
        programMap.put("partner", getPartner());
        programMap.put("logoUrl", getLogoUrl());
        programMap.put("executionDate", getExecutionDate());
        return programMap;
    }
}
