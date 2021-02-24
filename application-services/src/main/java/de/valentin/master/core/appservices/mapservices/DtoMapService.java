package de.valentin.master.core.appservices.mapservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import de.valentin.master.core.appservices.dto.ProjectData;
import de.valentin.master.core.appservices.dto.TaskData;
import de.valentin.master.core.appservices.dto.UserData;
import de.valentin.master.core.project.ProjectAggregate;
import de.valentin.master.core.project.ProjectAggregate.ProjectBuilder;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.task.TaskAggregate;
import de.valentin.master.core.user.UserAggregate;
import de.valentin.master.core.user.UserAggregate.UserProfileBuilder;

@Component
public class DtoMapService implements DtoMapper{
	
	@Override
	public List<ProjectData> convertAggregateToDto(List<ProjectAggregate> aggregates) {
		List<ProjectData> dtos = new ArrayList<>();
		for (ProjectAggregate aggregate : aggregates) {
			dtos.add(convertAggregateToDto(aggregate));
		}
		return dtos;
	}
	
	@Override
	public List<ProjectAggregate> convertDtoToAggregate(List<ProjectData> dtos) {
		List<ProjectAggregate> aggregates = new ArrayList<>();
		for (ProjectData dto : dtos) {
			aggregates.add(convertDtoToAggregate(dto));
		}
		return aggregates;
	}
	
	@Override
	public ProjectData convertAggregateToDto(ProjectAggregate aggregate) {
		ProjectData dto = new ProjectData();
		dto.setProjectId(aggregate.getProjectRootEntity().getProjectId().toString());
		dto.setName(aggregate.getProjectRootEntity().getName());
		dto.setDescription(aggregate.getProjectRootEntity().getDescription());
		List<String> members = converTo(aggregate.getProjectRootEntity().getMembers());
		dto.setUsers(members);
		return dto;
	}
	
	@Override
	public ProjectAggregate convertDtoToAggregate(ProjectData dto) {
		ProjectBuilder builder = new ProjectAggregate.ProjectBuilder(new ProjectId(dto.getProjectId()))
										.name(dto.getName())
										.description(dto.getDescription());
		for (String member : dto.getUsers()) {
			builder.addMember(member);
		}
		return builder.build();
	}
	
	private List<String> converTo(List<UserId> userIds) {
		List<String> usersAsString = userIds.stream()
				.map(userIdAsObject -> Objects.toString(userIdAsObject, null))
				.collect(Collectors.toList());
		
		return usersAsString;
	}

	@Override
	public List<TaskData> convertTodoAggregatesToTodoDtos(List<TaskAggregate> aggregates) {
		List<TaskData> dtos = new ArrayList<TaskData>();
		for (TaskAggregate aggregate : aggregates) {
			TaskData dto = convertTodoAggregateToTodoDto(aggregate);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<TaskAggregate> converTodoDtosToTodoAggregates(List<TaskData> dtos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskData convertTodoAggregateToTodoDto(TaskAggregate aggregate) {
		TaskData dto = new TaskData();

		dto.setDeadline(aggregate.getRootEntity().getDeadline());
		dto.setDescription(aggregate.getRootEntity().getDescription());
		dto.setName(aggregate.getRootEntity().getName());
		dto.setProjectId(aggregate.getRootEntity().getProjectId().toString());
		dto.setState(aggregate.getRootEntity().getState().toString());
		dto.setTaskId(aggregate.getRootEntity().getId());
		dto.setUserId(aggregate.getRootEntity().getCreator().toString());
		for (UserId liker : aggregate.getRootEntity().getLikers()) {
			dto.addLiker(liker.toString());
		}
		return dto;
	}

	@Override
	public TaskAggregate convertTodoDtoToTodoAggregate(TaskData dto) {
		TaskAggregate todo = new TaskAggregate.TodoBuilder(dto.getTaskId(), dto.getProjectId(), dto.getUserId())
				.name(dto.getName())
				.description(dto.getDescription())
				.date(dto.getDeadline())
				.state(dto.getState())
				.withLikes(dto.getLikers())
				.build();
		return todo;
	}

	@Override
	public List<UserData> convertUserAggregatesToUserDtos(List<UserAggregate> aggregates) {
		List<UserData> dtos = new ArrayList<>();
		for (UserAggregate aggregate : aggregates) {
			UserData dto = convertUserAggregateToUserDto(aggregate);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<UserAggregate> convertUserDtosToUserAggregates(List<UserData> dtos) {
		List<UserAggregate> aggregates = new ArrayList<>();
		for (UserData dto : dtos) {
			UserAggregate aggregate = convertUserDtoToUserAggregate(dto);
			aggregates.add(aggregate);
		}
		return aggregates;
	}

	@Override
	public UserData convertUserAggregateToUserDto(UserAggregate aggregate) {
		UserData dto = new UserData();
		dto.setFirstname(aggregate.getProfile().getFirstname());
		dto.setLastname(aggregate.getProfile().getLastname());
		dto.setPhone(aggregate.getProfile().getPhone());
		dto.setPrimaryMail(aggregate.getProfile().getPrimaryMail());
		dto.setUserId(aggregate.getProfile().getUserId().toString());
		dto.setTitle(aggregate.getProfile().getTitle());
		dto.setPictureId(aggregate.getProfile().getPictureId());
		for (ProjectId projectId : aggregate.getProfile().getProjects()) {
			dto.addProject(projectId.toString());
		}
		return dto;
	}

	@Override
	public UserAggregate convertUserDtoToUserAggregate(UserData dto) {
		UserProfileBuilder builder = new UserAggregate.UserProfileBuilder(dto.getUserId())
										.firstname(dto.getFirstname())
										.lastname(dto.getLastname())
										.phone(dto.getPhone())
										.primaryMail(dto.getPrimaryMail())
										.pictureId(dto.getPictureId())
										.title(dto.getTitle());
		for (String projectId : dto.getProjects()) {
			builder.addProject(projectId);
		}
		return builder.build();
	}
}
