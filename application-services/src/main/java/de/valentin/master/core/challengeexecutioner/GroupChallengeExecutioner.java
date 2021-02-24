package de.valentin.master.core.challengeexecutioner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import de.valentin.master.core.appservices.dto.AvatarData;
import de.valentin.master.core.appservices.events.dto.ProjectCreatedData;
import de.valentin.master.core.appservices.events.dto.ProjectJoinedData;
import de.valentin.master.core.appservices.events.dto.TaskCreatedData;
import de.valentin.master.core.appservices.events.dto.TaskFinishedData;
import de.valentin.master.core.appservices.events.dto.TaskLikedData;
import de.valentin.master.core.appservices.events.dto.UserLoggedInData;
import de.valentin.master.core.appservices.events.dto.UserRegisteredData;
import de.valentin.master.core.appservices.events.repositories.AllEventsRepository;
import de.valentin.master.core.appservices.internalevents.RewardGained;
import de.valentin.master.core.appservices.mapservices.DtoMapServiceGamification;
import de.valentin.master.core.appservices.repositories.AvatarRepository;
import de.valentin.master.core.appservices.repositories.GroupChallengeRepository;
import de.valentin.master.core.appservices.repositories.ItemRepository;
import de.valentin.master.core.avatar.AvatarAggregate;
import de.valentin.master.core.groupchallenge.GroupChallengeAggregate;
import de.valentin.master.core.item.ItemAggregate;
import de.valentin.master.core.shared_model.ItemId;
import de.valentin.master.core.shared_model.UserId;

@Service
public class GroupChallengeExecutioner {
	ApplicationEventPublisher applicationEventPublisher;
	
	GroupChallengeRepository groupChallengeRepository;
	AvatarRepository avatarRepository;
	
	AllEventsRepository allEventsRepository;
	
	ItemRepository itemRepository;
	
	@Autowired
	public GroupChallengeExecutioner(GroupChallengeRepository groupChallengeRepository, AvatarRepository avatarRepository, 
									AllEventsRepository allEventsRepository, ApplicationEventPublisher applicationEventPublisher,
									ItemRepository itemRepository) {
		this.groupChallengeRepository = groupChallengeRepository;
		this.avatarRepository = avatarRepository;
		this.applicationEventPublisher = applicationEventPublisher;
		
		this.allEventsRepository = allEventsRepository;
		this.itemRepository = itemRepository;
	}
	
	public void groupEventsHandler() {
		boolean runningEvents = true;
		Date now = new Date();
		// get all group events that are marked as "running" and the end date is in the past
		List<GroupChallengeAggregate> groupEvents = groupChallengeRepository.retrieve(runningEvents, now);
		for (GroupChallengeAggregate groupEvent : groupEvents) {
			if (groupEvent.getRootEntity().getType().toString() == "PLATFORM") {
				handleGroupEvent(groupEvent);
			} else if (groupEvent.getRootEntity().getType().toString() == "PROJECT"){
				// TODO: not implemented
			}
		}
	}
	
