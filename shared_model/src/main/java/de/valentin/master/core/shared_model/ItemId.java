package de.valentin.master.core.shared_model;

public class ItemId {
	
	private String itemId;
	
	public ItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Override
	public String toString() {
		return itemId;
	}
	
	@Override
	public boolean equals(Object o) {
        if (o == this) { 
            return true; 
        } 
        if (!(o instanceof ItemId)) { 
            return false; 
        }
        ItemId other = (ItemId) o;
		return this.itemId.equals(other.itemId);
	}
	
	public String getItemId() {
		return itemId;
	}
}
 