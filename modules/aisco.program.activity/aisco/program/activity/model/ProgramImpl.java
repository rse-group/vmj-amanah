package aisco.program.activity;

import java.lang.Math;
import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import aisco.program.core.ProgramComponent;
import aisco.program.core.ProgramDecorator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity(name="program_impl")
@Table(name="program_impl")
public class ProgramImpl extends ProgramComponent {
    public String name;
    public String description;
    public String target;
    public String partner;
    public String logoUrl;
    public String executionDate;

    public ProgramImpl(String name, String description, String target, String partner, String logoUrl, String executionDate) {
        Random r = new Random();
		this.idProgram = Math.abs(r.nextInt());
        this.name = name;
        this.description = description;
        this.target = target;
        this.partner = partner;
        this.logoUrl = logoUrl;
        this.executionDate = executionDate;
    }

    public ProgramImpl() {
        Random r = new Random();
		this.idProgram = Math.abs(r.nextInt());
        this.name = "";
        this.description = "";
        this.target = "";
        this.partner = "";
        this.logoUrl = "";
        this.executionDate = "";
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPartner() {
        return this.partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getLogoUrl() {
        return this.logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getExecutionDate() {
        return this.executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }
}
