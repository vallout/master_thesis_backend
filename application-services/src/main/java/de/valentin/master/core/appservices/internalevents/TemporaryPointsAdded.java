package de.valentin.master.core.appservices.internalevents;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class TemporaryPointsAdded extends ApplicationEvent {
	
	private String ipAddress;
	private int points;
	
	public TemporaryPointsAdded(Object source, String ipAddress, int points) {
		super(source);
		this.ipAddress = ipAddress;
		this.points = points;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getPoints() {
		return points;
	}
}
