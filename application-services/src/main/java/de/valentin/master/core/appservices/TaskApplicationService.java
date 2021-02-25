package de.valentin.master.core.appservices;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import de.valentin.master.core.appservices.dto.TaskData;
import de.valentin.master.core.appservices.internalevents.TaskCreated;
import de.valentin.master.core.appservices.internalevents.TaskFinished;
import de.valentin.master.core.appservices.internalevents.TaskLiked;
import de.valentin.master.core.appservices.mapservices.DtoMapper;
import de.valentin.master.core.appservices.repositories.TaskRepository;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.task.TaskAggregate;

@Service
public class TaskApplicationService {
	
	private TaskRepository taskRepository;
	
	private DtoMapper dtoMapper;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	public TaskApplicationService(TaskRepository taskRepository,
									ApplicationEventPublisher applicationEventPublisher,
									DtoMapper dtoMapper) {
		this.taskRepository = taskRepository;
		
		this.dtoMapper = dtoMapper;
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public TaskData getTaskBy(String todoId) {
		TaskAggregate aggregate = taskRepository.retrieve(todoId);
		return dtoMapper.convertTodoAggregateToTodoDto(aggregate);
	}
	
	public List<TaskData> getTasksBy(UserId userId) {

		List<TaskAggregate> aggregates = taskRepository.retrieve(userId);
		List<TaskData> dtos = dtoMapper.convertTodoAggregatesToTodoDtos(aggregates);
		return dtos;
	}
	
	public List<TaskData> getTasksBy(ProjectId projectId) {
		List<TaskAggregate> aggregates = taskRepository.retrieve(projectId);
		List<TaskData> dtos = dtoMapper.convertTodoAggregatesToTodoDtos(aggregates);
		return dtos;
	}
	
	public TaskData createTask(String userId, String projectId, TaskData todoData) {
		if (checkTaskRestriction(new UserId(userId), 3)) {
			return null;
		}
		String todoId = new ObjectId().toString();
		todoData.setUserId(userId);
		todoData.setProjectId(projectId);
		
		TaskData taskOut = null;
		try {
			taskOut = setTodo(todoId, todoData);
		} catch(Throwable t) {
			t.printStackTrace();
		}
		ProjectId projectIdAsObject = new ProjectId(projectId);
		applicationEventPublisher.publishEvent(new TaskCreated(this, new UserId(userId), projectIdAsObject));
		return taskOut;
	}
	
	public TaskData changeTask(String todoId, TaskData todoData) {
		try {
			return setTodo(todoId, todoData);
		} catch(Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
	
	public void likeTask(String todoId, String liker) {
		TaskAggregate todo = taskRepository.retrieve(todoId);
		todo.getRootEntity().addLiker(new UserId(liker));
		UserId creator = todo.getRootEntity().getCreator();
		taskRepository.save(todo);
		ProjectId projectId = todo.getRootEntity().getProjectId();
		applicationEventPublisher.publishEvent(new TaskLiked(this, creator, new UserId(liker), projectId));
	}
	
	public void unlikeTask(String todoId, String liker) {
		TaskAggregate todo = taskRepository.retrieve(todoId);
		todo.getRootEntity().removeLiker(new UserId(liker));
		taskRepository.save(todo);
	}
	
	public boolean deleteTask(String todoId) {
		return taskRepository.delete(todoId);
	}
	
	private TaskData setTodo(String todoId, TaskData todoData) {
		TaskAggregate oldTodo = taskRepository.retrieve(todoId);
		todoData.setTaskId(todoId);
		if (oldTodo != null) {
			for (UserId liker : oldTodo.getRootEntity().getLikers()) {
				todoData.addLiker(liker.toString());
			}
			todoData.setProjectId(oldTodo.getRootEntity().getProjectId().toString());
			todoData.setUserId(oldTodo.getRootEntity().getCreator().toString());
		}
		TaskAggregate todo = dtoMapper.convertTodoDtoToTodoAggregate(todoData);
		taskRepository.save(todo);
		
		if (oldTodo != null && oldTodo.getRootEntity().getState().toString() != "FINISHED"
				&& todoData.getState().equals("FINISHED")) {
			UserId userId = new UserId(todoData.getUserId());
			ProjectId projectId = new ProjectId(todoData.getProjectId());
			applicationEventPublisher.publishEvent(new TaskFinished(this, userId, projectId));
		}
		return todoData;
	}
	
	private boolean checkTaskRestriction(UserId userId, int restriction) {
		List<TaskAggregate> todos = taskRepository.retrieve(userId);
		return todos.size() > restriction;
	}
}
