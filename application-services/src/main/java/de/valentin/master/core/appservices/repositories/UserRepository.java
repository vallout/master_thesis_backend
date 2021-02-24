package de.valentin.master.core.appservices.repositories;

import java.util.List;

import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.user.UserAggregate;

public interface UserRepository {
	void save(UserAggregate userProfileAggregate);
	
	UserAggregate retrieve(UserId userId);
	UserAggregate retrieve(String email);
	List<UserAggregate> retrieve();
}
