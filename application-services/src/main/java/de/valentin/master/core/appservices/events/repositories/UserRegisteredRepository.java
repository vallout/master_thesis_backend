package de.valentin.master.core.appservices.events.repositories;

import java.util.List;

import de.valentin.master.core.appservices.events.dto.UserRegisteredData;
import de.valentin.master.core.appservices.internalevents.UserRegistered;
import de.valentin.master.core.shared_model.UserId;

public interface UserRegisteredRepository {
	void save(UserRegistered userRegistered);
	
	List<UserRegisteredData> retrieve(UserId userId);
	List<UserRegisteredData> retrieve(UserId userId, long begin, long end);
	List<UserRegisteredData> retrieve(long begin, long end);
	List<UserRegisteredData> retrieve();
}
