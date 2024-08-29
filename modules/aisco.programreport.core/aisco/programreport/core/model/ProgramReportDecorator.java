package aisco.programreport.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.CascadeType;

import aisco.program.core.*;

@MappedSuperclass
public abstract class ProgramReportDecorator extends ProgramReportComponent{
	protected ProgramReportComponent record;
		
	public ProgramReportDecorator (ProgramReportComponent record) {
		this.record = record;
	}

	public ProgramReportDecorator (UUID id, ProgramReportComponent record) {
		this.id =  id;
		this.record = record;
	}
	
	public ProgramReportDecorator(){
		super();
		this.id =  UUID.randomUUID();
	}

	public UUID getId() {
		return record.getId();
	}
	public void setId(UUID id) {
		record.setId(id);
	}
	public String getTitle() {
		return record.getTitle();
	}
	public void setTitle(String title) {
		record.setTitle(title);
	}
	public String getDescription() {
		return record.getDescription();
	}
	public void setDescription(String description) {
		record.setDescription(description);
	}
	public String getImage() {
		return record.getImage();
	}
	public void setImage(String image) {
		record.setImage(image);
	}
	public String getCreatedAt() {
		return record.getCreatedAt();
	}
	public void setCreatedAt(String createdAt) {
		record.setCreatedAt(createdAt);
	}
	public Program getProgram(){ 
        return record.getProgram();
    }
    public void setProgram(Program program){ 
        record.setProgram(program); 
    }

	public HashMap<String, Object> toHashMap() {
        return this.record.toHashMap();
    }

}
