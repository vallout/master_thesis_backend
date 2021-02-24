package de.valentin.master.core.appservices.events.repositories;

import java.util.List;

import de.valentin.master.core.appservices.events.dto.TaskCreatedData;
import de.valentin.master.core.appservices.events.dto.TaskLikedData;
import de.valentin.master.core.appservices.internalevents.TaskCreated;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

public interface TodoCreatedRepository {
	void save(TaskCreated todoCreated);
	
	List<TaskCreatedData> retrieve();
	List<TaskCreatedData> retrieve(ProjectId projectId);
	List<TaskCreatedData> retrieve(long begin, long end);
	List<TaskCreatedData> retrieve(UserId userId);
	List<TaskCreatedData> retrieve(UserId userId, long begin, long end);
	List<TaskCreatedData> retrieve(UserId userId, ProjectId projectId);
	List<TaskCreatedData> retrieve(UserId userId, ProjectId projectId, long begin, long end);
}
