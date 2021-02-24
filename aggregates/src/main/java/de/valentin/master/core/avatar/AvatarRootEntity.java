package de.valentin.master.core.avatar;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.shared_model.ItemId;

public class AvatarRootEntity {
	
	private UserId userId;
	
	private Face face;
	private HairColor hairColor;
	private SkinColor skinColor;
	private FacialExpression facialExpression;
	
	private Map<String, ItemId> avatarItems;
	private List<ItemId> userItems;
	
	private int points;
	private Long version;
	
	public AvatarRootEntity(UserId userId, Face face, HairColor hairColor, SkinColor skinColor,
			FacialExpression facialExpression, int points, List<ItemId> userItems, 
			Map<String, ItemId> avatarItems2, Long version) {
		this.userId = userId;
		this.face = face;
		this.hairColor = hairColor;
		this.skinColor = skinColor;
		this.facialExpression = facialExpression;
		this.points = points;
		this.userItems = userItems;
		this.avatarItems = avatarItems2;
		this.version = version;
	}
	
	
	public Long getVersion() {
		return version;
	}


	public List<ItemId> getUserItems() {
		return userItems;
	}
	public void addUserItem(ItemId userItemId) {
		this.userItems.add(userItemId);
	}
	public Map<String, ItemId> getAvatarItems() {
		return avatarItems;
	}
	public boolean putAvatarItem(String kind, ItemId avatarItem) {
		if (this.userItems.contains(avatarItem)) {
			this.avatarItems.put(kind, avatarItem);
			return true;
		} else {
			return false;
		}
	}
	public boolean removeAvatarItem(String kind) {
		ItemId itemid = this.avatarItems.remove(kind);
		if (itemid == null) return false;
		return true;
	}
	public void setUserId(UserId userId) {
		this.userId = userId;
	}
	public void changeLook(String face, String hairColor, String skinColor, String facialExpression) {
		this.face = Face.valueOf(face);
		this.hairColor = HairColor.valueOf(hairColor);
		this.skinColor = new SkinColor(skinColor);
		this.facialExpression = FacialExpression.valueOf(facialExpression);
	}
	public Face getFace() {
		return face;
	}
	public HairColor getHairColor() {
		return hairColor;
	}
	public SkinColor getSkinColor() {
		return skinColor;
	}
	public FacialExpression getFacialExpression() {
		return facialExpression;
	}
	public UserId getUserId() {
		return userId;
	}
	public int getPoints() {
		return points;
	}
	
	public void addPoints(int points) {
		this.points = this.points + points;
	}
	
	public void reducePoints(int points) {
		this.points = this.points - points;
	}
}
