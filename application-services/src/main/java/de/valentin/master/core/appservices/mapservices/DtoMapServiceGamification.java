package de.valentin.master.core.appservices.mapservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import de.valentin.master.core.appservices.dto.AvatarData;
import de.valentin.master.core.appservices.dto.GroupChallengeData;
import de.valentin.master.core.appservices.dto.ItemData;
import de.valentin.master.core.appservices.dto.UserChallengeData;
import de.valentin.master.core.avatar.AvatarAggregate;
import de.valentin.master.core.avatar.AvatarAggregate.AvatarBuilder;
import de.valentin.master.core.groupchallenge.GroupChallengeAggregate;
import de.valentin.master.core.item.ItemAggregate;
import de.valentin.master.core.shared_model.ItemId;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.userchallenge.UserChallengeAggregate;

@Component
public class DtoMapServiceGamification implements DtoMapperGamification{

	
	public List<AvatarData> convertAvatarAggregatesToAvatarDtos(List<AvatarAggregate> aggregates) {
		List<AvatarData> dtos = new ArrayList<>();
		for (AvatarAggregate aggregate : aggregates) {
			AvatarData dto = convertAvatarAggregateToAvatarDto(aggregate);
			dtos.add(dto);
		}
		return dtos;
	}

	
	public List<AvatarAggregate> convertAvatarDtosToAvatarAggregates(List<AvatarData> dtos) {
		List<AvatarAggregate> aggregates = new ArrayList<>();
		for (AvatarData dto : dtos) {
			AvatarAggregate aggregate = convertAvatarDtoToAvatarAggregate(dto);
			aggregates.add(aggregate);
		}
		return aggregates;
	}

	
	public AvatarData convertAvatarAggregateToAvatarDto(AvatarAggregate aggregate) {
		AvatarData dto = new AvatarData();
		for (Map.Entry<String, ItemId> equippedItem : aggregate.getRootEntity().getAvatarItems().entrySet()) {
			dto.setEquippedItem(equippedItem.getKey(), equippedItem.getValue().toString());
		}
		for (ItemId userItem : aggregate.getRootEntity().getUserItems()) {
			dto.setUserItem(userItem.toString());
		}
		dto.setExpression(aggregate.getRootEntity().getFacialExpression().toString());
		dto.setFace(aggregate.getRootEntity().getFace().toString());
		dto.setHairColor(aggregate.getRootEntity().getHairColor().toString());
		dto.setPoints(aggregate.getRootEntity().getPoints());
		dto.setSkinColor(aggregate.getRootEntity().getSkinColor().toString());
		dto.setUserId(aggregate.getRootEntity().getUserId().toString());
		
		return dto;
	}

	
	public AvatarAggregate convertAvatarDtoToAvatarAggregate(AvatarData dto) {
		AvatarBuilder builder = new AvatarAggregate.AvatarBuilder(new UserId(dto.getUserId()))
										.expression(dto.getExpression())
										.face(dto.getFace())
										.hairColor(dto.getHairColor())
										.points(dto.getPoints())
										.skinColor(dto.getSkinColor());
		for (Map.Entry<String, String> equippedItem : dto.getEquippedItems().entrySet()) {
			builder.setEquippedItem(equippedItem.getKey(), new ItemId(equippedItem.getValue()));
		}
		for (String userItem : dto.getUserItems()) {
			builder.setUserItem(new ItemId(userItem));
		}
		return builder.build();		
	}

	
	public List<ItemData> convertItemAggregatesToItemDtos(List<ItemAggregate> aggregates) {
		List<ItemData> dtos = new ArrayList<>();
		for (ItemAggregate aggregate : aggregates) {
			ItemData dto = convertItemAggregateToItemDto(aggregate);
			dtos.add(dto);
		}
		return dtos;
	}

	
	public List<ItemAggregate> convertItemDtosToItemAggregates(List<ItemData> dtos) {
		List<ItemAggregate> aggregates = new ArrayList<>();
		for (ItemData dto : dtos) {
			ItemAggregate aggregate = convertItemDtoToItemAggregate(dto);
			aggregates.add(aggregate);
		}
		return aggregates;
	}

	
	public ItemData convertItemAggregateToItemDto(ItemAggregate aggregate) {
		ItemData dto = new ItemData();
		dto.setDescription(aggregate.getRootEntity().getDescription());
		dto.setItemId(aggregate.getRootEntity().getItemId().toString());
		dto.setModelId(aggregate.getRootEntity().getModelId());
		dto.setName(aggregate.getRootEntity().getName());
		dto.setType(aggregate.getRootEntity().getItemType().toString());
		dto.setPrice(aggregate.getRootEntity().getPrice());
		return dto;
	}

	
	public ItemAggregate convertItemDtoToItemAggregate(ItemData dto) {
		ItemAggregate aggregate = new ItemAggregate.ItemBuilder(dto.getItemId())
									.description(dto.getDescription())
									.itemType(dto.getType())
									.modelId(dto.getModelId())
									.name(dto.getName())
									.setPrice(dto.getPrice())
									.build();
		return aggregate;
	}

	
	public List<UserChallengeData> converUserEventAggregatesToUserEventDtos(List<UserChallengeAggregate> aggregates) {
		List<UserChallengeData> dtos = new ArrayList<>();
		for (UserChallengeAggregate aggregate : aggregates) {
			UserChallengeData dto = convertUserEventAggregateToUserEventDto(aggregate);
			dtos.add(dto);
		}
		return dtos;
	}

	
	public List<UserChallengeAggregate> convertUserEventDtosToUserEventAggregates(List<UserChallengeData> dtos) {
		List<UserChallengeAggregate> aggregates = new ArrayList<>();
		for (UserChallengeData dto : dtos) {
			UserChallengeAggregate aggregate = convertUserEventDtoToUserEventAggregate(dto);
			aggregates.add(aggregate);
		}
		return aggregates;
	}

	
	public UserChallengeData convertUserEventAggregateToUserEventDto(UserChallengeAggregate aggregate) {
		UserChallengeData dto = new UserChallengeData();
		dto.setActive(aggregate.getRootEntity().isActivated());;
		dto.setUserChallengeId(aggregate.getRootEntity().getUserChallengeId());
		dto.setEvent(aggregate.getRootEntity().getEvent());
		dto.setQueryKeyWord(aggregate.getRootEntity().getQueryKeyword());
		dto.setBeginning(aggregate.getRootEntity().getBeginning());
		dto.setEnd(aggregate.getRootEntity().getEnd());
		dto.setCondition(aggregate.getRootEntity().getCondition());
		dto.setRewardPoints(aggregate.getRootEntity().getRewardPoints());
		try {
			dto.setRewardItem(aggregate.getRootEntity().getRewardItem().toString());
		} catch(NullPointerException e) {
			// do nothing
		}
		
		return dto;
	}

	
	public UserChallengeAggregate convertUserEventDtoToUserEventAggregate(UserChallengeData dto) {
		UserChallengeAggregate aggregate = new UserChallengeAggregate.UserChallengeBuilder(dto.getUserChallengeId(), dto.getEvent())
											.queryKeyword(dto.getQueryKeyword())
											.beginning(dto.getBeginning())
											.end(dto.getEnd())
											.condition(dto.getCondition())
											.rewardPoints(dto.getRewardPoints())
											.rewardItem(new ItemId(dto.getRewardItem()))
											.isActive(dto.isActive())
											.build();
		return aggregate;
	}

	
	public List<GroupChallengeData> convertGroupEventAggregatesToGroupEventDtos(List<GroupChallengeAggregate> aggregates) {
		List<GroupChallengeData> dtos = new ArrayList<>();
		for (GroupChallengeAggregate aggregate : aggregates) {
			GroupChallengeData dto = convertGroupEventAggregateToGroupEventDto(aggregate);
			dtos.add(dto);
		}
		return dtos;
	}

	
	public List<GroupChallengeAggregate> convertGroupEventDtosToGroupEventAggregates(List<GroupChallengeData> dtos) {
		List<GroupChallengeAggregate> aggregates = new ArrayList<>();
		for (GroupChallengeData dto : dtos) {
			GroupChallengeAggregate aggregate = convertGroupEventDtoToGroupEventAggregate(dto);
			aggregates.add(aggregate);
		}
		return aggregates;
	}

	
	public GroupChallengeData convertGroupEventAggregateToGroupEventDto(GroupChallengeAggregate aggregate) {
		GroupChallengeData dto = new GroupChallengeData();
		dto.setGroupChallengeId(aggregate.getRootEntity().getGroupChallengeId());
		dto.setRunning(aggregate.getRootEntity().isRunning());
		dto.setEventName(aggregate.getRootEntity().getEventName());
		dto.setDescription(aggregate.getRootEntity().getDescription());
		dto.setBeginning(aggregate.getRootEntity().getBeginning());
		dto.setEnd(aggregate.getRootEntity().getEnd());
		dto.setCondition(aggregate.getRootEntity().getCondition());
		dto.setRewardPoints(aggregate.getRootEntity().getRewardPoints());
		try {
			dto.setRewardItem(aggregate.getRootEntity().getRewardItem().toString());
		} catch(NullPointerException e) {
			// do nothing
		}
		
		return dto;
	}

	
	public GroupChallengeAggregate convertGroupEventDtoToGroupEventAggregate(GroupChallengeData dto) {
		GroupChallengeAggregate aggregate = new GroupChallengeAggregate.GroupChallengeBuilder(
					dto.getGroupChallengeId()
				).eventName(dto.getEventName())
				.description(dto.getDescription())
				.beginningDate(dto.getBeginning())
				.endDate(dto.getEnd())
				.condition(dto.getCondition())
				.rewards(dto.getRewardPoints(), dto.getRewardItem())
				.build();
		return aggregate;
	}

}
