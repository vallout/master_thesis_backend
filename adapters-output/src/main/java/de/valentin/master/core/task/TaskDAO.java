package de.valentin.master.core.task;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskDAO extends MongoRepository<TaskProjection, ObjectId>{
	List<TaskProjection> findByUserId(ObjectId userId);
	List<TaskProjection> findByProjectId(ObjectId projectId);
}
