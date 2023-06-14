package aisco.chartofaccount.core;

import java.lang.Math;
import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="chartofaccount_impl")
@Table(name="chartofaccount_impl")
public class ChartOfAccountImpl extends ChartOfAccountComponent {
	public int code;
    public String name;
    public String description;
    public String isVisible;
	public Integer parentId;
	public Integer level;
	public boolean isRestricted;
	public Integer cashflowType;

    public ChartOfAccountImpl(int code, String name, String description, String isVisible) {
		Random r = new Random();
		this.id = Math.abs(r.nextInt());
		this.code = code;
        this.name = name;
        this.description = description;
        this.isVisible = isVisible;
    }

	public ChartOfAccountImpl(int id, int code, String name, String description, String isVisible) {
		this.id = Math.abs(id);
		this.code = code;
        this.name = name;
        this.description = description;
        this.isVisible = isVisible;
    }

	public ChartOfAccountImpl() {
		Random r = new Random();
		this.id = Math.abs(r.nextInt());
		this.code = 0;
        this.name = "";
        this.description = "";
        this.isVisible = "";
    }

    public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean getIsRestricted() {
		return isRestricted;
	}

	public void setIsRestricted(boolean isRestricted) {
		this.isRestricted = isRestricted;
	}

	public Integer getCashflowType() {
		return cashflowType;
	}

	public void setCashflowType(int cashflowType) {
		this.cashflowType = cashflowType;
	}

}