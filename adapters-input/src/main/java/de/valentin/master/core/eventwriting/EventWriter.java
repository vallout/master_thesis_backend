package de.valentin.master.core.eventwriting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import de.valentin.master.core.appservices.EventApplicationService;
import de.valentin.master.core.appservices.GamingApplicationService;
import de.valentin.master.core.appservices.internalevents.ProjectCreated;
import de.valentin.master.core.appservices.internalevents.ProjectJoined;
import de.valentin.master.core.appservices.internalevents.RewardGained;
import de.valentin.master.core.appservices.internalevents.TaskCreated;
import de.valentin.master.core.appservices.internalevents.TaskFinished;
import de.valentin.master.core.appservices.internalevents.TaskLiked;
import de.valentin.master.core.appservices.internalevents.UserLoggedIn;
import de.valentin.master.core.appservices.internalevents.UserRegistered;
import de.valentin.master.core.challengeexecutioner.UserChallengeExecutioner;

@Controller
public class EventWriter {
	
	EventApplicationService eventApplicationService;
	GamingApplicationService gamingApplicationService;
	UserChallengeExecutioner ruleExecutioner;
	
	@Autowired
	public EventWriter(EventApplicationService applicationService, GamingApplicationService gamingApplicationService,
						UserChallengeExecutioner ruleExecutioner) {
		this.eventApplicationService = applicationService;
		this.gamingApplicationService = gamingApplicationService;
		this.ruleExecutioner = ruleExecutioner;
	}

	@Async
	@EventListener
	public void todoFinished(TaskFinished event) {
		// persist event
		eventApplicationService.addFinishedTodo(event);
		ruleExecutioner.todoFinished(event);
	}

	@Async
	@EventListener
	public void todoCreated(TaskCreated event) {
		// persist event
		eventApplicationService.addCreatedTodo(event);
		ruleExecutioner.todoCreatedRuling(event);
	}

	@Async
	@EventListener
	public void todoLiked(TaskLiked event) {
		// persist event
		eventApplicationService.addLikedTodo(event);
		ruleExecutioner.todoLikedRuling(event);
	}
	
	@Async
	@EventListener
	public void projectCreated(ProjectCreated event) {
		// persist event
		eventApplicationService.addCreatedProject(event);
		ruleExecutioner.projectCreatedRuling(event);
	}
	
	@Async
	@EventListener
	public void projectJoined(ProjectJoined event) {
		// persist event
		eventApplicationService.addJoinedProject(event);
		ruleExecutioner.projectJoinedRuling(event);
	}

	@Async
	@EventListener
	public void UserLoggedin(UserLoggedIn event) {
		// persist event
		eventApplicationService.addUserLoggedIn(event);
	}

	@Async
	@EventListener
	public void userRegistered(UserRegistered event) {
		// persist event
		eventApplicationService.addUserRegistered(event);
		gamingApplicationService.initializeAvatar(event.getUserId());
		gamingApplicationService.addTemporaryPointsToAvatar(event.getUserId(), event.getIpAddress());
		gamingApplicationService.deleteTemporaryPoints(event.getIpAddress());
	}
	
	@Async
	@EventListener
	public void rewardGained(RewardGained event) {
		eventApplicationService.addRewardGained(event);
	}
}
