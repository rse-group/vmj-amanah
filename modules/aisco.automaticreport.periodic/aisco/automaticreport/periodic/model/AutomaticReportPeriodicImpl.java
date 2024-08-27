package aisco.automaticreport.periodic;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="automaticreport_periodic_impl")
@Table(name="automaticreport_periodic_impl")
public class AutomaticReportPeriodicImpl extends AutomaticReportPeriodicComponent {
    
    public String name;
    public boolean isActive;

    public AutomaticReportPeriodicImpl() {
        Random r = new Random();
		this.id = Math.abs(r.nextInt());
        this.name = "2023";
        this.isActive = true;
    }

    public AutomaticReportPeriodicImpl(String name) {
        Random r = new Random();
		this.id = Math.abs(r.nextInt());
        this.name = name;
        this.isActive = true;
    }

    public AutomaticReportPeriodicImpl(String name, boolean isActive) {
        Random r = new Random();
		this.id = Math.abs(r.nextInt());
        this.name = name;
        this.isActive = isActive;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
