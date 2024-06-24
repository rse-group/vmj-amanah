package aisco.program.goodsprogram;

import aisco.program.core.ProgramDecorator;

import java.util.*;

import aisco.program.core.Program;
import aisco.program.core.ProgramComponent;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Lob;

@Entity(name="program_goodsprogram")
@Table(name="program_goodsprogram")
public class ProgramImpl extends ProgramDecorator {

	protected String goodsName;
	protected String unit;
	
	public ProgramImpl() {
		super();
		this.objectName = ProgramImpl.class.getName();
	}
	public ProgramImpl(ProgramComponent record, String goodsName, String unit) {
		super(record, ProgramImpl.class.getName());
		this.goodsName = goodsName;
		this.unit = unit;
		this.objectName = ProgramImpl.class.getName();
	}
	public ProgramImpl(UUID id, ProgramComponent record, String goodsName, String unit) {
		super(id, record);
		this.goodsName = goodsName;
		this.unit = unit;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> programMap = record.toHashMap();
        programMap.put("id", idProgram);
        programMap.put("goodsName", getGoodsName());
        programMap.put("unit", getUnit());
        return programMap;
    }
}
