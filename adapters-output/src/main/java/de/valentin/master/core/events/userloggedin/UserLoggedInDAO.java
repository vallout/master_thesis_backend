package de.valentin.master.core.events.userloggedin;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserLoggedInDAO extends MongoRepository<UserLoggedInProjection, ObjectId>{
	
	List<UserLoggedInProjection> findByUserId(ObjectId userId);
	List<UserLoggedInProjection> findByUserIdAndTimestampBetween(ObjectId userId, long from, long to);
	List<UserLoggedInProjection> findByTimestampBetween(long from, long to);
}
