package de.valentin.master.core.events.userregistered;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRegisteredDAO extends MongoRepository<UserRegisteredProjection, ObjectId>{
	
	List<UserRegisteredProjection> findByUserId(ObjectId userId);
	List<UserRegisteredProjection> findByUserIdAndTimestampBetween(ObjectId userId, long from, long to);
	List<UserRegisteredProjection> findByTimestampBetween(long from, long to);
}
