package de.valentin.master.core.appservices.events.dto;

public class UserRegisteredData {
	
	private String userId;
	private long timestamp;
	
	public UserRegisteredData(String userId, long timestamp) {
		super();
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
