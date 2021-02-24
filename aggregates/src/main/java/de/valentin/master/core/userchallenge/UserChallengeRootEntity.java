package de.valentin.master.core.userchallenge;

import java.util.Date;

import de.valentin.master.core.shared_model.ItemId;

public class UserChallengeRootEntity {
	
	private String userChallengeId;
	private String event;
	private String queryKeyword;
	private Date beginning;
	private Date end;
	private int condition;
	private int rewardPoints;
	private ItemId rewardItem;
	private boolean activated;
	
	public UserChallengeRootEntity(String userChallengeId, String event, String queryKeyword, Date beginning, 
			Date end, int condition, int rewardPoints, ItemId rewardItem, boolean activated) {		
		this.userChallengeId = userChallengeId;
		this.event = event;
		this.queryKeyword = queryKeyword;
		this.beginning = beginning;
		this.end = end;
		this.condition = condition;
		this.rewardPoints = rewardPoints;
		this.rewardItem = rewardItem;
		this.activated = activated;
	}
	
	public void deactivate() {
		this.activated = false;
	}
	
	public void activate() {
		this.activated = true;
	}
	
	public String getUserChallengeId() {
		return userChallengeId;
	}

	public String getEvent() {
		return event;
	}

	public String getQueryKeyword() {
		return queryKeyword;
	}

	public Date getBeginning() {
		return beginning;
	}

	public Date getEnd() {
		return end;
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
	
	public boolean isActivated() {
		return activated;
	}	
}
