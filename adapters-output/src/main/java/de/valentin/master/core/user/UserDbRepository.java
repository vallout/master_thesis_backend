package de.valentin.master.core.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.repositories.UserRepository;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.user.UserAggregate;
import de.valentin.master.core.user.UserAggregate.UserProfileBuilder;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserDbRepository implements UserRepository{
	
	private UserDAO dao;
	
	@Autowired
	public UserDbRepository(UserDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void save(UserAggregate userProfileAggregate) {
		
		UserProjection projection = new UserProjection();
		projection.setUserId(new ObjectId(userProfileAggregate.getProfile().getUserId().toString()));
		projection.setTitle(userProfileAggregate.getProfile().getTitle());
		projection.setFirstname(userProfileAggregate.getProfile().getFirstname());
		projection.setLastname(userProfileAggregate.getProfile().getLastname());
		projection.setPrimaryMail(userProfileAggregate.getProfile().getPrimaryMail());
		projection.setPhone(userProfileAggregate.getProfile().getPhone());
		
		projection.setPictureId(userProfileAggregate.getProfile().getPictureId());
		
		for (ProjectId projectId : userProfileAggregate.getProfile().getProjects()) {
			projection.addProject(projectId.toString());
		}
		
		dao.save(projection);
	}

	@Override
	public UserAggregate retrieve(UserId userId) {
	
		Optional<UserProjection> projection_option = dao.findById(userId.toString());
		if (projection_option.isPresent()) {
			UserProjection projection = projection_option.get();
			UserProfileBuilder builder = new UserAggregate.UserProfileBuilder(userId.toString())
												.firstname(projection.getFirstname())
												.lastname(projection.getLastname())
												.title(projection.getTitle())
												.primaryMail(projection.getPrimaryMail())
												.phone(projection.getPhone())
												.pictureId(projection.getPictureId());
			for (String projectId : projection.getProjects()) {
				builder.addProject(projectId);
			}
			return builder.build();
		} else {
			return null;
		}
	}

	@Override
	public List<UserAggregate> retrieve() {

		List<UserProjection> projections = dao.findAll();
		List<UserAggregate> aggregates = new ArrayList<UserAggregate>();
		for (UserProjection projection : projections) {
			UserProfileBuilder builder = new UserAggregate.UserProfileBuilder(projection.getUserId().toString())
					.firstname(projection.getFirstname())
					.lastname(projection.getLastname())
					.title(projection.getTitle())
					.primaryMail(projection.getPrimaryMail())
					.phone(projection.getPhone())
					.pictureId(projection.getPictureId());
			for (String projectId : projection.getProjects()) {
				builder.addProject(projectId);
			}
			aggregates.add(builder.build());
		}
		return aggregates;
	}

	@Override
	public UserAggregate retrieve(String email) {
		UserProjection projection = dao.findByPrimaryMail(email);
		if (projection != null) {
			UserProfileBuilder builder = new UserAggregate.UserProfileBuilder(projection.getUserId().toString())
												.firstname(projection.getFirstname())
												.lastname(projection.getLastname())
												.title(projection.getTitle())
												.primaryMail(email)
												.phone(projection.getPhone())
												.pictureId(projection.getPictureId());
			for (String projectId : projection.getProjects()) {
				builder.addProject(projectId);
			}
			return builder.build();
		} else {
			return null;
		}
	}

}
