package de.valentin.master.core.events.userloggedin;

import org.bson.types.ObjectId;

public class UserLoggedInProjection {
	
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
