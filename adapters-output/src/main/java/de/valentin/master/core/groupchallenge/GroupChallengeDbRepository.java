package de.valentin.master.core.groupchallenge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.repositories.GroupChallengeRepository;
import de.valentin.master.core.groupchallenge.GroupChallengeAggregate;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class GroupChallengeDbRepository implements GroupChallengeRepository{
	
	private GroupChallengeDAO dao;
	
	@Autowired
	public GroupChallengeDbRepository(GroupChallengeDAO dao) {
		this.dao = dao;
	}

	@Override
	public void save(GroupChallengeAggregate aggregate) {
		GroupChallengeProjection projection = convertAggregateToProjection(aggregate);
		dao.save(projection);
	}

	@Override
	public List<GroupChallengeAggregate> retrieve(boolean isRunning, Date end) {
		if (isRunning) {
			List<GroupChallengeProjection> projections = dao.findByIsRunningIsTrueAndEndLessThan(end);
			List<GroupChallengeAggregate> aggregates = new ArrayList<>();
			
			for (GroupChallengeProjection projection : projections) {
				GroupChallengeAggregate aggregate = convertProjectionToAggregate(projection);
				aggregates.add(aggregate);
			}
			return aggregates;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<GroupChallengeAggregate> retrieve() {
		List<GroupChallengeProjection> projections = dao.findAll();
		List<GroupChallengeAggregate> aggregates = new ArrayList<>();
		
		for (GroupChallengeProjection projection : projections) {
			GroupChallengeAggregate aggregate = convertProjectionToAggregate(projection);
			aggregates.add(aggregate);
		}
		return aggregates;
	}
	
	private GroupChallengeAggregate convertProjectionToAggregate(GroupChallengeProjection projection) {
		GroupChallengeAggregate aggregate = new GroupChallengeAggregate.GroupChallengeBuilder(
											projection.getGroupChallengeId().toString()
											).isRunning(projection.isRunning())
											.eventType(projection.getType())
											.eventName(projection.getEventName())
											.description(projection.getDescription())
											.beginningDate(projection.getBeginning())
											.endDate(projection.getEnd())
											.condition(projection.getCondition())
											.rewards(projection.getRewardPoints(), projection.getRewardItem())
											.isFinished(projection.isFinished())
											.build();
		return aggregate;
	}
	
	private GroupChallengeProjection convertAggregateToProjection(GroupChallengeAggregate aggregate) {
		GroupChallengeProjection projection= new GroupChallengeProjection();
		projection.setGroupChallengeId(new ObjectId(aggregate.getRootEntity().getGroupChallengeId()));
		projection.setRunning(aggregate.getRootEntity().isRunning());
		projection.setType(aggregate.getRootEntity().getType().toString());
		projection.setEventName(aggregate.getRootEntity().getEventName());
		projection.setDescription(aggregate.getRootEntity().getDescription());
		projection.setBeginning(aggregate.getRootEntity().getBeginning());
		projection.setEnd(aggregate.getRootEntity().getEnd());
		projection.setCondition(aggregate.getRootEntity().getCondition());
		projection.setRewardPoints(aggregate.getRootEntity().getRewardPoints());
		projection.setRewardItem(aggregate.getRootEntity().getRewardItem().toString());
		projection.setFinished(aggregate.getRootEntity().isFinished());
		
		return projection;
	}

	@Override
	public List<GroupChallengeAggregate> retrieve(boolean isRunning) {
		if (isRunning) {
			List<GroupChallengeProjection> projections = dao.findByIsRunningIsTrue();
			List<GroupChallengeAggregate> aggregates = new ArrayList<>();
			
			for (GroupChallengeProjection projection : projections) {
				GroupChallengeAggregate aggregate = convertProjectionToAggregate(projection);
				aggregates.add(aggregate);
			}
			return aggregates;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public GroupChallengeAggregate retrieve(String challengeId) {
		Optional<GroupChallengeProjection> challengeProjection_opt = dao.findById(challengeId);
		if (challengeProjection_opt.isPresent()) {
			GroupChallengeProjection projection = challengeProjection_opt.get();
			return convertProjectionToAggregate(projection);
		} else {
			return null;
		}
	}
}
