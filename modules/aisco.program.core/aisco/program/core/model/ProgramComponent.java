package aisco.program.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.*;

import prices.auth.vmj.annotations.Restricted;
// import aisco.financialreport.core.*;

@Entity(name = "program_comp")
@Table(name = "program_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProgramComponent implements Program {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int idProgram;
	public String name;
	public String description;
	public String target;
	public String partner;
	@Column(columnDefinition = "TEXT")
	public String logoUrl;
	public String executionDate;
	
	// Default constructor
	public ProgramComponent() {}

	public ProgramComponent(String name, String description, String target, String partner, String logoUrl, String executionDate) {
		this.name = name;
		this.description = description;
		this.target = target;
		this.partner = partner;
		this.logoUrl = logoUrl;
		this.executionDate = executionDate;
	}

	@Override
	public void setIdProgram(int idProgram) {
		this.idProgram = idProgram;
	}

	@Override
	public int getIdProgram() {
		return this.idProgram;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String getTarget() {
		return this.target;
	}

	@Override
	public void setPartner(String partner) {
		this.partner = partner;
	}

	@Override
	public String getPartner() {
		return this.partner;
	}

	@Override
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	@Override
	public String getLogoUrl() {
		return this.logoUrl;
	}

	@Override
	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}

	@Override
	public String getExecutionDate() {
		return this.executionDate;
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
