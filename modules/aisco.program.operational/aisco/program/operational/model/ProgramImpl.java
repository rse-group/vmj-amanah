package aisco.program.operational;

import java.lang.Math;
import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.DeltaPropertyException;

import aisco.program.core.ProgramComponent;
import aisco.program.core.ProgramDecorator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;

@Entity(name = "program_operational")
@Table(name = "program_operational")
public class ProgramImpl extends ProgramDecorator {

	// Default constructor
	public ProgramImpl() {}
	
	public ProgramImpl(String name, String description) {
		super(name, description);
	}

	@Override
	public void setTarget(String target) {
		throw new DeltaPropertyException("Target dihapus dari delta operational");
	}

	@Override
	public String getTarget() {
		throw new DeltaPropertyException("Target dihapus dari delta operational");
	}

	@Override
	public void setPartner(String partner) {
		throw new DeltaPropertyException("Partner dihapus dari delta operational");
	}

	@Override
	public String getPartner() {
		throw new DeltaPropertyException("Partner dihapus dari delta operational");
	}

	@Override
	public void setLogoUrl(String logoUrl) {
		throw new DeltaPropertyException("LogoUrl dihapus dari delta operational");
	}

	@Override
	public String getLogoUrl() {
		throw new DeltaPropertyException("LogoUrl dihapus dari delta operational");
	}

	@Override
	public void setExecutionDate(String executionDate) {
		throw new DeltaPropertyException("ExecutionDate dihapus dari delta operational");
	}

	@Override
	public String getExecutionDate() {
		throw new DeltaPropertyException("ExecutionDate dihapus dari delta operational");
	}
	
	@Override
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> programMap = new HashMap<String,Object>();
		programMap.put("id", getIdProgram());
		programMap.put("name", getName());
		programMap.put("description", getDescription());
		return programMap;
	}


	@Override
	public String toString() {
		return "{" +
				" id='" + getIdProgram() + "'" +
				"}";
	}
}