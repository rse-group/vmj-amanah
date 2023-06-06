package aisco.program.core;

import java.util.*;
import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.CascadeType;

@MappedSuperclass
public abstract class ProgramDecorator extends ProgramComponent{
	
	// Default constructor
	public ProgramDecorator() {}

	public ProgramDecorator(String name, String description) {
		super(name, description, null, null, null, null);
	}
}