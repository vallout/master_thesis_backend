package de.valentin.master.core.events.rewardgained;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.events.dto.RewardGainedData;
import de.valentin.master.core.appservices.events.repositories.RewardGainedRepository;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RewardGainedMongoRepository implements RewardGainedRepository{
	
	private RewardGainedDAO dao;
	
	@Autowired
	public RewardGainedMongoRepository(RewardGainedDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public void save(RewardGainedData rewardGained) {
		RewardGainedProjection projection = new RewardGainedProjection();
		projection.setUserEventId(rewardGained.getUserEventId());
		projection.setUserId(rewardGained.getUserId());
		projection.setTimestamp(rewardGained.getTimestamp());
		
		dao.save(projection);
	}

	@Override
	public List<RewardGainedData> retrieve(long from, long to) {
		List<RewardGainedProjection> projections = dao.findByTimestampBetween(from, to);
		List<RewardGainedData> datas = new ArrayList<>();
		for (RewardGainedProjection projection : projections) {
			RewardGainedData data = new RewardGainedData(
					projection.getUserEventId().toString(), 
					projection.getUserId(), 
					projection.getTimestamp()
			);
			datas.add(data);
		}
		return datas;
	}

	@Override
	public List<RewardGainedData> retrieve() {
		List<RewardGainedProjection> projections = dao.findAll();
		List<RewardGainedData> datas = new ArrayList<>();
		for (RewardGainedProjection projection : projections) {
			RewardGainedData data = new RewardGainedData(
					projection.getUserEventId().toString(), 
					projection.getUserId(), 
					projection.getTimestamp()
			);
			datas.add(data);
		}
		return datas;
	}

	@Override
	public List<RewardGainedData> retrieve(String challengeId) {
		List<RewardGainedProjection> projections = dao.findByChallengeId(new ObjectId(challengeId));
		List<RewardGainedData> datas = new ArrayList<>();
		for (RewardGainedProjection projection : projections) {
			RewardGainedData data = new RewardGainedData(
					projection.getUserEventId().toString(), 
					projection.getUserId(), 
					projection.getTimestamp()
			);
			datas.add(data);
		}
		return datas;
	}

}
