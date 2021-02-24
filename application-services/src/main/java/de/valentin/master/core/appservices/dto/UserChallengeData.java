package de.valentin.master.core.appservices.dto;

import java.util.Date;

public class UserChallengeData {
	
	private String userChallengeId;
	private String event;
	private String queryKeyword;
	private Date beginning;
	private Date end;
	private int condition;
	private int rewardPoints;
	private String rewardItem;
	
	private boolean active;

	public String getUserChallengeId() {
		return userChallengeId;
	}

	public void setUserChallengeId(String userChallengeId) {
		this.userChallengeId = userChallengeId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getQueryKeyword() {
		return queryKeyword;
	}

	public void setQueryKeyWord(String queryKeyword) {
		this.queryKeyword = queryKeyword;
	}

	public Date getBeginning() {
		return beginning;
	}

	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public String getRewardItem() {
		return rewardItem;
	}

	public void setRewardItem(String rewardItem) {
		this.rewardItem = rewardItem;
	}
}
