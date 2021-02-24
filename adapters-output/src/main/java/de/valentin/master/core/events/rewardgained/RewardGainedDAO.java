package de.valentin.master.core.events.rewardgained;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RewardGainedDAO extends MongoRepository<RewardGainedProjection, ObjectId>{
	
	List<RewardGainedProjection> findByTimestampBetween(long from, long to);
	List<RewardGainedProjection> findByChallengeId(ObjectId challengeId);
}
