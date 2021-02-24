package de.valentin.master.core.item;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class ItemProjection {
	@Id
	private ObjectId itemId;
	
	private String itemType;
	private String name;
	private String description;
	private String modelId;
	private int price;
	
	public ObjectId getItemId() {
		return itemId;
	}
	public void setItemId(ObjectId itemId) {
		this.itemId = itemId;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
