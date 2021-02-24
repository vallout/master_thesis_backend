package de.valentin.master.core.temporarypoints;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TemporaryPointsAggregate {
	
	private TemporaryPointsRootEntity rootEntity;
	
	private TemporaryPointsAggregate(TemporaryPointsBuilder builder) {
		this.rootEntity = new TemporaryPointsRootEntity(builder.ipAddress, 
							builder.creationDate, builder.points, builder.triggeredEvents,
							builder.version);
	}
	
	public TemporaryPointsRootEntity getRootEntity() {
		return this.rootEntity;
	}
	
	public static class TemporaryPointsBuilder {
		private String ipAddress;
		private Date creationDate;
		private int points;
		private List<String> triggeredEvents;
		private Long version;
		
		public TemporaryPointsBuilder(String ipAddress, Date creationDate) {
			this.ipAddress = ipAddress;
			this.creationDate = creationDate;
			this.triggeredEvents = new ArrayList<>();
		}
		
		public TemporaryPointsBuilder setVersion(Long version) {
			this.version = version;
			return this;
		}
		
		public TemporaryPointsBuilder setPoints(int points) {
			this.points = points;
			return this;
		}
		
		public TemporaryPointsBuilder setEvents(List<String> triggeredEvents) {
			this.triggeredEvents = triggeredEvents;
			return this;
		}
		
		public TemporaryPointsAggregate build() {
			return new TemporaryPointsAggregate(this);
		}
	}
} 
