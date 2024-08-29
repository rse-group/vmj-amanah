package aisco.automaticreport.periodic;

import java.lang.Math;
import java.util.*;

public abstract class AutomaticReportPeriodicDecorator extends AutomaticReportPeriodicComponent {

    public AutomaticReportPeriodicComponent record;

    public AutomaticReportPeriodicDecorator(AutomaticReportPeriodicComponent record) {
        this.record = record;
        Random r = new Random();
        this.id = Math.abs(r.nextInt());
    }

    public AutomaticReportPeriodicComponent getRecord() {
        return this.record;
    }

    public void setRecord(AutomaticReportPeriodicComponent record) {
        this.record = record;
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
    public boolean getIsActive() {
        return this.record.getIsActive();
    }

    @Override
    public void setIsActive(boolean isActive) {
        this.record.setIsActive(isActive);
    }
    
}
