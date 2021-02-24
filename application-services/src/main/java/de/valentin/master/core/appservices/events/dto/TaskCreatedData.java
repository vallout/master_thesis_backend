package de.valentin.master.core.appservices.events.dto;

public class TaskCreatedData {
	
	private String projectId;
	private String userId;
	private long timestamp;
	
	public TaskCreatedData(String projectId, String userId, long timestamp) {
		this.projectId = projectId;
		this.userId = userId;
		this.timestamp = timestamp;
	}
	public String getProjectId() {
		return projectId;
	}
	public String getUserId() {
		return userId;
	}
	public long getTimestamp() {
		return timestamp;
	}
}
