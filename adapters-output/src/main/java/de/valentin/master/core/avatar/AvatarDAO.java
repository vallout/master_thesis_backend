package de.valentin.master.core.avatar;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AvatarDAO extends MongoRepository<AvatarProjection, ObjectId>{
	
	AvatarProjection findByUserId(ObjectId userId);
}
