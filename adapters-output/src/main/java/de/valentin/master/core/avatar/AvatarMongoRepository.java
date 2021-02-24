package de.valentin.master.core.avatar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.repositories.AvatarRepository;
import de.valentin.master.core.avatar.AvatarAggregate.AvatarBuilder;
import de.valentin.master.core.shared_model.UserId;
import de.valentin.master.core.shared_model.ItemId;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AvatarMongoRepository implements AvatarRepository {
	
	AvatarDAO dao;
	
	@Autowired
	public AvatarMongoRepository(AvatarDAO dao) {
		this.dao = dao;
	}

	@Override
	public void save(AvatarAggregate aggregate) {
		
		AvatarProjection projection = new AvatarProjection();
		
		HashMap<String, ObjectId> hashmapConverted = new HashMap<String, ObjectId>();
		for (HashMap.Entry<String, ItemId> entry : aggregate.getRootEntity().getAvatarItems().entrySet()) {
		    hashmapConverted.put(entry.getKey(), new ObjectId(entry.getValue().toString()));
		}
		
		List<ObjectId> listConverted = null;
		
		try {
			listConverted = aggregate.getRootEntity().getUserItems().stream()
					.map(userId -> new ObjectId(userId.toString()))
					.collect(Collectors.toList());
		} catch(NullPointerException e) {
			// TODO: Don't know why here a nullpointer exception is thrown. Is it also when userItems exist?
		}
		
		projection.setAvatarItems(hashmapConverted);
		projection.setUserItems(listConverted);
		projection.setUserId(new ObjectId(aggregate.getRootEntity().getUserId().toString()));
		projection.setFace(aggregate.getRootEntity().getFace().toString());
		projection.setFacialExpression(aggregate.getRootEntity().getFacialExpression().toString());
		projection.setHairColor(aggregate.getRootEntity().getHairColor().toString());
		projection.setPoints(aggregate.getRootEntity().getPoints());
		projection.setSkinColor(aggregate.getRootEntity().getSkinColor().toString());
		projection.setVersion(aggregate.getRootEntity().getVersion());
		
		dao.save(projection);
	}

	@Override
	public AvatarAggregate retrieve(UserId userId) {

		AvatarProjection projection = dao.findByUserId(new ObjectId(userId.toString()));

		return retrieveSingle(projection);
	}

	@Override
	public List<AvatarAggregate> retrieve() {
		List<AvatarProjection> projections = dao.findAll();
		List<AvatarAggregate> aggregates = new ArrayList<>();
		
		for (AvatarProjection projection : projections) {
			AvatarAggregate aggregate = retrieveSingle(projection);
			aggregates.add(aggregate);
		}
		return aggregates;
	}
	
	private AvatarAggregate retrieveSingle(AvatarProjection projection) {
		AvatarBuilder builder = new AvatarAggregate.AvatarBuilder(new UserId(projection.getUserId().toString()))
										.expression(projection.getFacialExpression())
										.face(projection.getFace())
										.hairColor(projection.getHairColor())
										.skinColor(projection.getSkinColor())
										.points(projection.getPoints())
										.setVersion(projection.getVersion());
		for (HashMap.Entry<String, ObjectId> entry : projection.getAvatarItems().entrySet()) {
		    builder.setEquippedItem(entry.getKey(), new ItemId(entry.getValue().toString()));
		}
		for (ObjectId entry : projection.getUserItems()) {
			builder.setUserItem(new ItemId(entry.toString()));
		}
		return builder.build();
	}
}
