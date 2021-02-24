package de.valentin.master.core.appservices.events.dto;

public class UserLoggedInData {
	
	private String userId;
	private long timestamp;
	
	public UserLoggedInData(String userId, long timestamp) {
		this.userId = userId;
		this.timestamp = timestamp;
	}

	public String getUserId() {
		return userId;
	}
	public long getTimestamp() {
		return timestamp;
	}
}
