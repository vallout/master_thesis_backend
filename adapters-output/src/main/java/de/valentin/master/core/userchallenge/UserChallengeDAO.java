package de.valentin.master.core.userchallenge;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserChallengeDAO extends MongoRepository<UserChallengeProjection, ObjectId>{
	
	// find all active events by name
	List<UserChallengeProjection> findByEventAndActiveIsTrue(String event);
}
