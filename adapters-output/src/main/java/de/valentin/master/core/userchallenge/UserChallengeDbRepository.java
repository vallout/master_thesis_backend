package de.valentin.master.core.userchallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.repositories.UserChallengeRepository;
import de.valentin.master.core.shared_model.ItemId;
import de.valentin.master.core.userchallenge.UserChallengeAggregate;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserChallengeDbRepository implements UserChallengeRepository {
	
	UserChallengeDAO dao;
	
	@Autowired
	public UserChallengeDbRepository(UserChallengeDAO dao) {
		this.dao = dao;
	}
	@Override
	public void save(UserChallengeAggregate aggregate) {
		UserChallengeProjection projection = new UserChallengeProjection();
		projection.setUserChallengeId(new ObjectId(aggregate.getRootEntity().getUserChallengeId()));
		
		projection.setActive(aggregate.getRootEntity().isActivated());
		projection.setBeginning(aggregate.getRootEntity().getBeginning());
		projection.setEnd(aggregate.getRootEntity().getEnd());
		projection.setEvent(aggregate.getRootEntity().getEvent());
		projection.setOccurencesAsCondition(aggregate.getRootEntity().getCondition());
		projection.setQueryKeyword(aggregate.getRootEntity().getQueryKeyword());
		try {
			projection.setRewardItem(new ObjectId(aggregate.getRootEntity().getRewardItem().toString()));
		} catch (Exception e) {
			// do nothing
		}
		projection.setRewardPoints(aggregate.getRootEntity().getRewardPoints());
		
		dao.save(projection);
	}

	@Override
	public List<UserChallengeAggregate> retrieveActiveUserEvents(String eventName) {

		List<UserChallengeProjection> projections = dao.findByEventAndActiveIsTrue(eventName);
		List<UserChallengeAggregate> aggregates = new ArrayList<UserChallengeAggregate>();
		
		for (UserChallengeProjection projection : projections) {
			
			ItemId rewardItem = null;
			if (projection.getRewardItem() != null) {
				rewardItem = new ItemId(projection.getRewardItem().toString());
			}
			UserChallengeAggregate aggregate = new UserChallengeAggregate.UserChallengeBuilder(
											projection.getUserChallengeId().toString(), eventName
										).beginning(projection.getBeginning())
										.condition(projection.getOccurencesAsCondition())
										.end(projection.getEnd())
										.queryKeyword(projection.getQueryKeyword())
										.rewardItem(rewardItem)
										.rewardPoints(projection.getRewardPoints())
										.build();
			aggregates.add(aggregate);
		}
		
		return aggregates;
	}
	@Override
	public void delete(String userEventId) {
		dao.deleteById(new ObjectId(userEventId));
	}
	@Override
	public List<UserChallengeAggregate> retrieve() {
		List<UserChallengeProjection> projections =  dao.findAll();
		List<UserChallengeAggregate> aggregates = new ArrayList<>();
		
		for (UserChallengeProjection projection : projections) {
			
			ItemId rewardItem = null;
			if (projection.getRewardItem() != null) {
				rewardItem = new ItemId(projection.getRewardItem().toString());
			}
			UserChallengeAggregate aggregate = new UserChallengeAggregate.UserChallengeBuilder(
											projection.getUserChallengeId().toString(), projection.getEvent()
										).beginning(projection.getBeginning())
										.condition(projection.getOccurencesAsCondition())
										.end(projection.getEnd())
										.queryKeyword(projection.getQueryKeyword())
										.rewardItem(rewardItem)
										.rewardPoints(projection.getRewardPoints())
										.isActive(projection.isActive())
										.build();
			aggregates.add(aggregate);
		}
		
		return aggregates;
	}
	@Override
	public UserChallengeAggregate retrieve(String eventId) {
		Optional<UserChallengeProjection> projection_opt =  dao.findById(new ObjectId(eventId));

		if (projection_opt.isPresent()) {
			UserChallengeProjection projection = projection_opt.get();
			ItemId rewardItem = null;
			if (projection.getRewardItem() != null) {
				rewardItem = new ItemId(projection.getRewardItem().toString());
			}
			UserChallengeAggregate aggregate = new UserChallengeAggregate.UserChallengeBuilder(
					projection.getUserChallengeId().toString(), projection.getEvent()
				).beginning(projection.getBeginning())
				.condition(projection.getOccurencesAsCondition())
				.end(projection.getEnd())
				.queryKeyword(projection.getQueryKeyword())
				.rewardItem(rewardItem)
				.rewardPoints(projection.getRewardPoints())
				.isActive(projection.isActive())
				.build();
			return aggregate;
		} else {
			return null;
		}
	}

}
