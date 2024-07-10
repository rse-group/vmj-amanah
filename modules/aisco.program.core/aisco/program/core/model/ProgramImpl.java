package aisco.program.core;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "program_impl")
@Table(name = "program_impl")
public class ProgramImpl extends ProgramComponent {
	public ProgramImpl(String nameProgram, String descriptionProgram, String target, String partner, String logoUrl, String executionDate) {
		this.idProgram = UUID.randomUUID();
        this.nameProgram = nameProgram;
        this.descriptionProgram = descriptionProgram;
        this.target = target;
        this.partner = partner;
        this.logoUrl = logoUrl;
        this.executionDate = executionDate;
        this.objectName = ProgramImpl.class.getName();
    }
	
	public ProgramImpl(UUID idProgram, String nameProgram, String descriptionProgram, String target, String partner, String logoUrl, String executionDate) {
		this.idProgram = idProgram;
        this.nameProgram = nameProgram;
        this.descriptionProgram = descriptionProgram;
        this.target = target;
        this.partner = partner;
        this.logoUrl = logoUrl;
        this.executionDate = executionDate;
        this.objectName = ProgramImpl.class.getName();
    }

    public ProgramImpl() {
    	this.idProgram = UUID.randomUUID();
        this.nameProgram = "";
        this.descriptionProgram = "";
        this.target = "";
        this.partner = "";
        this.logoUrl = "";
        this.executionDate = "";
        this.objectName = "";
    }

    public String getNameProgram() {
        return this.nameProgram;
    }

    public void setNameProgram(String nameProgram) {
        this.nameProgram = nameProgram;
    }

    public String getDescriptionProgram() {
        return this.descriptionProgram;
    }

    public void setDescriptionProgram(String descriptionProgram) {
        this.descriptionProgram = descriptionProgram;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPartner() {
        return this.partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getLogoUrl() {
        return this.logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getExecutionDate() {
        return this.executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }
    
//    public long calculateDonation() {
//    	
//    }
    
//    public double calculatePercentage() {
//    	return this.record.calculatePercentage();
//    }
}