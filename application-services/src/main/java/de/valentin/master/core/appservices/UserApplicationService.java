package de.valentin.master.core.appservices;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import de.valentin.master.core.appservices.dto.UserData;
import de.valentin.master.core.appservices.internalevents.ProfileChanged;
import de.valentin.master.core.appservices.internalevents.UserLoggedIn;
import de.valentin.master.core.appservices.internalevents.UserRegistered;
import de.valentin.master.core.appservices.mapservices.DtoMapper;
import de.valentin.master.core.appservices.repositories.ProjectRepository;
import de.valentin.master.core.appservices.repositories.UserRepository;
import de.valentin.master.core.project.ProjectAggregate;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.user.UserAggregate;

@Service
public class UserApplicationService {
	
	private UserRepository userProfileRepository;
	private ProjectRepository projectRepository;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	private DtoMapper dtoMapper;
	
	@Autowired
	public UserApplicationService(UserRepository userProfileRepository,
									ApplicationEventPublisher applicationEventPublisher,
									ProjectRepository projectRepository,
									DtoMapper dtoMapper) {
		this.userProfileRepository = userProfileRepository;
		this.projectRepository = projectRepository;
		this.applicationEventPublisher = applicationEventPublisher;
		
		this.dtoMapper = dtoMapper;
	}
	
	public List<UserData> getAllUsers() {
		List<UserAggregate> aggregates = userProfileRepository.retrieve(); 
		return dtoMapper.convertUserAggregatesToUserDtos(aggregates);
	}
	
	public UserAggregate loginUser(UserId userId) {
		UserAggregate aggregate = userProfileRepository.retrieve(userId);
		applicationEventPublisher.publishEvent(new UserLoggedIn(this, userId));
		return aggregate;
	}
	
	public UserData getUserById(UserId userId) {
		UserAggregate aggregate = userProfileRepository.retrieve(userId);
		return dtoMapper.convertUserAggregateToUserDto(aggregate);
	}
	
	public UserData getUserBy(String primaryMail) {
		UserAggregate aggregate = userProfileRepository.retrieve(primaryMail);
		if (aggregate != null ) {
			return dtoMapper.convertUserAggregateToUserDto(aggregate);
		} else {
			return null;
		}
	}
	
	public UserData loginUser(String primaryMail) {
		UserData profile = getUserBy(primaryMail);
		if (profile != null) {
			applicationEventPublisher.publishEvent(
					new UserLoggedIn(this, 
							new UserId(profile.getUserId())));
		}
		return profile;
	}

	public UserData registerUser(UserData userData, String ipAddress) {
		if (userData.getPrimaryMail() == "") {
			throw new IllegalArgumentException("primary mail is needed for registration");
		}
		UserId userId = new UserId(new ObjectId().toString());
		UserAggregate profile = null;
		try {
			profile = setFirstProfile(userId, userData);
		} catch(Throwable t) {
			t.printStackTrace();
		}
		applicationEventPublisher.publishEvent(new UserRegistered(this, userId, ipAddress));
		UserData profileDTO = dtoMapper.convertUserAggregateToUserDto(profile);
		return profileDTO;
	}
	
	public UserData changeProfile(UserId userId, UserData userData) {
		try {
			UserAggregate user = setProfile(userId, userData);
			int profileProgress = user.getProfile().calculateProfileCompletion();
			applicationEventPublisher.publishEvent(new ProfileChanged(this, userId, profileProgress));
			return dtoMapper.convertUserAggregateToUserDto(user);
		} catch(Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	public List<UserData> getAllUsersOf(ProjectId projectId) {
		ProjectAggregate projectAggregate = projectRepository.retrieve(projectId);
		List<UserId> userIds = projectAggregate.getProjectRootEntity().getMembers();
		List<UserAggregate> users = new ArrayList<>();
		for (UserId userId : userIds) {
			users.add(userProfileRepository.retrieve(userId));
		}
		return dtoMapper.convertUserAggregatesToUserDtos(users);
	}
	
	public int getProfileProgress(UserId userId) {
		UserAggregate user = userProfileRepository.retrieve(userId);
		return user.getProfile().calculateProfileCompletion();
	}
	
	private UserAggregate setFirstProfile(UserId userId, UserData userData) {
		UserAggregate profile = new UserAggregate.UserProfileBuilder(userId.toString())
											.title(userData.getTitle())
											.firstname(userData.getFirstname())
											.lastname(userData.getLastname())
											.primaryMail(userData.getPrimaryMail())
											.phone(userData.getPhone())
											.build();
		userProfileRepository.save(profile);
		return profile;
	}
	
	private UserAggregate setProfile(UserId userId, UserData userData) {
		UserAggregate profile = userProfileRepository.retrieve(userId);
		if (profile == null) profile = new UserAggregate.UserProfileBuilder(userId.toString()).build();
		profile.getProfile().setFirstname(userData.getFirstname());
		profile.getProfile().setLastname(userData.getLastname());
		profile.getProfile().setPrimaryMail(userData.getPrimaryMail());
		profile.getProfile().setPhone(userData.getPhone());
		profile.getProfile().setTitle(userData.getTitle());
		profile.getProfile().setPictureId(userData.getPictureId());
		userProfileRepository.save(profile);
		return profile;
	}
}
