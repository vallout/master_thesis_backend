package de.valentin.master.core.groupchallenge;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class GroupChallengeProjection {
	@Id
	private ObjectId groupChallengeId;
	
	private boolean isRunning;
	private String type;
	private String eventName;
	private String description;
	private Date beginning;
	private Date end;
	private int condition;
	private int rewardPoints;
	private String rewardItem;
	private boolean isFinished;
	
	public void setGroupChallengeId(ObjectId groupChallengeId) {
		this.groupChallengeId = groupChallengeId;
	}
	public ObjectId getGroupChallengeId() {
		return this.groupChallengeId;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public boolean isFinished() {
		return isFinished;
	}
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
}
