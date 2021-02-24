package de.valentin.master.core.appservices.events.dto;

public class TaskLikedData {
	
	private String projectId;
	private String creatorId;
	private String likerId;
	private long timestamp;
	
	public TaskLikedData(String projectId, String creatorId, String likerId, long timestamp) {
		this.projectId = projectId;
		this.creatorId = creatorId;
		this.likerId = likerId;
		this.timestamp = timestamp;
	}
	public String getProjectId() {
		return projectId;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public String getLikerId() {
		return likerId;
	}
	public long getTimestamp() {
		return timestamp;
	}
}
