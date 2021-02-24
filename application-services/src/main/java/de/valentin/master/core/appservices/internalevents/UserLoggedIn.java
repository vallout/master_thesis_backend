package de.valentin.master.core.appservices.internalevents;

import org.springframework.context.ApplicationEvent;

import de.valentin.master.core.shared_model.UserId;

@SuppressWarnings("serial")
public class UserLoggedIn extends ApplicationEvent{

	private UserId userId;
	
	public UserLoggedIn(Object source, UserId userId) {
		super(source);
		this.userId = userId;
	}
	public UserId getUserId() {
		return userId;
	}
}
