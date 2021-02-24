package de.valentin.master.core.events.taskliked;

import org.bson.types.ObjectId;

public class TaskLikedProjection {
	
	private ObjectId projectId;
	private ObjectId creatorId;
	private ObjectId likerId;
	private long timestamp;
	
	public ObjectId getProjectId() {
		return projectId;
	}
	public void setProjectId(ObjectId projectId) {
		this.projectId = projectId;
	}
	public ObjectId getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(ObjectId creatorId) {
		this.creatorId = creatorId;
	}
	public ObjectId getLikerId() {
		return likerId;
	}
	public void setLikerId(ObjectId likerId) {
		this.likerId = likerId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
