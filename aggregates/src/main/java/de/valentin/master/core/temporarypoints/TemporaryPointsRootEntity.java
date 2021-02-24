package de.valentin.master.core.temporarypoints;

import java.util.Date;
import java.util.List;

public class TemporaryPointsRootEntity {
	private String ipAddress;
	private Date creationDate;
	private int points;
	private List<String> triggeredEvents;
	private Long version;
	
	public TemporaryPointsRootEntity(String ipAddress, Date creationDate, int points, List<String> triggeredEvents, Long version) {
		this.ipAddress = ipAddress;
		this.creationDate = creationDate;
		this.points = points;
		this.triggeredEvents = triggeredEvents;
		this.version = version;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getPoints() {
		return points;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public List<String> getTriggeredEvents() {
		return triggeredEvents;
	}
	
	public Long getVersion() {
		return version;
	}
	
	public int addPoints(String event) {
		int addedPoints = 0;
		switch(event) {
			case "VideoWatched":
				addedPoints = 30;
				if (!triggeredEvents.contains(Events.VIDEOWATCHED.getEventName())) {
					points = points + addedPoints;
					triggeredEvents.add(Events.VIDEOWATCHED.getEventName());
					return addedPoints;
				} else {
					return 0;
				}
			case "SpecialRegistration":
				addedPoints = 50;
				if (!triggeredEvents.contains(Events.SPECIALREGISTRATION.getEventName())) {
					points = points + addedPoints;
					triggeredEvents.add(Events.SPECIALREGISTRATION.getEventName());
					return addedPoints;
				} else {
					return 0;
				}
			case "StayedOnPage":
				addedPoints = 20;
				if (!triggeredEvents.contains(Events.STAYEDONPAGE.getEventName())) {
					points = points + addedPoints;
					triggeredEvents.add(Events.STAYEDONPAGE.getEventName());
					return addedPoints;
				} else {
					return 0;
				}
			default:
				return 0;
		}
	}
}
