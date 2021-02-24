package de.valentin.master.core.appservices.events.repositories;

import java.util.List;

import de.valentin.master.core.appservices.events.dto.ProjectCreatedData;
import de.valentin.master.core.appservices.internalevents.ProjectCreated;
import de.valentin.master.core.shared_model.UserId;

public interface ProjectCreatedRepository {
	void save(ProjectCreated projectCreated);
	
	List<ProjectCreatedData> retrieve(UserId userId);
	List<ProjectCreatedData> retrieve(UserId userId, long begin, long end);
	List<ProjectCreatedData> retrieve(long begin, long end);
	List<ProjectCreatedData> retrieve();
}
