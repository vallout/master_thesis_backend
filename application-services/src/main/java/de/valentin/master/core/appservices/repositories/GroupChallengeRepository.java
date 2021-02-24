package de.valentin.master.core.appservices.repositories;

import java.util.Date;
import java.util.List;

import de.valentin.master.core.groupchallenge.GroupChallengeAggregate;

public interface GroupChallengeRepository {
	
	void save(GroupChallengeAggregate aggregate);
	
	List<GroupChallengeAggregate> retrieve(boolean isRunning, Date end);
	List<GroupChallengeAggregate> retrieve(boolean isRunning);
	GroupChallengeAggregate retrieve(String challengeId);
	List<GroupChallengeAggregate> retrieve();
}
