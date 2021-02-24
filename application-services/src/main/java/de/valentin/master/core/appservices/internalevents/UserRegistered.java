package de.valentin.master.core.appservices.internalevents;

import org.springframework.context.ApplicationEvent;

import de.valentin.master.core.shared_model.UserId;

@SuppressWarnings("serial")
public class UserRegistered extends ApplicationEvent {
	
	private UserId userId;
	private String ipAddress;

	public UserRegistered(Object source, UserId userId, String ipAddress) {
		super(source);

		this.userId = userId;
		this.ipAddress = ipAddress;
	}

	public UserId getUserId() {
		return userId;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
}
