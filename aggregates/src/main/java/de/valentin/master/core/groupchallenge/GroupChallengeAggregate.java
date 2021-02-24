package de.valentin.master.core.groupchallenge;

import java.util.Date;

import de.valentin.master.core.shared_model.ItemId;

public class GroupChallengeAggregate {
	
	private GroupChallengeRootEntity rootEntity;
	
	private GroupChallengeAggregate(GroupChallengeBuilder builder) {
		rootEntity = new GroupChallengeRootEntity(builder.groupChallengeId, builder.isRunning, builder.type, 
							builder.eventName,
							builder.description, builder.beginning, builder.end, builder.condition, 
							builder.rewardPoints, builder.rewardItem, builder.isFinished);
	}
	
	public GroupChallengeRootEntity getRootEntity() {
		return rootEntity;
	}
	
	public static class GroupChallengeBuilder {
		private String groupChallengeId;
		private boolean isRunning;
		private GroupChallengeType type;
		private String eventName;
		private String description;
		private Date beginning;
		private Date end;
		private int condition;
		private int rewardPoints;
		private ItemId rewardItem;
		private boolean isFinished;
		
		
		public GroupChallengeBuilder(String groupChallengeId) {
			this.groupChallengeId = groupChallengeId;
			this.isFinished = false;
		}
		
		public GroupChallengeBuilder isRunning(boolean isRunning) {
			this.isRunning = isRunning;
			return this;
		}
		
		public GroupChallengeBuilder eventType(String type) {
			this.type = GroupChallengeType.PLATFORM;
			return this;
		}
		
		public GroupChallengeBuilder eventName(String eventName) {
			this.eventName = eventName;
			return this;
		}
		
		public GroupChallengeBuilder beginningDate(Date beginning) {
			this.beginning = beginning;
			return this;
		}
		
		public GroupChallengeBuilder endDate(Date end) {
			this.end = end;
			return this;
		}
		
		public GroupChallengeBuilder condition(int condition) {
			this.condition = condition;
			return this;
		}
		
		public GroupChallengeBuilder rewards(int points, String itemId) {
			this.rewardPoints = points;
			this.rewardItem = new ItemId(itemId);
			return this;
		}
		
		public GroupChallengeBuilder description(String description) {
			this.description = description;
			return this;
		}
		
		public GroupChallengeBuilder isFinished(boolean isFinished) {
			this.isFinished = isFinished;
			return this;
		}
		
		public GroupChallengeAggregate build() {
			return new GroupChallengeAggregate(this);
		}
	}
}
