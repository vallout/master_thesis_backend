package de.valentin.master.core.appservices.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvatarData {
	
	private String userId;
	private String face;
	private String hairColor;
	private String skinColor;
	private String expression;
	
	private Map<String, String> equippedItems;
	private List<String> userItems;
	
	private int points;
	
	public AvatarData() {
		this.equippedItems = new HashMap<>();
		this.userItems = new ArrayList<>();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getHairColor() {
		return hairColor;
	}

	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}

	public String getSkinColor() {
		return skinColor;
	}

	public void setSkinColor(String skinColor) {
		this.skinColor = skinColor;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Map<String, String> getEquippedItems() {
		return equippedItems;
	}

	public void setEquippedItem(String kind, String ItemId) {
		this.equippedItems.put(kind, ItemId);
	}

	public List<String> getUserItems() {
		return userItems;
	}

	public void setUserItem(String userItem) {
		this.userItems.add(userItem);
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
