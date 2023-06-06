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

@Entity(name="program_activity")
@Table(name="program_activity")
public class ProgramImpl extends ProgramComponent {
	
	// Default constructor
	public ProgramImpl() {}

	public ProgramImpl(String name, String description, String target, String partner, String logoUrl, String executionDate) {
        super(name, description, target, partner, logoUrl, executionDate);
    }
}
