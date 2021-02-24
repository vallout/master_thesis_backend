package de.valentin.master.core.appservices.events.repositories;

import java.util.List;

import de.valentin.master.core.appservices.events.dto.TaskFinishedData;
import de.valentin.master.core.appservices.events.dto.TaskLikedData;
import de.valentin.master.core.appservices.internalevents.TaskFinished;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

public interface TodoFinishedRepository {
	void save(TaskFinished todoFinished);
	
	List<TaskFinishedData> retrieve();
	List<TaskFinishedData> retrieve(ProjectId projectId);
	List<TaskFinishedData> retrieve(long begin, long end);
	List<TaskFinishedData> retrieve(UserId userId);
	List<TaskFinishedData> retrieve(UserId userId, long begin, long end);
	List<TaskFinishedData> retrieve(UserId userId, ProjectId projectId);
	List<TaskFinishedData> retrieve(UserId userId, ProjectId projectId, long begin, long end);
}
