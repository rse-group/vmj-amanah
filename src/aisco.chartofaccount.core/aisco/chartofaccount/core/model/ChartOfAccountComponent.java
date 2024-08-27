package aisco.chartofaccount.core;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="chartofaccount_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ChartOfAccountComponent implements ChartOfAccount {
	@Id
	public int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public abstract int getCode();
	public abstract void setCode(int code);

	public abstract String getName();
	public abstract void setName(String name);

	public abstract String getDescription();
	public abstract void setDescription(String description);

	public abstract String getIsVisible();
	public abstract void setIsVisible(String isVisible);

	@Override
	public String toString() {
		return "ChartOfAccount [id=" + getId() + ", name=" + getName() + ", description=" + getDescription() + ", isVisible="
				+ getIsVisible() + "]";
	}

	public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> chartOfAccountMap = new HashMap<String,Object>();
		// TODO: betulin kalau di FE-nya udah bisa ngolah parameter code
        chartOfAccountMap.put("id", getId());
        chartOfAccountMap.put("code", getCode());
        chartOfAccountMap.put("name", getName());
        chartOfAccountMap.put("description", getDescription());
        chartOfAccountMap.put("isVisible", getIsVisible());
        return chartOfAccountMap;
    }
}