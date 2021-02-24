package de.valentin.master.core.item;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemDAO extends MongoRepository<ItemProjection, ObjectId>{
}
