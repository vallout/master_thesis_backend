package de.valentin.master.core.appservices.repositories;

import java.util.List;

import de.valentin.master.core.temporarypoints.TemporaryPointsAggregate;

public interface TemporaryPointsRepository {
	void save(TemporaryPointsAggregate aggregate);
	
	List<TemporaryPointsAggregate> retrieve();
	TemporaryPointsAggregate retrieve(String ipAddress);
	
	void delete(String ipAddress);
}
