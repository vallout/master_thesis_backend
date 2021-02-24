package de.valentin.master.core.temppoints;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import de.valentin.master.core.appservices.repositories.TemporaryPointsRepository;
import de.valentin.master.core.temporarypoints.TemporaryPointsAggregate;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TemporaryPointsDbRepository implements TemporaryPointsRepository {
	
	private TemporaryPointsDAO dao;
	
	@Autowired
	public TemporaryPointsDbRepository(TemporaryPointsDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void save(TemporaryPointsAggregate aggregate) {
		TemporaryPointsProjection projection = new TemporaryPointsProjection();
		projection.setIpAddress(aggregate.getRootEntity().getIpAddress());
		projection.setCreationDate(aggregate.getRootEntity().getCreationDate());
		projection.setPoints(aggregate.getRootEntity().getPoints());
		projection.setTriggeredEvents(aggregate.getRootEntity().getTriggeredEvents());
		projection.setVersion(aggregate.getRootEntity().getVersion());
		dao.save(projection);
	}

	@Override
	public TemporaryPointsAggregate retrieve(String ipAddress) {
		Optional<TemporaryPointsProjection> optionalProjection = dao.findById(ipAddress);
		if (optionalProjection.isPresent()) {
			TemporaryPointsProjection projection = optionalProjection.get();
			TemporaryPointsAggregate aggregate = new TemporaryPointsAggregate.TemporaryPointsBuilder(
													ipAddress, projection.getCreationDate())
													.setVersion(projection.getVersion())
													.setPoints(projection.getPoints())
													.setEvents(projection.getTriggeredEvents())
													.build();
			return aggregate;
		} else {
			TemporaryPointsAggregate aggregate = new TemporaryPointsAggregate.TemporaryPointsBuilder(
													ipAddress, new Date())
													.build();
			return aggregate;
		}
	}

	@Override
	public List<TemporaryPointsAggregate> retrieve() {
		List<TemporaryPointsProjection> projections = dao.findAll();
		List<TemporaryPointsAggregate> aggregates = new ArrayList<>();
		for (TemporaryPointsProjection projection : projections) {
			TemporaryPointsAggregate aggregate = new TemporaryPointsAggregate.TemporaryPointsBuilder(
					projection.getIpAddress(), projection.getCreationDate())
					.setVersion(projection.getVersion())
					.setPoints(projection.getPoints())
					.setEvents(projection.getTriggeredEvents())
					.build();
			aggregates.add(aggregate);
		}
		return aggregates;
	}

	@Override
	public void delete(String ipAddress) {
		dao.deleteById(ipAddress);
	}

}
