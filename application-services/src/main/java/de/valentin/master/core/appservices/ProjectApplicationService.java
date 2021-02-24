package de.valentin.master.core.appservices;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import de.valentin.master.core.appservices.dto.ProjectData;
import de.valentin.master.core.appservices.dto.UserData;
import de.valentin.master.core.appservices.internalevents.ProjectCreated;
import de.valentin.master.core.appservices.internalevents.ProjectJoined;
import de.valentin.master.core.appservices.mapservices.DtoMapper;
import de.valentin.master.core.appservices.repositories.ProjectRepository;
import de.valentin.master.core.appservices.repositories.UserRepository;
import de.valentin.master.core.project.ProjectAggregate;
import de.valentin.master.core.project.ProjectAggregate.ProjectBuilder;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.user.UserAggregate;

@Service
public class ProjectApplicationService {
	
	private ProjectRepository projectRepository;
	private UserRepository userProfileRepository;
	
	private ApplicationEventPublisher applicationEventPublisher;
	private DtoMapper dtoMapper;
	
	@Autowired
	public ProjectApplicationService(ProjectRepository projectRepository, 
									ApplicationEventPublisher applicationEventPublisher,
									UserRepository userProfileRepository,
									DtoMapper dtoMapper) {
		this.projectRepository = projectRepository;
		this.userProfileRepository = userProfileRepository;
		this.applicationEventPublisher = applicationEventPublisher;
		this.dtoMapper = dtoMapper;
	}
	
	public ProjectData getProjectBy(ProjectId projectId) {
		ProjectAggregate aggregate = projectRepository.retrieve(projectId);
		ProjectData dto = dtoMapper.convertAggregateToDto(aggregate);
		return dto;
	}
	
	public List<ProjectData> getAllProjects() {
		List<ProjectAggregate> aggregates = projectRepository.retrieve();
		List<ProjectData> dtos = dtoMapper.convertAggregateToDto(aggregates);
		return dtos;
	}
	
	public List<ProjectData> getAllProjectsOf(UserId userId) {
		UserAggregate userAggregate = userProfileRepository.retrieve(userId);
		List<ProjectId> projects = userAggregate.getProfile().getProjects();
		List<ProjectAggregate> projectAggregates = new ArrayList<>();
		for (ProjectId projectId: projects) {
			projectAggregates.add(projectRepository.retrieve(projectId));
		}
		List<ProjectData> dtos = dtoMapper.convertAggregateToDto(projectAggregates);
		return dtos;
	}

	public ProjectData createProject(ProjectData projectData, UserId userId) {
		ProjectId projectId = new ProjectId(new ObjectId().toString());
		ProjectData dto = null;
		if (checkProjectRestriction(userId, 1)) {
			return null;
		}
		try {
			dto = setFirstProject(projectId, projectData, userId);
		} catch(Throwable t) {
			// TODO
			t.printStackTrace();
		}
		try {
			addProjectToUser(projectId, userId);
		} catch(Throwable t) {
			// TODO
			t.printStackTrace();
		}
		applicationEventPublisher.publishEvent(new ProjectCreated(this, userId, projectId));
		return dto;
	}
	
	public ProjectData changeProject(ProjectData projectData, ProjectId projectId) {
		ProjectData dto = null;
		try {
			dto = setProject(projectId, projectData);
		} catch(Throwable t) {
			// TODO
			t.printStackTrace();
		}
		return dto;
	}
	
	public ProjectData joinProject(ProjectId projectId, UserId userId) {
		ProjectData dto = null;
		if (checkProjectRestriction(userId, 1)) {
			return null;
		}
		try {
			dto = addUserToProject(projectId, userId);
		} catch(Throwable t) {
			// TODO
			t.printStackTrace();
		}
		try {
			addProjectToUser(projectId, userId);
		} catch(Throwable t) {
			t.printStackTrace();
		}
		applicationEventPublisher.publishEvent(new ProjectJoined(this, userId, projectId));
		return dto;
	}
	
	public void deleteProject(ProjectId projectId) {
		ProjectAggregate projectAggregate = projectRepository.retrieve(projectId);
		List<UserId> userIds = projectAggregate.getProjectRootEntity().getMembers();
		try {
			deleteProjectsAtUsers(projectId, userIds);
		} catch(Throwable t) {
			// TODO
			t.printStackTrace();
		}
		try {
			projectRepository.delete(projectId);
		} catch(Throwable t) {
			// TODO
			t.printStackTrace();
		}
	}
	
	public List<UserData> getAllMembers(ProjectId projectId) {
		ProjectData project = getProjectBy(projectId);
		List<String> userIds = project.getUsers();
		List<UserData> users = new ArrayList<>();
		for (String userId : userIds) {
			UserAggregate userAggregate = userProfileRepository.retrieve(new UserId(userId));
			UserData user = dtoMapper.convertUserAggregateToUserDto(userAggregate);
			users.add(user);
		}
		return users;
	}
	
	private void deleteProjectsAtUsers(ProjectId projectId, List<UserId> userIds) {
		for (UserId userId : userIds) {
			UserAggregate userAggregate = userProfileRepository.retrieve(userId);
			userAggregate.getProfile().removeProject(projectId);
			userProfileRepository.save(userAggregate);
		}
	}
	
	private ProjectData setFirstProject(ProjectId projectId, ProjectData projectData, UserId userId) {
		ProjectBuilder builder = new ProjectAggregate.ProjectBuilder(projectId)
										.name(projectData.getName())
										.description(projectData.getDescription());
		builder.addMember(userId.toString());
		ProjectAggregate aggregate = builder.build();
		projectRepository.save(aggregate);
		ProjectData dto = dtoMapper.convertAggregateToDto(aggregate);
		return dto;
	}
	
	private ProjectData setProject(ProjectId projectId, ProjectData projectData) {
		ProjectAggregate aggregate = projectRepository.retrieve(projectId);
		aggregate.getProjectRootEntity().setInformation(projectData.getName(), 
											projectData.getDescription());
		projectRepository.save(aggregate);
		ProjectData dto = dtoMapper.convertAggregateToDto(aggregate);
		return dto;
	}
	
	private void addProjectToUser(ProjectId projectId, UserId userId) {
		UserAggregate aggregate = userProfileRepository.retrieve(userId);
		aggregate.getProfile().addProject(projectId);
		userProfileRepository.save(aggregate);
	}
	
	private ProjectData addUserToProject(ProjectId projectId, UserId userId) {
		ProjectAggregate aggregate = projectRepository.retrieve(projectId);
		aggregate.getProjectRootEntity().addMember(userId);
		projectRepository.save(aggregate);
		ProjectData dto = dtoMapper.convertAggregateToDto(aggregate);
		return dto;
	}
	
	private boolean checkProjectRestriction(UserId userId, int restriction) {
		UserAggregate user = userProfileRepository.retrieve(userId);
		if (user.getProfile().getProjects().size() > restriction) {
			return true;
		} else {
			return false;
		}
	}
}
