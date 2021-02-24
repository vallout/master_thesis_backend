package de.valentin.master.core.appservices.dto;

import java.util.Date;

public class GroupChallengeData {
	
	private String groupChallengeId;
	
	private boolean isRunning;
	private String eventType;
	private String eventName;
	private String description;
	private Date beginning;
	private Date end;
	private int condition;
	private int rewardPoints;
	private String rewardItem;
	
	public String getGroupChallengeId() {
		return groupChallengeId;
	}
	public void setGroupChallengeId(String groupChallengeId) {
		this.groupChallengeId = groupChallengeId;
	}
	public boolean isRunning() {
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
