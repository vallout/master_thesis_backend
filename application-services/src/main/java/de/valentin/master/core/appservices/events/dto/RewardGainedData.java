package de.valentin.master.core.appservices.events.dto;

public class RewardGainedData {
	
	private String challengeId;
	private String userId;
	private long timestamp;
	
	public RewardGainedData(String challengeId, String userId, long timestamp) {
		super();
		this.challengeId = challengeId;
		this.userId = userId;
		this.timestamp = timestamp;
	}
	
	public String getUserEventId() {
		return challengeId;
	}
	public void setUserEventId(String challengeId) {
		this.challengeId = challengeId;
	}
	public String getUserId() {
		return userId;
	}
	public void serUserId(String userId) {
		this.userId = userId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
