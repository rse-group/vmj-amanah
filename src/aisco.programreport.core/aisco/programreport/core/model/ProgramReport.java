package aisco.programreport.core;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import java.util.*;
import aisco.program.core.*;

public interface ProgramReport {
	UUID getId();
	void setId(UUID id);

	String getTitle();
	void setTitle(String title);

	String getDescription();
	void setDescription(String description);

	String getImage();
	void setImage(String image);

	String getCreatedAt();
	void setCreatedAt(String createdAt);

	Program getProgram();
	void setProgram(Program program);

	HashMap<String, Object> toHashMap();
}
