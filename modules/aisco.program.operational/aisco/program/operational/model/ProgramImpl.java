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

	public ProgramImpl() {
        super();
        this.objectName = ProgramImpl.class.getName();
    }

    public ProgramImpl(ProgramComponent record) {
        super(record, ProgramImpl.class.getName());
        this.objectName = ProgramImpl.class.getName();
    }

    public ProgramImpl(UUID id, ProgramComponent record) {
        super(id, record);
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
    public String toString() {
        return "{" +
                " id='" + getIdProgram() + "'" +
                ", record='" + getRecord() + "'" +
                "}";
    }

    public HashMap<String, Object> toHashMap() {
        System.out.println("====print record operational=======");
        System.out.println(record.getClass().getName());
        System.out.println("==================================");
        HashMap<String, Object> programMap = record.toHashMap();
        programMap.put("id", idProgram);
        programMap.remove("target");
        programMap.remove("partner");
        programMap.remove("executionDate");
        programMap.remove("logoUrl");
        return programMap;
    }
}