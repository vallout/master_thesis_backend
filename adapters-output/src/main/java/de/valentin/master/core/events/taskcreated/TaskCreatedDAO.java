package de.valentin.master.core.events.taskcreated;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskCreatedDAO extends MongoRepository<TaskCreatedProjection, ObjectId> {

	List<TaskCreatedProjection> findByProjectId(ObjectId projectId);
	List<TaskCreatedProjection> findByUserId(ObjectId userId);
	List<TaskCreatedProjection> findByUserIdAndTimestampBetween(ObjectId userId, long from, long to);
	List<TaskCreatedProjection> findByTimestampBetween(long from, long to);
	List<TaskCreatedProjection> findByUserIdAndProjectId(ObjectId userId, ObjectId projectId);
	List<TaskCreatedProjection> findByUserIdAndProjectIdAndTimestampBetween(ObjectId userId, ObjectId projectId, long from, long to);
	
}
