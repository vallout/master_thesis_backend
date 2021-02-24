package de.valentin.master.core.appservices.events.repositories;

import java.util.List;

import de.valentin.master.core.appservices.events.dto.TaskLikedData;
import de.valentin.master.core.appservices.internalevents.TaskLiked;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

public interface TodoLikedRepository {
	void save(TaskLiked todoLiked);
	
	List<TaskLikedData> retrieve();
	List<TaskLikedData> retrieve(ProjectId projectId);
	List<TaskLikedData> retrieve(long begin, long end);
	List<TaskLikedData> retrieve(String key, UserId userId);
	List<TaskLikedData> retrieve(String key, UserId userId, long begin, long end);
	List<TaskLikedData> retrieve(String key, UserId userId, ProjectId projectId);
	List<TaskLikedData> retrieve(String key, UserId userId, ProjectId projectId, long begin, long end);

}
