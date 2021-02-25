package de.valentin.master.core.appservices;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import de.valentin.master.core.appservices.dto.AvatarData;
import de.valentin.master.core.appservices.dto.GroupChallengeData;
import de.valentin.master.core.appservices.dto.ItemData;
import de.valentin.master.core.appservices.dto.UserChallengeData;
import de.valentin.master.core.appservices.events.dto.RewardGainedData;
import de.valentin.master.core.appservices.events.repositories.AllEventsRepository;
import de.valentin.master.core.appservices.events.repositories.RewardGainedRepository;
import de.valentin.master.core.appservices.internalevents.TemporaryPointsAdded;
import de.valentin.master.core.appservices.mapservices.DtoMapperGamification;
import de.valentin.master.core.appservices.repositories.AvatarRepository;
import de.valentin.master.core.appservices.repositories.GroupChallengeRepository;
import de.valentin.master.core.appservices.repositories.ItemRepository;
import de.valentin.master.core.appservices.repositories.UserChallengeRepository;
import de.valentin.master.core.appservices.repositories.TemporaryPointsRepository;
import de.valentin.master.core.avatar.AvatarAggregate;
import de.valentin.master.core.groupchallenge.GroupChallengeAggregate;
import de.valentin.master.core.item.ItemAggregate;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.temporarypoints.TemporaryPointsAggregate;
import de.valentin.master.core.userchallenge.UserChallengeAggregate;
import de.valentin.master.core.shared_model.ItemId;
import de.valentin.master.core.shared_model.ProjectId;

@Service
public class GamingApplicationService {
	
	private UserChallengeRepository ruleRepository;
	private GroupChallengeRepository groupEventRepository;
	private AvatarRepository avatarRepository;
	private TemporaryPointsRepository temporaryPointsRepository;
	private ItemRepository itemRepository;
	private AllEventsRepository eventRepositories;
	private RewardGainedRepository rewardGainedRepository;
	
	private DtoMapperGamification dtoMapper;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Autowired
	public GamingApplicationService(UserChallengeRepository ruleRepository, AvatarRepository avatarRepository,
									TemporaryPointsRepository temporaryPointsRepository,
									ItemRepository itemRepository, GroupChallengeRepository groupEventRepository,
									DtoMapperGamification dtoMapper, ApplicationEventPublisher applicationEventPublisher,
									AllEventsRepository eventRepositories, RewardGainedRepository rewardGainedRepository) {
		this.ruleRepository = ruleRepository;
		this.avatarRepository = avatarRepository;
		this.temporaryPointsRepository = temporaryPointsRepository;
		this.itemRepository = itemRepository;
		this.groupEventRepository = groupEventRepository;
		this.eventRepositories = eventRepositories;
		this.rewardGainedRepository = rewardGainedRepository;
		
		this.applicationEventPublisher = applicationEventPublisher;
		this.dtoMapper = dtoMapper;
	}
	
	public String createNewGroupEvent(GroupChallengeData dto) {
		String challengeId = new ObjectId().toString();
		GroupChallengeAggregate aggregate = new GroupChallengeAggregate.GroupChallengeBuilder(
				challengeId
								).isRunning(false)
								.eventType(dto.getEventType())
								.description(dto.getDescription())
								.eventName(dto.getEventName())
								.condition(dto.getCondition())
								.endDate(dto.getEnd())
								.rewards(dto.getRewardPoints(), dto.getRewardItem())
								.build();
		try {
			groupEventRepository.save(aggregate);
			return challengeId;
		} catch(Exception e) {
			return "";
		}
	}
	
	public List<GroupChallengeData> getAllGroupChallenges() {
		List<GroupChallengeAggregate> groupEvents = groupEventRepository.retrieve();
		return dtoMapper.convertGroupEventAggregatesToGroupEventDtos(groupEvents);
	}
	
	public List<GroupChallengeData> getActiveGroupChallenges() {
		List<GroupChallengeAggregate> groupEvents = groupEventRepository.retrieve(true);
		return dtoMapper.convertGroupEventAggregatesToGroupEventDtos(groupEvents);
	}
	
	public List<String> getUsersThatParticipated(String challengeId) {
		List<RewardGainedData> userChallenges = rewardGainedRepository.retrieve(challengeId);
		List<String> participators = new ArrayList<>();
		for (RewardGainedData userChallenge : userChallenges) {
			participators.add(userChallenge.getUserId());
		}
		
		return participators;
	}
	
	public List<GroupChallengeData> getAllRunningGroupEvents() {
		boolean isRunning = true;
		List<GroupChallengeAggregate> groupEvents = groupEventRepository.retrieve(isRunning);
		return dtoMapper.convertGroupEventAggregatesToGroupEventDtos(groupEvents);
	}
	
