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
import de.valentin.master.core.appservices.repositories.TodoRepository;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.task.TaskAggregate;

@Service
public class TaskApplicationService {
	
	private TodoRepository todoRepository;
	
	private DtoMapper dtoMapper;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	public TaskApplicationService(TodoRepository todoRepository,
									ApplicationEventPublisher applicationEventPublisher,
									DtoMapper dtoMapper) {
		this.todoRepository = todoRepository;
		
		this.dtoMapper = dtoMapper;
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public TaskData getTodoBy(String todoId) {
		TaskAggregate aggregate = todoRepository.retrieve(todoId);
		return dtoMapper.convertTodoAggregateToTodoDto(aggregate);
	}
	
	public List<TaskData> getTodosBy(UserId userId) {

		List<TaskAggregate> aggregates = todoRepository.retrieve(userId);
		List<TaskData> dtos = dtoMapper.convertTodoAggregatesToTodoDtos(aggregates);
		return dtos;
	}
	
	public List<TaskData> getTodosBy(ProjectId projectId) {
		List<TaskAggregate> aggregates = todoRepository.retrieve(projectId);
		List<TaskData> dtos = dtoMapper.convertTodoAggregatesToTodoDtos(aggregates);
		return dtos;
	}
	
	public TaskData createTodo(String userId, String projectId, TaskData todoData) {
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
	
	public TaskData changeTodo(String todoId, TaskData todoData) {
		try {
			return setTodo(todoId, todoData);
		} catch(Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
	
	public void likeTodo(String todoId, String liker) {
		TaskAggregate todo = todoRepository.retrieve(todoId);
		todo.getRootEntity().addLiker(new UserId(liker));
		UserId creator = todo.getRootEntity().getCreator();
		todoRepository.save(todo);
		ProjectId projectId = todo.getRootEntity().getProjectId();
		applicationEventPublisher.publishEvent(new TaskLiked(this, creator, new UserId(liker), projectId));
	}
	
	public void unlikeTodo(String todoId, String liker) {
		TaskAggregate todo = todoRepository.retrieve(todoId);
		todo.getRootEntity().removeLiker(new UserId(liker));
		todoRepository.save(todo);
	}
	
	public boolean deleteTodo(String todoId) {
		return todoRepository.delete(todoId);
	}
	
	private TaskData setTodo(String todoId, TaskData todoData) {
		TaskAggregate oldTodo = todoRepository.retrieve(todoId);
		todoData.setTaskId(todoId);
		if (oldTodo != null) {
			for (UserId liker : oldTodo.getRootEntity().getLikers()) {
				todoData.addLiker(liker.toString());
			}
			todoData.setProjectId(oldTodo.getRootEntity().getProjectId().toString());
			todoData.setUserId(oldTodo.getRootEntity().getCreator().toString());
		}
		TaskAggregate todo = dtoMapper.convertTodoDtoToTodoAggregate(todoData);
		todoRepository.save(todo);
		
		if (oldTodo != null && oldTodo.getRootEntity().getState().toString() != "FINISHED"
				&& todoData.getState().equals("FINISHED")) {
			UserId userId = new UserId(todoData.getUserId());
			ProjectId projectId = new ProjectId(todoData.getProjectId());
			applicationEventPublisher.publishEvent(new TaskFinished(this, userId, projectId));
		}
		return todoData;
	}
	
	private boolean checkTaskRestriction(UserId userId, int restriction) {
		List<TaskAggregate> todos = todoRepository.retrieve(userId);
		return todos.size() > restriction;
	}
}
