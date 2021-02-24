package de.valentin.master.core.groupchallenge;

import java.util.Date;

import de.valentin.master.core.shared_model.ItemId;

public class GroupChallengeRootEntity {
	
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
	
	public GroupChallengeRootEntity(String groupChallengeId, boolean isRunning, GroupChallengeType type, String eventName, 
								String description, Date beginning, Date end, 
								int condition, int rewardPoints, ItemId rewardItem, boolean isFinished) {
		this.groupChallengeId = groupChallengeId;
		this.isRunning = isRunning;
		this.type = type;
		this.eventName = eventName;
		this.description = description;
		this.end = end;
		this.condition = condition;
		this.rewardPoints = rewardPoints;
		this.rewardItem = rewardItem;
		this.isFinished = isFinished;
	}
	
	public String getGroupChallengeId() {
		return groupChallengeId;
	}

	public String getEventName() {
		return eventName;
	}

	public Date getEnd() {
		return end;
	}
	
	public Date getBeginning() {
		return beginning;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public int getCondition() {
		return condition;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public ItemId getRewardItem() {
		return rewardItem;
	}

	public GroupChallengeType getType() {
		return type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isFinished() {
		return isFinished;
	}
	
	public void activate() {
		if (new Date().before(end)) {
			this.isRunning = true;
			this.beginning = new Date();
		}
	}
	
	public void deactivate() {
		this.isRunning = false;
	}
}
