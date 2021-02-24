package de.valentin.master.core.appservices.repositories;

import java.util.List;

import de.valentin.master.core.avatar.AvatarAggregate;
import de.valentin.master.core.shared_model.UserId;

public interface AvatarRepository {
	void save(AvatarAggregate aggregate);
	
	AvatarAggregate retrieve(UserId userId);
	List<AvatarAggregate> retrieve();
}
