package de.valentin.master.core.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.repositories.TaskRepository;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.task.TaskAggregate;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TaskDbRepository implements TaskRepository{
	
	private TaskDAO dao;
	
	@Autowired
	public TaskDbRepository(TaskDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void save(TaskAggregate todoAggregate) {
		TaskProjection projection = new TaskProjection();
		
		projection.setTaskId(new ObjectId(todoAggregate.getRootEntity().getId().toString()));
		projection.setProjectId(new ObjectId(todoAggregate.getRootEntity().getProjectId().toString()));
		projection.setUserId(new ObjectId(todoAggregate.getRootEntity().getCreator().toString()));
		projection.setName(todoAggregate.getRootEntity().getName());
		projection.setDescription(todoAggregate.getRootEntity().getDescription());
		projection.setState(todoAggregate.getRootEntity().getState().toString());
		projection.setDeadline(todoAggregate.getRootEntity().getDeadline());
		List<ObjectId> likerConverter = todoAggregate.getRootEntity().getLikers().stream()
									.map(userId -> new ObjectId(userId.toString()))
									.collect(Collectors.toList());
		projection.setLikers(likerConverter);
		dao.save(projection);
	}
	
	@Override
	public TaskAggregate retrieve(String todoId) {
		Optional<TaskProjection> optionalProjection = dao.findById(new ObjectId(todoId));
		TaskProjection projection = null;
		if (!optionalProjection.isPresent()) return null;
		else {
			projection = optionalProjection.get();
		}
		List<String> likerConverter = projection.getLikers().stream()
				.map(userIdAsObject -> Objects.toString(userIdAsObject, null))
				.collect(Collectors.toList());
		TaskAggregate aggregate = new TaskAggregate.TodoBuilder(todoId.toString(), projection.getProjectId().toString(), projection.getUserId().toString())
									.name(projection.getName())
									.description(projection.getDescription())
									.date(projection.getDeadline())
									.state(projection.getState())
									.withLikes(likerConverter)
									.build();
		return aggregate;
	}

	@Override
	public List<TaskAggregate> retrieve(UserId userId) {
		List<TaskProjection> projections = dao.findByUserId(new ObjectId(userId.toString()));
		List<TaskAggregate> aggregates = new ArrayList<TaskAggregate>();
		for (TaskProjection projection : projections) {
			String todoId = projection.getTaskId().toString();
			String projectId = projection.getProjectId().toString();
			List<String> likerConverter = projection.getLikers().stream()
					.map(userIdAsObject -> Objects.toString(userIdAsObject, null))
					.collect(Collectors.toList());
			TaskAggregate aggregate = new TaskAggregate.TodoBuilder(todoId, projectId, userId.toString())
										.name(projection.getName())
										.description(projection.getDescription())
										.date(projection.getDeadline())
										.state(projection.getState())
										.withLikes(likerConverter)
										.build();
			aggregates.add(aggregate);
		}
		return aggregates;
	}

	@Override
	public List<TaskAggregate> retrieve(ProjectId projectId) {
		List<TaskProjection> projections = dao.findByProjectId(new ObjectId(projectId.toString()));
		List<TaskAggregate> aggregates = new ArrayList<TaskAggregate>();
		for (TaskProjection projection : projections) {
			String todoId = projection.getTaskId().toString();
			String creator = projection.getUserId().toString();
			List<String> likerConverter = projection.getLikers().stream()
					.map(userIdAsObject -> Objects.toString(userIdAsObject, null))
					.collect(Collectors.toList());
			TaskAggregate aggregate = new TaskAggregate.TodoBuilder(todoId, projectId.toString(), creator)
										.name(projection.getName())
										.description(projection.getDescription())
										.date(projection.getDeadline())
										.state(projection.getState())
										.withLikes(likerConverter)
										.build();
			aggregates.add(aggregate);
		}
		return aggregates;
	}

	@Override
	public boolean delete(String todoId) {
		try {
			dao.deleteById(new ObjectId(todoId));
		} catch(Throwable t) {
			return false;
		}
		return true;
	}

}
