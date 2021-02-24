package de.valentin.master.core.events.userregistered;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.events.dto.UserRegisteredData;
import de.valentin.master.core.appservices.events.repositories.UserRegisteredRepository;
import de.valentin.master.core.appservices.internalevents.UserRegistered;
import de.valentin.master.core.shared_model.UserId;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserRegisteredMongoRepository implements UserRegisteredRepository {
	
	UserRegisteredDAO dao;
	
	public UserRegisteredMongoRepository(UserRegisteredDAO dao) {
		this.dao = dao;
	}
	@Override
	public void save(UserRegistered userRegistered) {
		UserRegisteredProjection projection = new UserRegisteredProjection();
		projection.setTimestamp(userRegistered.getTimestamp());
		projection.setUserId(new ObjectId(userRegistered.getUserId().toString()));
		dao.save(projection);
	}

	@Override
	public List<UserRegisteredData> retrieve(UserId userId) {
		List<UserRegisteredProjection> projections = dao.findByUserId(new ObjectId(userId.toString()));
		return retrieveGeneral(projections);
	}

	@Override
	public List<UserRegisteredData> retrieve(UserId userId, long begin, long end) {
		List<UserRegisteredProjection> projections = dao.findByUserIdAndTimestampBetween(new ObjectId(userId.toString()), begin, end);
		return retrieveGeneral(projections);
	}

	@Override
	public List<UserRegisteredData> retrieve(long begin, long end) {
		List<UserRegisteredProjection> projections = dao.findByTimestampBetween(begin, end);
		return retrieveGeneral(projections);
	}

	@Override
	public List<UserRegisteredData> retrieve() {
		List<UserRegisteredProjection> projections = dao.findAll();
		return retrieveGeneral(projections);
	}
	
	private List<UserRegisteredData> retrieveGeneral(List<UserRegisteredProjection> projections) {
		List<UserRegisteredData> usersRegistered = new ArrayList<UserRegisteredData>();
		
		for (UserRegisteredProjection projection : projections) {
			UserRegisteredData userRegistered = new UserRegisteredData(
											projection.getUserId().toString(), 
											projection.getTimestamp());
			usersRegistered.add(userRegistered);
		}
		
		return usersRegistered;
	}
}
 