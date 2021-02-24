package de.valentin.master.core.avatar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.shared_model.ItemId;

public class AvatarAggregate {
	private AvatarRootEntity rootEntity;
	
	private AvatarAggregate(AvatarBuilder builder) {
		this.rootEntity = new AvatarRootEntity(builder.userId, builder.face, 
							builder.hairColor, builder.skinColor, 
							builder.expression, builder.points, builder.userItems, builder.avatarItems,
							builder.version);
	}
	
	public AvatarRootEntity getRootEntity() {
		return this.rootEntity;
	}
	
	public static class AvatarBuilder {
		private final UserId userId;
		private Face face;
		private FacialExpression expression;
		private HairColor hairColor;
		private SkinColor skinColor;
		private int points;
		private Map<String, ItemId> avatarItems;
		private List<ItemId> userItems;
		private Long version;
		
		public AvatarBuilder(UserId userId) {
			this.userId = userId;
			this.face = Face.NEUTRAL;
			this.expression = FacialExpression.NEUTRAL;
			this.hairColor = HairColor.BRUNETTE;
			this.skinColor = new SkinColor("120,120,120");
			this.points = 0;
			this.avatarItems = new HashMap<String, ItemId>();
			this.userItems = new ArrayList<ItemId>();
		}
		
		public AvatarBuilder face(String face) {
			this.face = Face.valueOf(face);
			return this;
		}
		
		public AvatarBuilder setVersion(Long version) {
			this.version = version;
			return this;
		}
		
		public AvatarBuilder expression(String expression) {
			this.expression = FacialExpression.valueOf(expression);
			return this;
		}
		
		public AvatarBuilder hairColor(String hairColor) {
			this.hairColor = HairColor.valueOf(hairColor);
			return this;
		}
		
		public AvatarBuilder skinColor(String skinColor) {
			this.skinColor = new SkinColor(skinColor);
			return this;
		}
		
		public AvatarBuilder points(int points) {
			this.points = points;
			return this;
		}
		
		public AvatarBuilder setEquippedItem(String kind, ItemId itemId) {
			this.avatarItems.put(kind, itemId);
			return this;
		}
		
		public AvatarBuilder setUserItem(ItemId userItem) {
			this.userItems.add(userItem);
			return this;
		}
		
		public AvatarAggregate build() {
			return new AvatarAggregate(this);
		}
	}
}
