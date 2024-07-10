package aisco.program.core;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Lob;


@Entity(name = "program_comp")
@Table(name = "program_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProgramComponent implements Program {
	
	@Id
	public UUID idProgram;
	public String nameProgram;
	public String descriptionProgram;
	public String target;
	public String partner;
	
	@Lob
	public String logoUrl;
	public String executionDate;
	public String objectName;

	@Override
	public void setIdProgram(UUID idProgram) {
		this.idProgram = idProgram;
	}

	@Override
	public UUID getIdProgram() {
		return this.idProgram;
	}

	public abstract String getNameProgram();
    public abstract void setNameProgram(String nameProgram);

    public abstract String getDescriptionProgram();
    public abstract void setDescriptionProgram(String descriptionProgram);

    public abstract String getTarget();
    public abstract void setTarget(String target);

    public abstract String getPartner();
    public abstract void setPartner(String partner);

    public abstract String getLogoUrl();
    public abstract void setLogoUrl(String logoUrl);

    public abstract String getExecutionDate();
    public abstract void setExecutionDate(String executionDate);
    
//    public abstract long calculateDonation();
//    public abstract double calculatePercentage();
	
	@Override
    public String toString() {
        return "Program  [id=" + getIdProgram() + ", name=" + getName() + "]";
    }
	
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> programMap = new HashMap<String,Object>();
		programMap.put("idProgram", getIdProgram());
		programMap.put("nameProgram", getNameProgram());
		programMap.put("descriptionProgram", getDescriptionProgram());
		programMap.put("target", getTarget());
		programMap.put("partner", getPartner());
		programMap.put("logoUrl", getLogoUrl());
		programMap.put("executionDate", getExecutionDate());
		return programMap;
	}
}
