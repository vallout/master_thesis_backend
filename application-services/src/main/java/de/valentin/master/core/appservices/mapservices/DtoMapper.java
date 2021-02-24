package de.valentin.master.core.appservices.mapservices;

import java.util.List;

import de.valentin.master.core.appservices.dto.ProjectData;
import de.valentin.master.core.appservices.dto.TaskData;
import de.valentin.master.core.appservices.dto.UserData;
import de.valentin.master.core.project.ProjectAggregate;
import de.valentin.master.core.task.TaskAggregate;
import de.valentin.master.core.user.UserAggregate;

public interface DtoMapper {
	public List<ProjectData> convertAggregateToDto(List<ProjectAggregate> aggregates);
	public List<ProjectAggregate> convertDtoToAggregate(List<ProjectData> dtos);
	public ProjectData convertAggregateToDto(ProjectAggregate aggregate);
	public ProjectAggregate convertDtoToAggregate(ProjectData dto);
	
	public List<TaskData> convertTodoAggregatesToTodoDtos(List<TaskAggregate> aggregates);
	public List<TaskAggregate> converTodoDtosToTodoAggregates(List<TaskData> dtos);
	public TaskData convertTodoAggregateToTodoDto(TaskAggregate aggregate);
	public TaskAggregate convertTodoDtoToTodoAggregate(TaskData dto);
	
	public List<UserData> convertUserAggregatesToUserDtos(List<UserAggregate> aggregates);
	public List<UserAggregate> convertUserDtosToUserAggregates(List<UserData> dtos);
	public UserData convertUserAggregateToUserDto(UserAggregate aggregate);
	public UserAggregate convertUserDtoToUserAggregate(UserData dto);
}
