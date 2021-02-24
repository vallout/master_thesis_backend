package de.valentin.master.core.groupchallenge;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupChallengeDAO extends MongoRepository<GroupChallengeProjection, String>{
	
	List<GroupChallengeProjection> findByIsRunningIsTrueAndEndLessThan(Date end);
	List<GroupChallengeProjection> findByIsRunningIsTrue();
}
