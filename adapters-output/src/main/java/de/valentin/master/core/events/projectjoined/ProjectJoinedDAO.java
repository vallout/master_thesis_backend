package de.valentin.master.core.events.projectjoined;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectJoinedDAO extends MongoRepository<ProjectJoinedProjection, ObjectId> {
	
	List<ProjectJoinedProjection> findByUserId(ObjectId userId);
	List<ProjectJoinedProjection> findByUserIdAndTimestampBetween(ObjectId userId, long from, long to);
	List<ProjectJoinedProjection> findByTimestampBetween(long from, long to);
}
