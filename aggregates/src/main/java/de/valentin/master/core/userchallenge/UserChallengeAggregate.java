package de.valentin.master.core.userchallenge;

import java.util.Date;

import de.valentin.master.core.shared_model.ItemId;

public class UserChallengeAggregate {
	
	private UserChallengeRootEntity rootEntity;
	
	private UserChallengeAggregate(UserChallengeBuilder builder) {
		rootEntity = new UserChallengeRootEntity(builder.userEventId, builder.event, builder.queryKeyword, 
						builder.beginning, builder.end, builder.occurencesAsCondition, 
						builder.rewardPoints, builder.rewardItem, builder.isActive);
	}
	
	public UserChallengeRootEntity getRootEntity() {
		return this.rootEntity;
	}
	
	public static class UserChallengeBuilder {
		
		private String userEventId;
		private String event;
		private String queryKeyword;
		private Date beginning;
		private Date end;
		private int occurencesAsCondition;
		private int rewardPoints;
		private ItemId rewardItem;
		private boolean isActive;
		
		public UserChallengeBuilder(String userEventId, String event) {
			
			this.userEventId = userEventId;
			this.event = event;
			this.queryKeyword = "";
			this.beginning = null;
			this.end = null;
			this.occurencesAsCondition = 1000000;
			this.rewardPoints = 0;
			this.rewardItem = null;
		}
		
		public UserChallengeBuilder queryKeyword(String queryKeyword) {
			this.queryKeyword = queryKeyword;
			return this;
		}
		
		public UserChallengeBuilder beginning(Date beginning) {
			this.beginning = beginning;
			return this;
		}
		
		public UserChallengeBuilder end(Date end) {
			this.end = end;
			return this;
		}
		
		public UserChallengeBuilder condition(int condition) {
			this.occurencesAsCondition = condition;
			return this;
		}
		
		public UserChallengeBuilder rewardPoints(int points) {
			this.rewardPoints = points;
			return this;
		}
		
		public UserChallengeBuilder rewardItem(ItemId itemId) {
			this.rewardItem = itemId;
			return this;
		}
		
		public UserChallengeBuilder isActive(boolean isActive) {
			this.isActive = isActive;
			return this;
		}
		
		public UserChallengeAggregate build() {
			return new UserChallengeAggregate(this);
		}
	}
}
