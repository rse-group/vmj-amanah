package aisco.program.core;

import java.util.*;
import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.CascadeType;

@MappedSuperclass
public abstract class ProgramDecorator extends ProgramComponent{
	
	@OneToOne(cascade=CascadeType.ALL)
    public ProgramComponent record;

    public ProgramDecorator(ProgramComponent record, String objectName) {
        this.record = record;
        this.record.objectName = objectName;
		this.idProgram = UUID.randomUUID();
    }

    public ProgramDecorator(UUID idProgram, ProgramComponent record) {
        this.idProgram = idProgram;
        this.record = record;
    }

    public ProgramDecorator() {
        super();
        this.idProgram = UUID.randomUUID();
    }

    public String getNameProgram() {
        return this.record.getNameProgram();
    }

    public void setNameProgram(String nameProgram) {
        this.record.setName(nameProgram);
    }

    public String getDescriptionProgram() {
        return this.record.getDescriptionProgram();
    }

    public void setDescriptionProgram(String descriptionProgram) {
        this.record.setDescription(descriptionProgram);
    }

    public String getTarget() {
        return this.record.getTarget();
    }

    public void setTarget(String target) {
        this.record.setTarget(target);
    }

    public String getPartner() {
        return this.record.getPartner();
    }

    public void setPartner(String partner) {
        this.record.setPartner(partner);
    }

    public String getLogoUrl() {
        return this.record.getLogoUrl();
    }

    public void setLogoUrl(String logoUrl) {
        this.record.setLogoUrl(logoUrl);
    }

    public String getExecutionDate() {
        return this.record.getExecutionDate();
    }

    public void setExecutionDate(String executionDate) {
        this.record.setExecutionDate(executionDate);
    }
    
    public ProgramComponent getRecord() {
        return this.record;
    }
    
//    public long calculateDonation() {
//    	return this.record.calculateDonation();
//    }
//    
//    public double calculatePercentage() {
//    	return this.record.calculatePercentage();
//    }
    
}