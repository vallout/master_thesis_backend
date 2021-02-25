package de.valentin.master.core.item;

import de.valentin.master.core.shared_model.ItemId;

public class ItemRootEntity {
	
	private ItemId itemId;
	private ItemType itemType;
	private String name;
	private String description;
	private String modelId;
	private int price;
	
	public ItemRootEntity(ItemId itemId, ItemType itemType, String name, String description, String modelId, int price) {
		super();
		this.itemId = itemId;
		this.itemType = itemType;
		this.name = name;
		this.description = description;
		this.modelId = modelId;
		this.price = price;
	}

	public ItemId getItemId() {
		return itemId;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getModelId() {
		return modelId;
	}

	public int getPrice() {
		return price;
	}
	
	public boolean isBuyable() {
		if (price > 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
