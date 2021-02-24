package de.valentin.master.core.appservices.events.repositories;

import java.util.List;

import de.valentin.master.core.appservices.events.dto.ProjectJoinedData;
import de.valentin.master.core.appservices.internalevents.ProjectJoined;
import de.valentin.master.core.shared_model.UserId;

public interface ProjectJoinedRepository {
	void save(ProjectJoined projectJoined);
	
	List<ProjectJoinedData> retrieve(UserId userId);
	List<ProjectJoinedData> retrieve(UserId userId, long begin, long end);
	List<ProjectJoinedData> retrieve(long begin, long end);
	List<ProjectJoinedData> retrieve();
}
