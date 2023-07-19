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
        Random r = new Random();
		this.idProgram = Math.abs(r.nextInt());
    }

    public ProgramDecorator(int idProgram, ProgramComponent record) {
        this.idProgram = idProgram;
        this.record = record;
    }

    public ProgramDecorator() {
        super();
        Random r = new Random();
		this.idProgram = Math.abs(r.nextInt());
    }

    public String getName() {
        return this.record.getName();
    }

    public void setName(String name) {
        this.record.setName(name);
    }

    public String getDescription() {
        return this.record.getDescription();
    }

    public void setDescription(String description) {
        this.record.setDescription(description);
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
}