package de.valentin.master.core.avatar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public class AvatarProjection {
	
	@Id
	private ObjectId userId;
	
	private String face;
	private String hairColor;
	private String skinColor;
	private String facialExpression;
	private int points;
	private Map<String, ObjectId> avatarItems;
	private List<ObjectId> userItems;
	@Version
	private Long version;
	
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
	public AvatarProjection() {
		avatarItems = new HashMap<>();
		userItems = new ArrayList<>();
	}
	
	public ObjectId getUserId() {
		return userId;
	}
	public void setUserId(ObjectId userId) {
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
	public String getFacialExpression() {
		return facialExpression;
	}
	public void setFacialExpression(String facialExpression) {
		this.facialExpression = facialExpression;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public Map<String, ObjectId> getAvatarItems() {
		return avatarItems;
	}
	public void setAvatarItems(HashMap<String, ObjectId> avatarItems) {
		this.avatarItems = avatarItems;
	}
	public List<ObjectId> getUserItems() {
		return userItems;
	}
	public void setUserItems(List<ObjectId> userItems) {
		this.userItems = userItems;
	}
}
