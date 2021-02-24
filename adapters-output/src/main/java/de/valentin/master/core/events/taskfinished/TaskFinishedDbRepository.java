package de.valentin.master.core.events.taskfinished;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.events.dto.TaskFinishedData;
import de.valentin.master.core.appservices.events.repositories.TodoFinishedRepository;
import de.valentin.master.core.appservices.internalevents.TaskFinished;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TaskFinishedDbRepository implements TodoFinishedRepository {
	TaskFinishedDAO dao;
	
	@Autowired
	public TaskFinishedDbRepository(TaskFinishedDAO dao) {
		this.dao = dao;
	}

	@Override
	public void save(TaskFinished todoFinished) {

		TaskFinishedProjection projection = new TaskFinishedProjection();
		projection.setProjectId(new ObjectId(todoFinished.getProjectId().toString()));
		projection.setUserId(new ObjectId(todoFinished.getUserId().toString()));
		projection.setTimestamp(todoFinished.getTimestamp());
		
		dao.save(projection);
	}

	@Override
	public List<TaskFinishedData> retrieve() {
		List<TaskFinishedProjection> projections = dao.findAll();
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskFinishedData> retrieve(long begin, long end) {
		List<TaskFinishedProjection> projections = dao.findByTimestampBetween(begin, end);
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskFinishedData> retrieve(UserId userId) {
		List<TaskFinishedProjection> projections = dao.findByUserId(new ObjectId(userId.toString()));
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskFinishedData> retrieve(UserId userId, long begin, long end) {
		List<TaskFinishedProjection> projections = dao.findByUserIdAndTimestampBetween(new ObjectId(userId.toString()), begin, end);
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskFinishedData> retrieve(UserId userId, ProjectId projectId) {
		List<TaskFinishedProjection> projections = dao.findByUserIdAndProjectId(new ObjectId(userId.toString()), new ObjectId(projectId.toString()));
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskFinishedData> retrieve(ProjectId projectId) {
		List<TaskFinishedProjection> projections = dao.findByProjectId(new ObjectId(projectId.toString()));
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskFinishedData> retrieve(UserId userId, ProjectId projectId, long begin, long end) {
		List<TaskFinishedProjection> projections = dao.findByUserIdAndProjectIdAndTimestampBetween(new ObjectId(userId.toString()), new ObjectId(projectId.toString()), begin, end);
		return retrieveGeneral(projections);
	}
	
	private List<TaskFinishedData> retrieveGeneral(List<TaskFinishedProjection> projections) {
		List<TaskFinishedData> todosFinished = new ArrayList<TaskFinishedData>();
		
		for (TaskFinishedProjection projection : projections) {
			TaskFinishedData todoFinished = new TaskFinishedData(
											projection.getProjectId().toString(), projection.getUserId().toString(), 
											projection.getTimestamp());
			todosFinished.add(todoFinished);
		}
		
		return todosFinished;
	}
}
