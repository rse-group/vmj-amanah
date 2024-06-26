package aisco.programreport.core;

import java.lang.Math;
import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import aisco.program.core.*;


@Entity(name="programreport_impl")
@Table(name="programreport_impl")
public class ProgramReportImpl extends ProgramReportComponent {

	public ProgramReportImpl(UUID id, String title, String description, String image, String createdAt, Program program) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.image = image;
		this.createdAt = createdAt;
		this.program = program;
	}

	public ProgramReportImpl(String title, String description, String image, String createdAt, Program program) {
		this.id =  UUID.randomUUID();
		this.title = title;
		this.description = description;
		this.image = image;
		this.createdAt = createdAt;
		this.program = program;
	}

	public ProgramReportImpl() {
		this.id =  UUID.randomUUID();
		this.title = "";
		this.description = "";
		this.image = "";
		this.createdAt = "";
	}


}
