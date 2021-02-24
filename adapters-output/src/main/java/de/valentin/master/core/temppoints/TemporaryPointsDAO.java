package de.valentin.master.core.temppoints;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemporaryPointsDAO extends MongoRepository<TemporaryPointsProjection, String>{
	
}
