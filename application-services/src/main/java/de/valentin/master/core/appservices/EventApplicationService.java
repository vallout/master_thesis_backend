package de.valentin.master.core.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.valentin.master.core.appservices.events.dto.RewardGainedData;
import de.valentin.master.core.appservices.events.repositories.ProjectCreatedRepository;
import de.valentin.master.core.appservices.events.repositories.ProjectJoinedRepository;
import de.valentin.master.core.appservices.events.repositories.RewardGainedRepository;
import de.valentin.master.core.appservices.events.repositories.TodoCreatedRepository;
import de.valentin.master.core.appservices.events.repositories.TodoFinishedRepository;
import de.valentin.master.core.appservices.events.repositories.TodoLikedRepository;
import de.valentin.master.core.appservices.events.repositories.UserLoggedInRepository;
import de.valentin.master.core.appservices.events.repositories.UserRegisteredRepository;
import de.valentin.master.core.appservices.internalevents.ProjectCreated;
import de.valentin.master.core.appservices.internalevents.ProjectJoined;
import de.valentin.master.core.appservices.internalevents.RewardGained;
import de.valentin.master.core.appservices.internalevents.TaskCreated;
import de.valentin.master.core.appservices.internalevents.TaskFinished;
import de.valentin.master.core.appservices.internalevents.TaskLiked;
import de.valentin.master.core.appservices.internalevents.UserLoggedIn;
import de.valentin.master.core.appservices.internalevents.UserRegistered;

@Service
public class EventApplicationService {

	ProjectCreatedRepository projectCreatedRepository;
	ProjectJoinedRepository projectJoinedRepository;
	TodoCreatedRepository todoCreatedRepository;
	TodoFinishedRepository todoFinishedRepository;
	TodoLikedRepository todoLikedRepository;
	UserLoggedInRepository userLoggedInRepository;
	UserRegisteredRepository userRegisteredRepository;
	RewardGainedRepository rewardGainedRepository;
	
	
	@Autowired
	public EventApplicationService(ProjectCreatedRepository projectCreatedRepository, ProjectJoinedRepository projectJoinedRepository,
									TodoCreatedRepository todoCreatedRepository, TodoFinishedRepository todoFinishedRepository,
									TodoLikedRepository todoLikedRepository, UserLoggedInRepository userLoggedInRepository,
									UserRegisteredRepository userRegisteredRepository, RewardGainedRepository rewardGainedRepository) {
		this.projectCreatedRepository = projectCreatedRepository;
		this.projectJoinedRepository = projectJoinedRepository;
		this.todoCreatedRepository = todoCreatedRepository;
		this.todoFinishedRepository = todoFinishedRepository;
		this.todoLikedRepository = todoLikedRepository;
		this.userLoggedInRepository = userLoggedInRepository;
		this.userRegisteredRepository = userRegisteredRepository;
		this.rewardGainedRepository = rewardGainedRepository;
	}	
	
	public void addCreatedProject(ProjectCreated projectCreated) {
		projectCreatedRepository.save(projectCreated);
	}
	
	public void addJoinedProject(ProjectJoined projectJoined) {
		projectJoinedRepository.save(projectJoined);
	}
	
	public void addCreatedTodo(TaskCreated todoCreated) {
		todoCreatedRepository.save(todoCreated);
	}
	
	public void addFinishedTodo(TaskFinished todoFinished) {
		todoFinishedRepository.save(todoFinished);
	}
	
	public void addLikedTodo(TaskLiked todoLiked) {
		todoLikedRepository.save(todoLiked);
	}
	
	public void addUserLoggedIn(UserLoggedIn userLoggedIn) {
		userLoggedInRepository.save(userLoggedIn);
	}
	
	public void addUserRegistered(UserRegistered userRegistered) {
		userRegisteredRepository.save(userRegistered);
	}
	
	public void addRewardGained(RewardGained rewardGained) {
		RewardGainedData data = new RewardGainedData(
									rewardGained.getChallengeId(), 
									rewardGained.getUserId(),
									rewardGained.getTimestamp());
		rewardGainedRepository.save(data);
	}
}
 