package de.valentin.master.core.appservices.repositories;

import java.util.List;

import de.valentin.master.core.item.ItemAggregate;

public interface ItemRepository {
	void save(ItemAggregate aggregate);
	
	ItemAggregate retrieve(String itemId);
	List<ItemAggregate> retrieve();
	List<ItemAggregate> retrieve(boolean buyable);
	
	void delete(String itemId);
}
