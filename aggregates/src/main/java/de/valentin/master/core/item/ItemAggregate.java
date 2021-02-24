package de.valentin.master.core.item;

import de.valentin.master.core.shared_model.ItemId;

public class ItemAggregate {
	private ItemRootEntity rootEntity;
	
	private ItemAggregate(ItemBuilder builder) {
		this.rootEntity = new ItemRootEntity(builder.itemId, builder.itemType, builder.name,
							builder.description, builder.modelId, builder.price);
	}
	
	public ItemRootEntity getRootEntity() {
		return this.rootEntity;
	}
	
	public static class ItemBuilder {
		private ItemId itemId;
		private ItemType itemType;
		private String name;
		private String description;
		private String modelId;
		private int price;
		
		public ItemBuilder(String itemId) {
			this.itemId = new ItemId(itemId);
		}
		
		public ItemBuilder itemType(String itemType) {
			this.itemType = ItemType.valueOf(itemType);
			return this;
		}
		
		public ItemBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public ItemBuilder description(String description) {
			this.description = description;
			return this;
		}
		
		public ItemBuilder modelId(String modelId) {
			this.modelId = modelId;
			return this;
		}
		
		public ItemBuilder setPrice(int price) {
			this.price = price;
			return this;
		}
		public ItemAggregate build() {
			return new ItemAggregate(this);
		}
	}
}
