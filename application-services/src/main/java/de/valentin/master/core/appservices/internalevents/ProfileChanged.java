package de.valentin.master.core.appservices.internalevents;

import org.springframework.context.ApplicationEvent;

import de.valentin.master.core.shared_model.UserId;

@SuppressWarnings("serial")
public class ProfileChanged extends ApplicationEvent  {

	private UserId userId;
	private int progress;
	
	public ProfileChanged(Object source, UserId userId, int progress) {
		super(source);
		this.userId = userId;
		this.progress = progress;
	}

	public UserId getUserId() {
		return userId;
	}

	public int getProgress() {
		return progress;
	}
}
