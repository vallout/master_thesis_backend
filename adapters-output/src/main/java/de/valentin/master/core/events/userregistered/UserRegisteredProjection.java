package de.valentin.master.core.events.userregistered;

import org.bson.types.ObjectId;

public class UserRegisteredProjection {
	
	private ObjectId userId;
	private long timestamp;

	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}	
}
