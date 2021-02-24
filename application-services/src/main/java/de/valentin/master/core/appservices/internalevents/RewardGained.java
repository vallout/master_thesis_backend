package de.valentin.master.core.appservices.internalevents;

import org.springframework.context.ApplicationEvent;

import de.valentin.master.core.appservices.dto.AvatarData;

@SuppressWarnings("serial")
public class RewardGained extends ApplicationEvent {
	
	private String userId;
	private String challengeId;
	private String typeOfChallenge;
	private String itemName;
	private int addedPoints;

	public RewardGained(Object source, String userId, String challengeId,
							String typeOfChallenge, String itemName, int addedPoints) {
		super(source);
		this.userId = userId;
		this.challengeId = challengeId;
		this.typeOfChallenge = typeOfChallenge;
		this.itemName = itemName;
		this.addedPoints = addedPoints;
	}
	
	public String getUserId() {
		return userId;
	}
	public String getTypeOfChallenge() {
		return typeOfChallenge;
	}

	public String getChallengeId() {
		return challengeId;
	}

	public String getItemName() {
		return itemName;
	}

	public int getAddedPoints() {
		return addedPoints;
	}
	
	
}
