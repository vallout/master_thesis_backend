package de.valentin.master.core.temporarypoints;

public enum Events {
	VIDEOWATCHED("VideoWatched"),
	SPECIALREGISTRATION("SpecialRegistration"),
	STAYEDONPAGE("StayedOnPage");
	
	private String eventName;
	
	Events(String eventName) {
		this.eventName = eventName;
	}
	
	public String getEventName() {
		return eventName;
	}
}
