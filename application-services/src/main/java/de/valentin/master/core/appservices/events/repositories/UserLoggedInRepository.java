package de.valentin.master.core.appservices.events.repositories;

import java.util.List;

import de.valentin.master.core.appservices.events.dto.UserLoggedInData;
import de.valentin.master.core.appservices.internalevents.UserLoggedIn;
import de.valentin.master.core.shared_model.UserId;

public interface UserLoggedInRepository {
	void save(UserLoggedIn userLoggedIn);
	
	List<UserLoggedInData> retrieve(UserId userId);
	List<UserLoggedInData> retrieve(UserId userId, long begin, long end);
	List<UserLoggedInData> retrieve(long begin, long end);
	List<UserLoggedInData> retrieve();
}
