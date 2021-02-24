package de.valentin.master.core.events.taskcreated;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.events.dto.TaskCreatedData;
import de.valentin.master.core.appservices.events.dto.TaskLikedData;
import de.valentin.master.core.appservices.events.repositories.TodoCreatedRepository;
import de.valentin.master.core.appservices.internalevents.TaskCreated;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TaskCreatedDbRepository implements TodoCreatedRepository {
	
	TaskCreatedDAO dao;
	
	@Autowired
	public TaskCreatedDbRepository(TaskCreatedDAO dao) {
		this.dao = dao;
	}

	@Override
	public void save(TaskCreated todoCreated) {

		TaskCreatedProjection projection = new TaskCreatedProjection();
		projection.setProjectId(new ObjectId(todoCreated.getProjectId().toString()));
		projection.setUserId(new ObjectId(todoCreated.getUserId().toString()));
		projection.setTimestamp(todoCreated.getTimestamp());
		
		dao.save(projection);
	}

	@Override
	public List<TaskCreatedData> retrieve() {
		List<TaskCreatedProjection> projections = dao.findAll();
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskCreatedData> retrieve(long begin, long end) {
		List<TaskCreatedProjection> projections = dao.findByTimestampBetween(begin, end);
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskCreatedData> retrieve(UserId userId) {
		List<TaskCreatedProjection> projections = dao.findByUserId(new ObjectId(userId.toString()));
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskCreatedData> retrieve(UserId userId, long begin, long end) {
		List<TaskCreatedProjection> projections = dao.findByUserIdAndTimestampBetween(new ObjectId(userId.toString()), begin, end);
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskCreatedData> retrieve(UserId userId, ProjectId projectId) {
		List<TaskCreatedProjection> projections = dao.findByUserIdAndProjectId(new ObjectId(userId.toString()), new ObjectId(projectId.toString()));
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskCreatedData> retrieve(ProjectId projectId) {
		List<TaskCreatedProjection> todosCreated = dao.findByProjectId(new ObjectId(projectId.toString()));
		return retrieveGeneral(todosCreated);
	}

	@Override
	public List<TaskCreatedData> retrieve(UserId userId, ProjectId projectId, long begin, long end) {
		List<TaskCreatedProjection> projections = dao.findByUserIdAndProjectIdAndTimestampBetween(new ObjectId(userId.toString()), new ObjectId(projectId.toString()), begin, end);
		return retrieveGeneral(projections);
	}
	
	private List<TaskCreatedData> retrieveGeneral(List<TaskCreatedProjection> projections) {
		List<TaskCreatedData> todosCreated = new ArrayList<TaskCreatedData>();
		
		for (TaskCreatedProjection projection : projections) {
			TaskCreatedData todoCreated = new TaskCreatedData(
											projection.getProjectId().toString(), projection.getUserId().toString(), 
											projection.getTimestamp());
			todosCreated.add(todoCreated);
		}
		
		return todosCreated;
	}
}