	public void activateGroupChallenge(String challengeId) {
		GroupChallengeAggregate groupEvent = groupEventRepository.retrieve(challengeId);
		groupEvent.getRootEntity().activate();
		groupEventRepository.save(groupEvent);
	}
	
	public void deactivateGroupChallenge(String challengeId) {
		GroupChallengeAggregate groupEvent = groupEventRepository.retrieve(challengeId);
		groupEvent.getRootEntity().deactivate();
		groupEventRepository.save(groupEvent);
	}
	
	public String createNewItem(ItemData dto) {
		String itemId = new ObjectId().toString();
		dto.setItemId(itemId);
		ItemAggregate aggregate = dtoMapper.convertItemDtoToItemAggregate(dto);
		itemRepository.save(aggregate);
		return itemId;
	}
	
	public List<ItemData> getAllItems() {
		List<ItemAggregate> items = itemRepository.retrieve();
		return dtoMapper.convertItemAggregatesToItemDtos(items);
	}
	
	public List<ItemData> getUserItems(String userId) {
		List<ItemData> items = new ArrayList<>();
		AvatarAggregate avatar = avatarRepository.retrieve(new UserId(userId));
		List<ItemId> userItems = avatar.getRootEntity().getUserItems();
		for (ItemId userItem : userItems) {
			ItemAggregate itemAggregate = itemRepository.retrieve(userItem.toString());
			ItemData itemData = dtoMapper.convertItemAggregateToItemDto(itemAggregate);
			items.add(itemData);
		}
		return items;
	}
	
	public List<ItemData> getBuyableItems() {
		boolean buyable = true;
		List<ItemAggregate> items = itemRepository.retrieve(buyable);
		return dtoMapper.convertItemAggregatesToItemDtos(items);
	}
	
	public void deleteItem(ItemId itemId) {
		itemRepository.delete(itemId.toString());
	}
	
	
	public void initializeAvatar(UserId userId) {
		AvatarAggregate aggregate = new AvatarAggregate.AvatarBuilder(userId).build();
		try {
			avatarRepository.save(aggregate);
		} catch (OptimisticLockingFailureException e) {
			// repeat function if the aggregate is currently changed in another thread
			initializeAvatar(userId);
		} catch (DuplicateKeyException e) {
			// repeat function if the aggregate is not yet existing in the database and 
			// concurrent changes happen
			initializeAvatar(userId);
		}
	}
	
	public List<AvatarData> getAllAvatars() {
		List<AvatarAggregate> aggregates = avatarRepository.retrieve();
		List<AvatarData> dtos = dtoMapper.convertAvatarAggregatesToAvatarDtos(aggregates);
		return dtos;
	}
	
	public AvatarData getAvatar(String userId) {
		AvatarAggregate aggregate = avatarRepository.retrieve(new UserId(userId));
		AvatarData dto = dtoMapper.convertAvatarAggregateToAvatarDto(aggregate);
		return dto;
	}
	
	public AvatarData changeAvatar(AvatarData avatar) {
		AvatarAggregate aggregate = avatarRepository.retrieve(new UserId(avatar.getUserId()));
		aggregate.getRootEntity().changeLook(avatar.getFace(), avatar.getHairColor(), 
											avatar.getSkinColor(), avatar.getExpression());
		try {
			avatarRepository.save(aggregate);
		} catch (OptimisticLockingFailureException e) {
			// repeat function if the aggregate is currently changed in another thread
			changeAvatar(avatar);
		} catch (DuplicateKeyException e) {
			// repeat function if the aggregate is not yet existing in the database and 
			// concurrent changes happen
			changeAvatar(avatar);
		}
		AvatarData newAvatar = dtoMapper.convertAvatarAggregateToAvatarDto(aggregate);
		return newAvatar;
	}
	
