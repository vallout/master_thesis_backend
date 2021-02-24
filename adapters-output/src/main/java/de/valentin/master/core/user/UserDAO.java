package de.valentin.master.core.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDAO extends MongoRepository<UserProjection, String> {
	UserProjection findByPrimaryMail(String primaryMail);
}
