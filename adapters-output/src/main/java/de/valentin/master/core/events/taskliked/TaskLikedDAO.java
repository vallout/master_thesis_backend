package de.valentin.master.core.events.taskliked;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskLikedDAO extends MongoRepository<TaskLikedProjection, ObjectId>   {
	
	List<TaskLikedProjection> findByProjectId(ObjectId projectId);
	List<TaskLikedProjection> findByCreatorId(ObjectId creatorId);
	List<TaskLikedProjection> findByCreatorIdAndTimestampBetween(ObjectId creatorId, long from, long to);
	List<TaskLikedProjection> findByTimestampBetween(long from, long to);
	List<TaskLikedProjection> findByCreatorIdAndProjectId(ObjectId creatorId, ObjectId projectId);
	List<TaskLikedProjection> findByCreatorIdAndProjectIdAndTimestampBetween(ObjectId creatorId, ObjectId projectId, long from, long to);
	List<TaskLikedProjection> findByLikerId(ObjectId likerId);
	List<TaskLikedProjection> findByLikerIdAndTimestampBetween(ObjectId likerId, long from, long to);
	List<TaskLikedProjection> findByLikerIdAndProjectId(ObjectId likerId, ObjectId projectId);
	List<TaskLikedProjection> findByLikerIdAndProjectIdAndTimestampBetween(ObjectId likerId, ObjectId projectId, long from, long to);
}
