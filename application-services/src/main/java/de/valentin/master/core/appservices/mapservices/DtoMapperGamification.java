package de.valentin.master.core.appservices.mapservices;

import java.util.List;

import de.valentin.master.core.appservices.dto.AvatarData;
import de.valentin.master.core.appservices.dto.GroupChallengeData;
import de.valentin.master.core.appservices.dto.ItemData;
import de.valentin.master.core.appservices.dto.UserChallengeData;
import de.valentin.master.core.avatar.AvatarAggregate;
import de.valentin.master.core.groupchallenge.GroupChallengeAggregate;
import de.valentin.master.core.item.ItemAggregate;
import de.valentin.master.core.userchallenge.UserChallengeAggregate;

public interface DtoMapperGamification {
	public List<AvatarData> convertAvatarAggregatesToAvatarDtos(List<AvatarAggregate> aggregates);
	public List<AvatarAggregate> convertAvatarDtosToAvatarAggregates(List<AvatarData> dtos);
	public AvatarData convertAvatarAggregateToAvatarDto(AvatarAggregate aggregate);
	public AvatarAggregate convertAvatarDtoToAvatarAggregate(AvatarData dto);
	
	public List<ItemData> convertItemAggregatesToItemDtos(List<ItemAggregate> aggregates);
	public List<ItemAggregate> convertItemDtosToItemAggregates(List<ItemData> dtos);
	public ItemData convertItemAggregateToItemDto(ItemAggregate aggregate);
	public ItemAggregate convertItemDtoToItemAggregate(ItemData dto);
	
	public List<UserChallengeData> converUserEventAggregatesToUserEventDtos(List<UserChallengeAggregate> aggregates);
	public List<UserChallengeAggregate> convertUserEventDtosToUserEventAggregates(List<UserChallengeData> dtos);
	public UserChallengeData convertUserEventAggregateToUserEventDto(UserChallengeAggregate aggregate);
	public UserChallengeAggregate convertUserEventDtoToUserEventAggregate(UserChallengeData dto);
	
	public List<GroupChallengeData> convertGroupEventAggregatesToGroupEventDtos(List<GroupChallengeAggregate> aggregates);
	public List<GroupChallengeAggregate> convertGroupEventDtosToGroupEventAggregates(List<GroupChallengeData> dtos);
	public GroupChallengeData convertGroupEventAggregateToGroupEventDto(GroupChallengeAggregate aggregate);
	public GroupChallengeAggregate convertGroupEventDtoToGroupEventAggregate(GroupChallengeData dto);
}
