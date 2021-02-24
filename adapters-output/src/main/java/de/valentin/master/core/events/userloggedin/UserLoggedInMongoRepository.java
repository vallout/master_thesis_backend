package de.valentin.master.core.events.userloggedin;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.events.dto.UserLoggedInData;
import de.valentin.master.core.appservices.events.repositories.UserLoggedInRepository;
import de.valentin.master.core.appservices.internalevents.UserLoggedIn;
import de.valentin.master.core.shared_model.UserId;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserLoggedInMongoRepository implements UserLoggedInRepository {
	
	UserLoggedInDAO dao;
	
	@Autowired
	public UserLoggedInMongoRepository(UserLoggedInDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void save(UserLoggedIn userLoggedIn) {
		UserLoggedInProjection projection = new UserLoggedInProjection();
		projection.setUserId(new ObjectId(userLoggedIn.getUserId().toString()));
		projection.setTimestamp(userLoggedIn.getTimestamp());
		dao.save(projection);
	}

	@Override
	public List<UserLoggedInData> retrieve(UserId userId) {
		List<UserLoggedInProjection> projections = dao.findByUserId(new ObjectId(userId.toString()));
		return retrieveGeneral(projections);
	}

	@Override
	public List<UserLoggedInData> retrieve(UserId userId, long begin, long end) {
		List<UserLoggedInProjection> projections = dao.findByUserIdAndTimestampBetween(new ObjectId(userId.toString()), begin, end);
		return retrieveGeneral(projections);
	}

	@Override
	public List<UserLoggedInData> retrieve(long begin, long end) {
		List<UserLoggedInProjection> projections = dao.findByTimestampBetween(begin, end);
		return retrieveGeneral(projections);
	}

	@Override
	public List<UserLoggedInData> retrieve() {
		List<UserLoggedInProjection> projections = dao.findAll();
		return retrieveGeneral(projections);
	}
	
	private List<UserLoggedInData> retrieveGeneral(List<UserLoggedInProjection> projections) {
		List<UserLoggedInData> usersLoggedIn = new ArrayList<UserLoggedInData>();
		
		for (UserLoggedInProjection projection : projections) {
			UserLoggedInData userLoggedIn = new UserLoggedInData(
											projection.getUserId().toString(), 
											projection.getTimestamp());
			usersLoggedIn.add(userLoggedIn);
		}
		
		return usersLoggedIn;
	}
}
