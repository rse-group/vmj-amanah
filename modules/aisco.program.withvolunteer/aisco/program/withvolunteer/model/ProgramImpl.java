package aisco.program.withvolunteer;

import java.util.*;
import java.util.stream.Collectors;

import aisco.program.core.ProgramComponent;
import aisco.program.core.ProgramDecorator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;

import volunteers.volunteer.core.*;

@Entity(name="program_with_volunteer")
@Table(name="program_with_volunteer")
public class ProgramImpl extends ProgramDecorator {
	@ManyToMany(fetch = FetchType.EAGER)
    protected List<VolunteerComponent> volunteers;

    public ProgramImpl(ProgramComponent record) {
        super(record);
		this.volunteers = new ArrayList<>();
    }

    public ProgramImpl() {
		this.volunteers = new ArrayList<>();
	}
	
	public List<VolunteerComponent> getVolunteers(){ return volunteers; }
    public void addMember(VolunteerComponent volunteer){ volunteers.add(volunteer); }
	
	public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> programMap = record.toHashMap();
        programMap.put("id", getIdProgram());
        programMap.put("volunteers", getVolunteers().stream().map(VolunteerComponent::toHashMap).
        collect(Collectors.toList()));
		
        return programMap;
    }
}
