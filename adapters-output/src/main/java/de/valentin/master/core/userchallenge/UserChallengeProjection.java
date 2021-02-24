package de.valentin.master.core.userchallenge;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class UserChallengeProjection {
	
	@Id
	private ObjectId userChallengeId;
	
	private String event;
	private String queryKeyword;
	private Date beginning;
	private Date end;
	private int occurencesAsCondition;
	private int rewardPoints;
	private ObjectId rewardItem;
	
	private boolean active;

	public void setUserChallengeId(ObjectId userChallengeId) {
		this.userChallengeId = userChallengeId;
	}
	
	public ObjectId getUserChallengeId() {
		return userChallengeId;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getQueryKeyword() {
		return queryKeyword;
	}
	public void setQueryKeyword(String queryKeyword) {
		this.queryKeyword = queryKeyword;
	}
	public Date getBeginning() {
		return beginning;
	}
	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public int getOccurencesAsCondition() {
		return occurencesAsCondition;
	}
	public void setOccurencesAsCondition(int occurencesAsCondition) {
		this.occurencesAsCondition = occurencesAsCondition;
	}
	public int getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public ObjectId getRewardItem() {
		return rewardItem;
	}
	public void setRewardItem(ObjectId rewardItem) {
		this.rewardItem = rewardItem;
	}
}
