package aisco.programreport.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

import aisco.program.core.*;

@Entity
@Table(name="programreport_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProgramReportComponent implements ProgramReport {
	@Id
	protected UUID id; 
	protected String title;
	protected String description;
	@Lob
	protected String image;
	protected String createdAt;
	@ManyToOne(targetEntity=aisco.program.core.ProgramComponent.class)
	protected Program program;

	public ProgramReportComponent() {

	} 

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	public String getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public Program getProgram() { return this.program;}
	public void setProgram(Program program){ this.program = program; }
	
 

	@Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            " title='" + getTitle() + "'" +
            " description='" + getDescription() + "'" +
            " image='" + getImage() + "'" +
            " createdAt='" + getCreatedAt() + "'" +
            " program='" + getProgram() + "'" +
            "}";
    }
	
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> Map = new HashMap<String,Object>();
		Map.put("id",getId());
		Map.put("title",getTitle());
		Map.put("description",getDescription());
		Map.put("image",getImage());
		Map.put("createdAt",getCreatedAt());
		Map.put("program",getProgram());

        return Map;
    }
}
