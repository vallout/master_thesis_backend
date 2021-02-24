package de.valentin.master.core.project;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectDAO extends MongoRepository<ProjectProjection, ObjectId>{

}
