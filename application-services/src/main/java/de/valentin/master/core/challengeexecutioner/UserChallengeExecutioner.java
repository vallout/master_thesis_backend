package de.valentin.master.core.challengeexecutioner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import de.valentin.master.core.appservices.dto.AvatarData;
import de.valentin.master.core.appservices.events.dto.ProjectCreatedData;
import de.valentin.master.core.appservices.events.dto.ProjectJoinedData;
import de.valentin.master.core.appservices.events.dto.TaskCreatedData;
import de.valentin.master.core.appservices.events.dto.TaskFinishedData;
import de.valentin.master.core.appservices.events.dto.TaskLikedData;
import de.valentin.master.core.appservices.events.dto.UserLoggedInData;
import de.valentin.master.core.appservices.events.repositories.ProjectCreatedRepository;
import de.valentin.master.core.appservices.events.repositories.ProjectJoinedRepository;
import de.valentin.master.core.appservices.events.repositories.TodoCreatedRepository;
import de.valentin.master.core.appservices.events.repositories.TodoFinishedRepository;
import de.valentin.master.core.appservices.events.repositories.TodoLikedRepository;
import de.valentin.master.core.appservices.events.repositories.UserLoggedInRepository;
import de.valentin.master.core.appservices.internalevents.ProjectCreated;
import de.valentin.master.core.appservices.internalevents.ProjectJoined;
import de.valentin.master.core.appservices.internalevents.RewardGained;
import de.valentin.master.core.appservices.internalevents.TaskCreated;
import de.valentin.master.core.appservices.internalevents.TaskFinished;
import de.valentin.master.core.appservices.internalevents.TaskLiked;
import de.valentin.master.core.appservices.internalevents.UserLoggedIn;
import de.valentin.master.core.appservices.mapservices.DtoMapperGamification;
import de.valentin.master.core.appservices.repositories.AvatarRepository;
import de.valentin.master.core.appservices.repositories.ItemRepository;
import de.valentin.master.core.appservices.repositories.UserChallengeRepository;
import de.valentin.master.core.avatar.AvatarAggregate;
import de.valentin.master.core.item.ItemAggregate;
import de.valentin.master.core.shared_model.ItemId;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.userchallenge.UserChallengeAggregate;

@Service
public class UserChallengeExecutioner {
	
	ApplicationEventPublisher applicationEventPublisher;
	DtoMapperGamification dtoMapper;
		
	TodoCreatedRepository todoCreatedRepository;
	TodoLikedRepository todoLikedRepository;
	TodoFinishedRepository todoFinishedRepository;
	UserLoggedInRepository userLoggedInRepository;
	ProjectCreatedRepository projectCreatedRepository;
	ProjectJoinedRepository projectJoinedRepository;
	
	AvatarRepository avatarRepository;
	UserChallengeRepository ruleRepository;
	ItemRepository itemRepository;
	
	@Autowired
	public UserChallengeExecutioner(ApplicationEventPublisher applicationEventPublisher,
							DtoMapperGamification dtoMapper,
							TodoCreatedRepository todoCreatedRepository, 
							TodoLikedRepository todoLikedRepository,
							TodoFinishedRepository todoFinishedRepository,
							AvatarRepository avatarRepository,
							UserLoggedInRepository userLoggedInRepository,
							ProjectCreatedRepository projectCreatedRepository,
							ProjectJoinedRepository projectJoinedRepository,
							UserChallengeRepository ruleRepository,
							ItemRepository itemRepository) {
		
		this.applicationEventPublisher = applicationEventPublisher;
		this.dtoMapper = dtoMapper;
		
		this.todoCreatedRepository = todoCreatedRepository;
		this.todoLikedRepository = todoLikedRepository;
		this.todoFinishedRepository = todoFinishedRepository;
		this.avatarRepository = avatarRepository;
		this.userLoggedInRepository = userLoggedInRepository;
		this.projectCreatedRepository = projectCreatedRepository;
		this.projectJoinedRepository = projectJoinedRepository;
		
		this.ruleRepository = ruleRepository;
		this.itemRepository = itemRepository;
	}
	
	public void todoCreatedRuling(TaskCreated newEvent) {
		System.out.println(newEvent.getClass().getSimpleName());
		List<UserChallengeAggregate> rules = ruleRepository.retrieveActiveUserEvents(newEvent.getClass().getSimpleName());
		List<TaskCreatedData> events = new ArrayList<TaskCreatedData>();
		
		for (UserChallengeAggregate rule : rules) {
			System.out.println(rule.toString());
			switch(rule.getRootEntity().getQueryKeyword()) {
			case "byUser":
				events = todoCreatedRepository.retrieve(newEvent.getUserId());
				break;
			case "byUserAndProject":
				events = todoCreatedRepository.retrieve(newEvent.getUserId(), newEvent.getProjectId());
				break;
			case "byUserAndTime":
				events = todoCreatedRepository.retrieve(newEvent.getUserId(), 
						rule.getRootEntity().getBeginning().getTime(), 
						rule.getRootEntity().getEnd().getTime());
				break;
			case "byUserAndProjectAndTime":
				events = todoCreatedRepository.retrieve(newEvent.getUserId(), 
						newEvent.getProjectId(),
						rule.getRootEntity().getBeginning().getTime(), 
						rule.getRootEntity().getEnd().getTime());
				break;
			default:
				return;
			}
			addRewardsIfConditionIsMet(rule, events.size(), newEvent.getUserId());
		}
	}	
	
