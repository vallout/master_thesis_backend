package de.valentin.master.core.appservices.repositories;

import java.util.List;

import de.valentin.master.core.userchallenge.UserChallengeAggregate;

public interface UserChallengeRepository {
	void save(UserChallengeAggregate aggregate);
	
	List<UserChallengeAggregate> retrieveActiveUserEvents(String eventName);
	UserChallengeAggregate retrieve(String eventId);
	List<UserChallengeAggregate> retrieve();
	
	void delete(String challengeId);
}
