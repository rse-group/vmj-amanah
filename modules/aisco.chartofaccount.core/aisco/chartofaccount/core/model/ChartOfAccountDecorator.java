package aisco.chartofaccount.core;

import java.util.*;

public abstract class ChartOfAccountDecorator extends ChartOfAccountComponent {

    public ChartOfAccountComponent record;

    public ChartOfAccountDecorator(ChartOfAccountComponent record) {
        this.record = record;
        Random r = new Random();
		this.id = Math.abs(r.nextInt());
    }

    public ChartOfAccountDecorator() {
        super();
        this.record = new ChartOfAccountImpl();
        Random r = new Random();
		this.id = Math.abs(r.nextInt());
    }


    public ChartOfAccountComponent getRecord() {
        return this.record;
    }

    public void setRecord(ChartOfAccountComponent record) {
        this.record = record;
    }

    @Override
	public int getCode() {
		return this.record.getCode();
	}

    @Override
	public void setCode(int code) {
		this.record.setCode(code);
	}

    @Override
	public String getName() {
		return this.record.getName();
	}

    @Override
	public void setName(String name) {
		this.record.setName(name);
	}

    @Override
	public String getDescription() {
		return this.record.getDescription();
	}

    @Override
	public void setDescription(String description) {
		this.record.setDescription(description);
	}

    @Override
	public String getIsVisible() {
		return this.record.getIsVisible();
	}

    @Override
	public void setIsVisible(String isVisible) {
		this.record.setIsVisible(isVisible);
	}

	@Override
	public Integer getParentId() {
		return this.record.getParentId();
	}

    @Override
	public void setParentId(int parentId) {
		this.record.setParentId(parentId);
	}

	@Override
	public Integer getLevel() {
		return this.record.getLevel();
	}

    @Override
	public void setLevel(int level) {
		this.record.setLevel(level);
	}

	@Override
	public boolean getIsRestricted() {
		return this.record.getIsRestricted();
	}

    @Override
	public void setIsRestricted(boolean isRestricted) {
		this.record.setIsRestricted(isRestricted);
	}

	@Override
	public Integer getCashflowType() {
		return this.record.getCashflowType();
	}

    @Override
	public void setCashflowType(int cashflowType) {
		this.record.setCashflowType(cashflowType);
	}
}