	public void todoFinished(TaskFinished newEvent) {
		List<UserChallengeAggregate> rules = ruleRepository.retrieveActiveUserEvents(newEvent.getClass().getSimpleName());
		List<TaskFinishedData> events = new ArrayList<>();
		
		for (UserChallengeAggregate rule : rules) {
			switch(rule.getRootEntity().getQueryKeyword()) {
			case "byUser":
				events = todoFinishedRepository.retrieve(newEvent.getUserId());
				break;
			case "byUserAndProject":
				events = todoFinishedRepository.retrieve(newEvent.getUserId(), newEvent.getProjectId());
				break;
			case "byUserAndTime":
				events = todoFinishedRepository.retrieve(newEvent.getUserId(), 
						rule.getRootEntity().getBeginning().getTime(), 
						rule.getRootEntity().getEnd().getTime());
				break;
			case "byUserAndProjectAndTime":
				events = todoFinishedRepository.retrieve(newEvent.getUserId(), 
						newEvent.getProjectId(),
						rule.getRootEntity().getBeginning().getTime(), 
						rule.getRootEntity().getEnd().getTime());
				break;
			default:
				return;
			}
			addRewardsIfConditionIsMet(rule, events.size(), newEvent.getUserId());
		}
	}
	
	public void loggedinRuling(UserLoggedIn newEvent) {
		List<UserChallengeAggregate> rules = ruleRepository.retrieveActiveUserEvents(newEvent.getClass().getSimpleName());
		List<UserLoggedInData> events = new ArrayList<>();
		
		for (UserChallengeAggregate rule : rules) {
			switch(rule.getRootEntity().getQueryKeyword()) {
			case "byUser":
				events = userLoggedInRepository.retrieve(newEvent.getUserId());
				break;
			case "byUserAndTime":
				events = userLoggedInRepository.retrieve(newEvent.getUserId(), 
						rule.getRootEntity().getBeginning().getTime(), 
						rule.getRootEntity().getEnd().getTime());
				break;
			default:
				return;
			}
			addRewardsIfConditionIsMet(rule, events.size(), newEvent.getUserId());
		}
	}
	
	public void projectCreatedRuling(ProjectCreated newEvent) {
		List<UserChallengeAggregate> rules = ruleRepository.retrieveActiveUserEvents(newEvent.getClass().getSimpleName());
		List<ProjectCreatedData> events = new ArrayList<>();
		
		for (UserChallengeAggregate rule : rules) {
			switch(rule.getRootEntity().getQueryKeyword()) {
			case "byUser":
				events = projectCreatedRepository.retrieve(newEvent.getUserId());
				break;
			case "byUserAndTime":
				events = projectCreatedRepository.retrieve(newEvent.getUserId(), 
						rule.getRootEntity().getBeginning().getTime(), 
						rule.getRootEntity().getEnd().getTime());
				break;
			default:
				return;
			}
			addRewardsIfConditionIsMet(rule, events.size(), newEvent.getUserId());
		}
	}
	
	public void projectJoinedRuling(ProjectJoined newEvent) {
		List<UserChallengeAggregate> rules = ruleRepository.retrieveActiveUserEvents(newEvent.getClass().getSimpleName());
		List<ProjectJoinedData> events = new ArrayList<>();
		
		for (UserChallengeAggregate rule : rules) {
			switch(rule.getRootEntity().getQueryKeyword()) {
			case "byUser":
				events = projectJoinedRepository.retrieve(newEvent.getUserId());
				break;
			case "byUserAndTime":
				events = projectJoinedRepository.retrieve(newEvent.getUserId(), 
						rule.getRootEntity().getBeginning().getTime(), 
						rule.getRootEntity().getEnd().getTime());
				break;
			default:
				return;
			}
			addRewardsIfConditionIsMet(rule, events.size(), newEvent.getUserId());
		}
	}
	
