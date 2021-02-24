package de.valentin.master.core.events.taskliked;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.events.dto.TaskLikedData;
import de.valentin.master.core.appservices.events.repositories.TodoLikedRepository;
import de.valentin.master.core.appservices.internalevents.TaskLiked;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TaskLikedDbRepository implements TodoLikedRepository{

	TaskLikedDAO dao;
	
	@Autowired
	public TaskLikedDbRepository(TaskLikedDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void save(TaskLiked taskLiked) {
		
		TaskLikedProjection projection = new TaskLikedProjection();
		projection.setCreatorId(new ObjectId(taskLiked.getCreator().toString()));
		projection.setLikerId(new ObjectId(taskLiked.getLiker().toString()));
		projection.setProjectId(new ObjectId(taskLiked.getProjectId().toString()));
		projection.setTimestamp(taskLiked.getTimestamp());
		dao.save(projection);	
	}

	@Override
	public List<TaskLikedData> retrieve() {
		List<TaskLikedProjection> projections = dao.findAll();
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskLikedData> retrieve(long begin, long end) {
		List<TaskLikedProjection> projections = dao.findByTimestampBetween(begin, end);
		return retrieveGeneral(projections);
	}

	@Override
	public List<TaskLikedData> retrieve(String key, UserId userId) {
		if(key == "Liker") {
			List<TaskLikedProjection> projections = dao.findByLikerId(new ObjectId(userId.toString()));
			return retrieveGeneral(projections);
		} else if (key == "Creator") {
			List<TaskLikedProjection> projections = dao.findByCreatorId(new ObjectId(userId.toString()));
			return retrieveGeneral(projections);
		} else {
			return null;
		}
	}

	@Override
	public List<TaskLikedData> retrieve(String key, UserId userId, long begin, long end) {
		if(key == "Liker") {
			List<TaskLikedProjection> projections = dao.findByLikerIdAndTimestampBetween(new ObjectId(userId.toString()), begin, end);
			return retrieveGeneral(projections);
		} else if (key == "Creator") {
			List<TaskLikedProjection> projections = dao.findByCreatorIdAndTimestampBetween(new ObjectId(userId.toString()), begin, end);
			return retrieveGeneral(projections);
		} else {
			return null;
		}
	}

	@Override
	public List<TaskLikedData> retrieve(String key, UserId userId, ProjectId projectId) {
		if(key == "Liker") {
			List<TaskLikedProjection> projections = dao.findByLikerIdAndProjectId(new ObjectId(userId.toString()), new ObjectId(projectId.toString()));
			return retrieveGeneral(projections);
		} else if (key == "Creator") {
			List<TaskLikedProjection> projections = dao.findByCreatorIdAndProjectId(new ObjectId(userId.toString()), new ObjectId(projectId.toString()));
			return retrieveGeneral(projections);
		} else {
			return null;
		}
	}

	@Override
	public List<TaskLikedData> retrieve(String key, UserId userId, ProjectId projectId, long begin, long end) {
		if(key == "Liker") {
			List<TaskLikedProjection> projections = dao.findByLikerIdAndProjectIdAndTimestampBetween(new ObjectId(userId.toString()),
														new ObjectId(projectId.toString()), begin, end);
			return retrieveGeneral(projections);
		} else if (key == "Creator") {
			List<TaskLikedProjection> projections = dao.findByCreatorIdAndProjectIdAndTimestampBetween(new ObjectId(userId.toString()),
														new ObjectId(projectId.toString()), begin, end);
			return retrieveGeneral(projections);
		} else {
			return null;
		}
	}

	@Override
	public List<TaskLikedData> retrieve(ProjectId projectId) {
		List<TaskLikedProjection> projections = dao.findByProjectId(new ObjectId(projectId.toString()));
		return retrieveGeneral(projections);
	}
	
	private List<TaskLikedData> retrieveGeneral(List<TaskLikedProjection> projections) {
		List<TaskLikedData> todosLiked = new ArrayList<TaskLikedData>();
		
		for (TaskLikedProjection projection : projections) {
			TaskLikedData todoFinished = new TaskLikedData(
											projection.getProjectId().toString(), projection.getCreatorId().toString(), 
											projection.getLikerId().toString(), projection.getTimestamp());
			todosLiked.add(todoFinished);
		}
		
		return todosLiked;
	}
}