	private void handleGroupEvent(GroupChallengeAggregate groupChallenge) {
		String eventName = groupChallenge.getRootEntity().getEventName();
		Date beginning = groupChallenge.getRootEntity().getBeginning();
		Date end = groupChallenge.getRootEntity().getEnd();
		int condition = groupChallenge.getRootEntity().getCondition();
		int points = groupChallenge.getRootEntity().getRewardPoints();
		ItemId rewardItem = groupChallenge.getRootEntity().getRewardItem();
		String groupChallengeId = groupChallenge.getRootEntity().getGroupChallengeId();
		
		switch(eventName) {
		case "ProjectCreated":
			List<ProjectCreatedData> projectsCreated = allEventsRepository.getProjectCreatedRepository()
														.retrieve(beginning.getTime(), end.getTime());
			if (projectsCreated.size() >= condition) {
				Set<String> userIds = new HashSet<>();
				for (ProjectCreatedData projectCreated: projectsCreated) {
					userIds.add(projectCreated.getUserId());
				}
				groupChallenge.getRootEntity().isFinished();
				groupChallengeRepository.save(groupChallenge);
				distributeRewards(userIds, points, rewardItem, groupChallengeId);
			}
			break;
		case "ProjectJoined":
			List<ProjectJoinedData> projectsJoined = allEventsRepository.getProjectJoinedRepository()
														.retrieve(beginning.getTime(), end.getTime());
			if (projectsJoined.size() >= condition) {
				Set<String> userIds = new HashSet<>();
				for (ProjectJoinedData projectJoined : projectsJoined) {
					userIds.add(projectJoined.getUserId());
				}
				distributeRewards(userIds, points, rewardItem, groupChallengeId);
			}
			break;
		case "TodoCreated":
			List<TaskCreatedData> todosCreated = allEventsRepository.getTodoCreatedRepository()
														.retrieve(beginning.getTime(), end.getTime());
			if (todosCreated.size() >= condition) {
				Set<String> userIds = new HashSet<>();
				for (TaskCreatedData todoCreated : todosCreated) {
					userIds.add(todoCreated.getUserId());
				}
				distributeRewards(userIds, points, rewardItem, groupChallengeId);
			}
			break;
		case "TodoFinished":
			List<TaskFinishedData> todosFinished = allEventsRepository.getTodoFinishedRepository()
														.retrieve(beginning.getTime(), end.getTime());
			if (todosFinished.size() >= condition) {
				Set<String> userIds = new HashSet<>();
				for (TaskFinishedData todoFinished : todosFinished) {
					userIds.add(todoFinished.getUserId());
				}
				distributeRewards(userIds, points, rewardItem, groupChallengeId);
			}
			break;
		case "TodoLiked":
			List<TaskLikedData> todosLiked = allEventsRepository.getTodoLikedRepository()
														.retrieve(beginning.getTime(), end.getTime());
			if (todosLiked.size() >= condition) {
				Set<String> userIds = new HashSet<>();
				for (TaskLikedData todoLiked : todosLiked) {
					// provide rewards to all creators AND likers
					userIds.add(todoLiked.getCreatorId());
					userIds.add(todoLiked.getLikerId());
				}
				distributeRewards(userIds, points, rewardItem, groupChallengeId);
			}
			break;
		case "UserLoggedIn":
			List<UserLoggedInData> usersLoggedIn = allEventsRepository.getUserLoggedInRepository()
														.retrieve(beginning.getTime(), end.getTime());
			if (usersLoggedIn.size() >= condition) {
				Set<String> userIds = new HashSet<>();
				for (UserLoggedInData userLoggedIn : usersLoggedIn) {
					// provide rewards to all creators AND likers
					userIds.add(userLoggedIn.getUserId());
				}
				distributeRewards(userIds, points, rewardItem, groupChallengeId);
			}
			break;
		case "UserRegistered":
			List<UserRegisteredData> usersRegistered = allEventsRepository.getUserRegisteredRepository()
														.retrieve(beginning.getTime(), end.getTime());
			if (usersRegistered.size() >= condition) {
				Set<String> userIds = new HashSet<>();
				for (AvatarAggregate avatar : avatarRepository.retrieve()) {
					// provide reward to ALL users
					userIds.add(avatar.getRootEntity().getUserId().toString());
				}
				distributeRewards(userIds, points, rewardItem, groupChallengeId);
			}
			break;
		}
	}
	
	private void distributeRewards(Set<String> userIds, int points, ItemId rewardItem, String groupChallengeId) {
		for (String userId : userIds) {
			AvatarAggregate avatar = avatarRepository.retrieve(new UserId(userId));
			if (points == 0) {
				avatar.getRootEntity().addUserItem(rewardItem);
				ItemAggregate item = itemRepository.retrieve(rewardItem.toString());
				RewardGained eventToPublish = new RewardGained(
						this, userId.toString(), groupChallengeId, "GroupChallenge",
						item.getRootEntity().getName(), 0
				);
				applicationEventPublisher.publishEvent(eventToPublish);
			} else {
				avatar.getRootEntity().addPoints(points);
				RewardGained eventToPublish = new RewardGained(
						this, userId.toString(), groupChallengeId, "GroupChallenge",
						"", points
				);
				applicationEventPublisher.publishEvent(eventToPublish);
			}
		}
	}
}