	public void todoLikedRuling(TaskLiked newEvent) {
		List<UserChallengeAggregate> rules = ruleRepository.retrieveActiveUserEvents(newEvent.getClass().getSimpleName());
		List<TaskLikedData> events = new ArrayList<>();
		
		for (UserChallengeAggregate rule : rules) {
			System.out.println(rule.getRootEntity().getUserChallengeId());
			System.out.println(rule.getRootEntity().getQueryKeyword());
			switch(rule.getRootEntity().getQueryKeyword()) {
			case "byCreator":
				events = todoLikedRepository.retrieve("Creator", newEvent.getCreator());
				addRewardsIfConditionIsMet(rule, events.size(), newEvent.getCreator());
				break;
			case "byCreatorAndProject":
				events = todoLikedRepository.retrieve("Creator", newEvent.getCreator(), 
						newEvent.getProjectId());
				addRewardsIfConditionIsMet(rule, events.size(), newEvent.getCreator());
				break;
			case "byCreatorAndTime":
				events = todoLikedRepository.retrieve("Creator", newEvent.getCreator(), 
						rule.getRootEntity().getBeginning().getTime(), 
						rule.getRootEntity().getEnd().getTime());
				addRewardsIfConditionIsMet(rule, events.size(), newEvent.getCreator());
				break;
			case "byCreatorAndProjectAndTime":
				events = todoLikedRepository.retrieve("Creator", newEvent.getCreator(), 
						newEvent.getProjectId(),
						rule.getRootEntity().getBeginning().getTime(), 
						rule.getRootEntity().getEnd().getTime());
				addRewardsIfConditionIsMet(rule, events.size(), newEvent.getCreator());
				break;
			case "byLiker":
				events = todoLikedRepository.retrieve("Liker", newEvent.getCreator());
				addRewardsIfConditionIsMet(rule, events.size(), newEvent.getLiker());
				break;
			case "byLikerAndProject":
				events = todoLikedRepository.retrieve("Liker", newEvent.getCreator(), 
						newEvent.getProjectId());
				addRewardsIfConditionIsMet(rule, events.size(), newEvent.getLiker());
				break;
			case "byLikerAndTime":
				events = todoLikedRepository.retrieve("Liker", newEvent.getCreator(), 
						rule.getRootEntity().getBeginning().getTime(), 
						rule.getRootEntity().getEnd().getTime());
				addRewardsIfConditionIsMet(rule, events.size(), newEvent.getLiker());
				break;
			case "byLikerAndProjectAndTime":
				events = todoLikedRepository.retrieve("Liker", newEvent.getCreator(), 
						newEvent.getProjectId(),
						rule.getRootEntity().getBeginning().getTime(), 
						rule.getRootEntity().getEnd().getTime());
				addRewardsIfConditionIsMet(rule, events.size(), newEvent.getLiker());
				break;
			default:
				return;
			}
		}
	}
	
	private void addRewardsIfConditionIsMet(UserChallengeAggregate userChallenge, int numberOfEvents, UserId userId) {
		if (numberOfEvents == userChallenge.getRootEntity().getCondition()) {
			AvatarAggregate avatar = avatarRepository.retrieve(userId);
			ItemId itemId = userChallenge.getRootEntity().getRewardItem();
			if (itemId != null && itemId.toString() != "") {
				avatar.getRootEntity().addUserItem(itemId);
				try {
					avatarRepository.save(avatar);
				} catch (OptimisticLockingFailureException e) {
					// repeat function if the aggregate is currently changed in another thread
					addRewardsIfConditionIsMet(userChallenge, numberOfEvents, userId);
				} catch (DuplicateKeyException e) {
					// repeat function if the aggregate is not yet existing in the database and 
					// concurrent changes happen
					addRewardsIfConditionIsMet(userChallenge, numberOfEvents, userId);
				}
				ItemAggregate item = itemRepository.retrieve(itemId.toString());
				String itemName = item.getRootEntity().getName();
				RewardGained eventToPublish = new RewardGained(
						this, userId.toString(), userChallenge.getRootEntity().getUserChallengeId(), "UserChallenge",
						itemName, 0
				);
				applicationEventPublisher.publishEvent(eventToPublish);
			} else {
				avatar.getRootEntity().addPoints(userChallenge.getRootEntity().getRewardPoints());
				try {
					avatarRepository.save(avatar);
				} catch (OptimisticLockingFailureException e) {
					// repeat function if the aggregate is currently changed in another thread
					addRewardsIfConditionIsMet(userChallenge, numberOfEvents, userId);
				} catch (DuplicateKeyException e) {
					// repeat function if the aggregate is not yet existing in the database and 
					// concurrent changes happen
					addRewardsIfConditionIsMet(userChallenge, numberOfEvents, userId);
				}
				RewardGained eventToPublish = new RewardGained(
						this, userId.toString(), userChallenge.getRootEntity().getUserChallengeId(), "UserChallenge",
						"", userChallenge.getRootEntity().getRewardPoints()
				);
				applicationEventPublisher.publishEvent(eventToPublish);
			}

		}
	}
}
