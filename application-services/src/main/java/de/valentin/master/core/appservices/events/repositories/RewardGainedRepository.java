package de.valentin.master.core.appservices.events.repositories;

import java.util.List;

import de.valentin.master.core.appservices.events.dto.RewardGainedData;

public interface RewardGainedRepository {
	void save(RewardGainedData rewardGained);
	
	List<RewardGainedData> retrieve(long from, long to);
	List<RewardGainedData> retrieve(String challengeId);
	List<RewardGainedData> retrieve();
}
