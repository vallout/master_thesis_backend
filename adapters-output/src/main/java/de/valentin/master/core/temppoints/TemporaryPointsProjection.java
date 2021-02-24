package de.valentin.master.core.temppoints;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public class TemporaryPointsProjection {
	@Id
	private String ipAddress;
	private Date creationDate;
	private int points;
	private List<String> triggeredEvents;
	@Version
	private Long version;
	
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public List<String> getTriggeredEvents() {
		return triggeredEvents;
	}
	public void setTriggeredEvents(List<String> triggeredEvents) {
		this.triggeredEvents = triggeredEvents;
	}
}
