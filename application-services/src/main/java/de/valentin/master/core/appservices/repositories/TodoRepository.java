package de.valentin.master.core.appservices.repositories;

import java.util.List;

import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.task.TaskAggregate;

public interface TodoRepository {
	
	void save(TaskAggregate todoAggregate);
	
	List<TaskAggregate> retrieve(UserId userId);
	List<TaskAggregate> retrieve(ProjectId projectId);
	TaskAggregate retrieve(String todoId);

	boolean delete(String todoId);
}
