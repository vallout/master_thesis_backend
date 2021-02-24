package de.valentin.master.core.events.projectcreated;

import org.bson.types.ObjectId;

public class ProjectCreatedProjection {
	
	private ObjectId userId;
	private ObjectId projectId;
	private long timestamp;

	
	public ObjectId getUserId() {
		return userId;
	}
	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}
	public ObjectId getProjectId() {
		return projectId;
	}
	public void setProjectId(ObjectId projectId) {
		this.projectId = projectId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
