package de.valentin.master.core.events.taskfinished;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskFinishedDAO extends MongoRepository<TaskFinishedProjection, ObjectId>  {

	List<TaskFinishedProjection> findByProjectId(ObjectId projectId);
	List<TaskFinishedProjection> findByUserId(ObjectId userId);
	List<TaskFinishedProjection> findByUserIdAndTimestampBetween(ObjectId userId, long from, long to);
	List<TaskFinishedProjection> findByTimestampBetween(long from, long to);
	List<TaskFinishedProjection> findByUserIdAndProjectId(ObjectId userId, ObjectId projectId);
	List<TaskFinishedProjection> findByUserIdAndProjectIdAndTimestampBetween(ObjectId userId, ObjectId projectId, long from, long to);
	
}
