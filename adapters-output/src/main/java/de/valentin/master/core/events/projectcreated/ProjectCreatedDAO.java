package de.valentin.master.core.events.projectcreated;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectCreatedDAO extends MongoRepository<ProjectCreatedProjection, ObjectId>{
	
	List<ProjectCreatedProjection> findByUserId(ObjectId userId);
	List<ProjectCreatedProjection> findByUserIdAndTimestampBetween(ObjectId userId, long from, long to);
	List<ProjectCreatedProjection> findByTimestampBetween(long from, long to);
}
