package de.valentin.master.core.appservices.events.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllEventsRepository {
	private ProjectCreatedRepository projectCreatedRepository;
	private ProjectJoinedRepository projectJoinedRepository;
	private TodoCreatedRepository todoCreatedRepository;
	private TodoFinishedRepository todoFinishedRepository;
	private TodoLikedRepository todoLikedRepository;
	private UserLoggedInRepository userLoggedInRepository;
	private UserRegisteredRepository userRegisteredRepository;
	
	@Autowired
	public AllEventsRepository(ProjectCreatedRepository projectCreatedRepository,
			ProjectJoinedRepository projectJoinedRepository, TodoCreatedRepository todoCreatedRepository,
			TodoFinishedRepository todoFinishedRepository, TodoLikedRepository todoLikedRepository,
			UserLoggedInRepository userLoggedInRepository, UserRegisteredRepository userRegisteredRepository) {
		super();
		this.projectCreatedRepository = projectCreatedRepository;
		this.projectJoinedRepository = projectJoinedRepository;
		this.todoCreatedRepository = todoCreatedRepository;
		this.todoFinishedRepository = todoFinishedRepository;
		this.todoLikedRepository = todoLikedRepository;
		this.userLoggedInRepository = userLoggedInRepository;
		this.userRegisteredRepository = userRegisteredRepository;
	}

	public ProjectCreatedRepository getProjectCreatedRepository() {
		return projectCreatedRepository;
	}

	public ProjectJoinedRepository getProjectJoinedRepository() {
		return projectJoinedRepository;
	}

	public TodoCreatedRepository getTodoCreatedRepository() {
		return todoCreatedRepository;
	}

	public TodoFinishedRepository getTodoFinishedRepository() {
		return todoFinishedRepository;
	}

	public TodoLikedRepository getTodoLikedRepository() {
		return todoLikedRepository;
	}

	public UserLoggedInRepository getUserLoggedInRepository() {
		return userLoggedInRepository;
	}

	public UserRegisteredRepository getUserRegisteredRepository() {
		return userRegisteredRepository;
	}	
}
