package de.valentin.master.core.events.rewardgained;

import org.bson.types.ObjectId;

public class RewardGainedProjection {
	
	private ObjectId challengeId;
	private ObjectId userId;
	private long timestamp;
    private String type;
	
	public ObjectId getUserEventId() {
		return challengeId;
	}
	
	public void setUserEventId(String challengeId) {
		this.challengeId = new ObjectId(challengeId);
	}
	public void setUserId(String userId) {
		this.userId = new ObjectId(userId);
	}
	public String getUserId() {
		return userId.toString();
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
}