	public boolean buyItem(String userId, String itemId) {
		ItemAggregate itemAggregate = itemRepository.retrieve(itemId);
		if (itemAggregate == null) {
			return false;
		}
		int itemPrice = itemAggregate.getRootEntity().getPrice();
		
		AvatarAggregate aggregate = avatarRepository.retrieve(new UserId(userId));
		if (aggregate.getRootEntity().getPoints() >= itemPrice) {
			aggregate.getRootEntity().reducePoints(itemPrice);
			aggregate.getRootEntity().addUserItem(new ItemId(itemId));
			try {
				avatarRepository.save(aggregate);
			} catch (OptimisticLockingFailureException e) {
				// repeat function if the aggregate is currently changed in another thread
				buyItem(userId, itemId);
			} catch (DuplicateKeyException e) {
				// repeat function if the aggregate is not yet existing in the database and 
				// concurrent changes happen
				buyItem(userId, itemId);
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean equipItem(String userId, String itemId) {
		AvatarAggregate avatarAggregate = avatarRepository.retrieve(new UserId(userId));
		ItemAggregate itemAggregate = itemRepository.retrieve(itemId);
		boolean check = avatarAggregate.getRootEntity().equipItem(
				itemAggregate.getRootEntity().getItemType().toString(), 
				new ItemId(itemId)
		);
		if (!check) return false;
		try {
			avatarRepository.save(avatarAggregate);
		} catch (OptimisticLockingFailureException e) {
			// repeat function if the aggregate is currently changed in another thread
			equipItem(userId, itemId);
		} catch (DuplicateKeyException e) {
			// repeat function if the aggregate is not yet existing in the database and 
			// concurrent changes happen
			equipItem(userId, itemId);
		}
		return true;
	}
	
	public boolean unequipItem(String userId, String kind) {
		AvatarAggregate avatarAggregate = avatarRepository.retrieve(new UserId(userId));
		boolean check = avatarAggregate.getRootEntity().unequipItem(kind);
		if(!check) return false;
		try {
			avatarRepository.save(avatarAggregate);
		} catch (OptimisticLockingFailureException e) {
			// repeat function if the aggregate is currently changed in another thread
			unequipItem(userId, kind);
		} catch (DuplicateKeyException e) {
			// repeat function if the aggregate is not yet existing in the database and 
			// concurrent changes happen
			unequipItem(userId, kind);
		}
		return true;
	}
	
	public Map<String, ItemData> getEquippedItems(String userId) {
		AvatarAggregate avatarAggregate = avatarRepository.retrieve(new UserId(userId));
		HashMap<String, ItemId> equippedItems = (HashMap<String, ItemId>) avatarAggregate.getRootEntity().getAvatarItems();
		Map<String, ItemData> items = new HashMap<>();
		equippedItems.forEach((k, v) -> {
			ItemAggregate itemAggregate = itemRepository.retrieve(v.toString());
			ItemData item = dtoMapper.convertItemAggregateToItemDto(itemAggregate);
			items.put(k, item);
		});
		return items;
	}
	
	public ItemData getItemBy(String itemId) {
		ItemAggregate itemAggregate = itemRepository.retrieve(itemId);
		return dtoMapper.convertItemAggregateToItemDto(itemAggregate);
	}
	
	public void addTemporaryPoints(String event, String ipAddress) {
		TemporaryPointsAggregate aggregate = temporaryPointsRepository.retrieve(ipAddress);
		int pointsAdded = aggregate.getRootEntity().addPoints(event);
		if (pointsAdded != 0) {
			try {
				temporaryPointsRepository.save(aggregate);
				applicationEventPublisher.publishEvent(new TemporaryPointsAdded(this, ipAddress, 
														pointsAdded));
			} catch (OptimisticLockingFailureException e) {
				// repeat function if the aggregate is currently changed in another thread
				addTemporaryPoints(event, ipAddress);
			} catch (DuplicateKeyException e) {
				// repeat function if the aggregate is not yet existing in the database and 
				// concurrent changes happen
				addTemporaryPoints(event, ipAddress);
			}
		} 
	}
	
	public int getTemporaryPoints(String ipAddress) {
		TemporaryPointsAggregate aggregate = temporaryPointsRepository.retrieve(ipAddress);
		return aggregate.getRootEntity().getPoints();
	}
	
	public void deleteTemporaryPoints() {
		List<TemporaryPointsAggregate> aggregates = temporaryPointsRepository.retrieve();
		for (TemporaryPointsAggregate aggregate : aggregates) {
			long oneDay = 24 * 60 * 60 * 1000;
			Date currentDate = new Date();
			if (currentDate.after(new Date(aggregate.getRootEntity().getCreationDate().getTime() + oneDay))) {
				temporaryPointsRepository.delete(aggregate.getRootEntity().getIpAddress());
			}
		}
	}
	
	public void deleteTemporaryPoints(String ipAddress) {
		temporaryPointsRepository.delete(ipAddress);
	}
	
	public void addTemporaryPointsToAvatar(UserId userId, String ipAddress) {
		TemporaryPointsAggregate tpAggregate = temporaryPointsRepository.retrieve(ipAddress);
		if (tpAggregate == null) {
			return;
		} else {
			AvatarAggregate aAggregate = avatarRepository.retrieve(userId);
			aAggregate.getRootEntity().addPoints(tpAggregate.getRootEntity().getPoints());
			try {
				avatarRepository.save(aAggregate);
			} catch (OptimisticLockingFailureException e) {
				// repeat function if the aggregate is currently changed in another thread
				addTemporaryPointsToAvatar(userId, ipAddress);
			} catch (DuplicateKeyException e) {
				// repeat function if the aggregate is not yet existing in the database and 
				// concurrent changes happen
				addTemporaryPointsToAvatar(userId, ipAddress);
			}
		}
	}
	
	public void createUserEvent(UserChallengeData dto) {
		dto.setActive(true);
		dto.setUserChallengeId(new ObjectId().toString());
		UserChallengeAggregate aggregate = dtoMapper.convertUserEventDtoToUserEventAggregate(dto);
		ruleRepository.save(aggregate);
	}
	
	public List<UserChallengeData> getAllUserEvents() {
		List<UserChallengeAggregate> aggregates = ruleRepository.retrieve();
		List<UserChallengeData> dtos = dtoMapper.converUserEventAggregatesToUserEventDtos(aggregates);
		return dtos;
	}
	
	public boolean deactivateUserChallenge(String ruleId) {
		UserChallengeAggregate userChallenge = ruleRepository.retrieve(ruleId);
		userChallenge.getRootEntity().deactivate();
		try {
			ruleRepository.save(userChallenge);
			return true;
		} catch(Error e) {
			return false;
		}
	}
	
	public boolean activateUserChallenge(String ruleId) {
		UserChallengeAggregate userChallenge = ruleRepository.retrieve(ruleId);
		userChallenge.getRootEntity().activate();
		try {
			ruleRepository.save(userChallenge);
			return true;
		} catch(Error e) {
			return false;
		}
	}
	
	public int getUserPoints(UserId userId) {
		AvatarAggregate avatar = avatarRepository.retrieve(userId);
		return avatar.getRootEntity().getPoints();
	}
	
	public Map<String, Object> getProjectStatistics(String projectId) {
		Object tasksCreatedData = eventRepositories.getTodoCreatedRepository().retrieve(new ProjectId(projectId));
		Object tasksFinishedData = eventRepositories.getTodoFinishedRepository().retrieve(new ProjectId(projectId));
		Object tasksLikedData = eventRepositories.getTodoLikedRepository().retrieve(new ProjectId(projectId));
		
		Map<String, Object> statistics = new HashMap<>();
		statistics.put("tasksCreated", tasksCreatedData);
		statistics.put("tasksFinished", tasksFinishedData);
		statistics.put("tasksLiked", tasksLikedData);
		
		return statistics;
	}
	
	public Map<String, Object> getStatistics() {
		Object tasksCreatedData = eventRepositories.getTodoCreatedRepository().retrieve();
		Object tasksFinishedData = eventRepositories.getTodoFinishedRepository().retrieve();
		Object tasksLikedData = eventRepositories.getTodoLikedRepository().retrieve();
		Object projectsCreatedData = eventRepositories.getProjectCreatedRepository().retrieve();
		Object projectsJoinedData = eventRepositories.getProjectJoinedRepository().retrieve();
		Object loggedInData = eventRepositories.getUserLoggedInRepository().retrieve();
		Object RegisteredData = eventRepositories.getUserRegisteredRepository().retrieve();
		
		Map<String, Object> statistics = new HashMap<>();
		statistics.put("Tasks created", tasksCreatedData);
		statistics.put("Tasks finished", tasksFinishedData);
		statistics.put("Tasks liked", tasksLikedData);
		statistics.put("Projects created", projectsCreatedData);
		statistics.put("Projects joined", projectsJoinedData);
		statistics.put("Logins", loggedInData);
		statistics.put("Registrations", RegisteredData);
		
		return statistics;
	}
	
	public Map<String, Map<Integer, Integer>> getGamificationStatistics() {
		List<RewardGainedData> finishedChallenges = rewardGainedRepository.retrieve();
		
		// Map Event-Id on another map that maps the 
		// week to the # of occurrences in that week:
		Map<String, Map<Integer, Integer>> statistics = new HashMap<>();
		for(RewardGainedData data: finishedChallenges) {
			LocalDate date =
				    Instant.ofEpochMilli(data.getTimestamp())
				    .atZone(ZoneId.systemDefault()).toLocalDate();
			WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
			int week = date.get(weekFields.weekOfWeekBasedYear());
			if (statistics.containsKey(data.getUserEventId())) {
				Map<Integer, Integer> map_tmp = statistics.get(data.getUserEventId());

				if (map_tmp.containsKey(week)) {
					map_tmp.put(week, map_tmp.get(week)+1);		
				} else {
					map_tmp.put(week, 1);	
				};
				statistics.put(data.getUserEventId(), map_tmp);
			} else {
				Map<Integer, Integer> map_tmp = new HashMap<>();
				map_tmp.put(week, 1);
				statistics.put(data.getUserEventId(), map_tmp);
			}
		}
		
		return statistics;
	}
}
