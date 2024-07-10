package aisco.automaticreport.periodic;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import vmj.auth.annotations.Restricted;

@Entity
@Table(name="automaticreport_periodic_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AutomaticReportPeriodicComponent implements AutomaticReportPeriodic {
    @Id
    public int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getName();
    public abstract void setName(String name);

    public abstract boolean getIsActive();
    public abstract void setIsActive(boolean isActive);

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> automaticReportPeriodicMap = new HashMap<String,Object>();
        automaticReportPeriodicMap.put("id", getId());
        automaticReportPeriodicMap.put("name", getName());
        automaticReportPeriodicMap.put("isActive", getIsActive());
        return automaticReportPeriodicMap;
    }
}
