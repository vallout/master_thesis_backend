package de.valentin.master.core.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.repositories.ItemRepository;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ItemDbRepository implements ItemRepository{
	
	private ItemDAO dao;
	
	@Autowired
	public ItemDbRepository(ItemDAO dao) {
		this.dao = dao;
	}
	@Override
	public void save(ItemAggregate aggregate) {
		ItemProjection projection = new ItemProjection();
		
		projection.setItemId(new ObjectId(aggregate.getRootEntity().getItemId().toString()));
		projection.setItemType(aggregate.getRootEntity().getItemType().toString());
		projection.setName(aggregate.getRootEntity().getName());
		projection.setDescription(aggregate.getRootEntity().getDescription());
		projection.setModelId(aggregate.getRootEntity().getModelId());
		projection.setPrice(aggregate.getRootEntity().getPrice());
		
		dao.save(projection);
	}

	@Override
	public ItemAggregate retrieve(String itemId) {
		Optional<ItemProjection> optional_projection = dao.findById(new ObjectId(itemId.toString()));
		if (optional_projection.isPresent()) {
			ItemProjection projection = optional_projection.get();
			ItemAggregate aggregate = singleRetrieve(projection);
			return aggregate;
		} else {
			return null;
		}
	}

	@Override
	public List<ItemAggregate> retrieve() {
		List<ItemProjection> projections = dao.findAll();
		List<ItemAggregate> aggregates = new ArrayList<>();
		for (ItemProjection projection : projections) {
			ItemAggregate aggregate = singleRetrieve(projection);
			aggregates.add(aggregate);
		}
		return aggregates;
	}
	@Override
	public void delete(String itemId) {
		dao.deleteById(new ObjectId(itemId));		
	}
	@Override
	public List<ItemAggregate> retrieve(boolean buyable) {
		List<ItemProjection> projections = dao.findAll();
		List<ItemAggregate> aggregates = new ArrayList<>();
		for (ItemProjection projection : projections) {
			if (projection.getPrice() != 0) {
				ItemAggregate aggregate = singleRetrieve(projection);
				aggregates.add(aggregate);
			}
		}
		return aggregates;
	}
	
	private ItemAggregate singleRetrieve(ItemProjection projection) {
		ItemAggregate aggregate = new ItemAggregate.ItemBuilder(projection.getItemId().toString())
				.name(projection.getName())
				.description(projection.getDescription())
				.itemType(projection.getItemType())
				.modelId(projection.getModelId())
				.setPrice(projection.getPrice())
				.build();
		return aggregate;
	}
}
