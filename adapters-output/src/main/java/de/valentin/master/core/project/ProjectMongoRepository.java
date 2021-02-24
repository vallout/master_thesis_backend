package de.valentin.master.core.project;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.repositories.ProjectRepository;
import de.valentin.master.core.project.ProjectAggregate.ProjectBuilder;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProjectMongoRepository implements ProjectRepository{
	
	private ProjectDAO dao;
	
	@Autowired
	public ProjectMongoRepository(ProjectDAO dao) {
		this.dao = dao;
	}

	public void save(ProjectAggregate projectAggregate) {
		ProjectProjection projection = new ProjectProjection();
		ObjectId projectIdAsObjectId = new ObjectId(projectAggregate.getProjectRootEntity().getProjectId().toString());
		projection.setProjectId(projectIdAsObjectId);
		projection.setName(projectAggregate.getProjectRootEntity().getName());
		projection.setDescription(projectAggregate.getProjectRootEntity().getDescription());
		for (UserId userId : projectAggregate.getProjectRootEntity().getMembers()) {
			projection.addMember(new ObjectId(userId.toString()));
		}
		dao.save(projection);
	}
	
	public ProjectAggregate retrieve(ProjectId projectId) {
		ObjectId projectIdAsObjectId = new ObjectId(projectId.toString());
		ProjectProjection projection = dao.findById(projectIdAsObjectId).get();
		if(projection != null) {
			ProjectBuilder builder = new ProjectAggregate.ProjectBuilder(projectId)
					.name(projection.getName())
					.description(projection.getDescription());
			for (ObjectId userId : projection.getMembers()) {
				builder.addMember(userId.toString());
			}
			return builder.build();
		} else {
			return null;
		}
	}

	@Override
	public List<ProjectAggregate> retrieve() {
		List<ProjectProjection> projections = dao.findAll();
		List<ProjectAggregate> aggregates = new ArrayList<ProjectAggregate>();
		if (projections != null) {
			for (ProjectProjection projection : projections) {
				ProjectId projectId = new ProjectId(projection.getProjectId().toString());
				ProjectBuilder builder = new ProjectAggregate.ProjectBuilder(projectId)
						.name(projection.getName())
						.description(projection.getDescription());
				for (ObjectId userId : projection.getMembers()) {
					builder.addMember(userId.toString());
				}
				aggregates.add(builder.build());
			}
			return aggregates;
		} else {
			return null;
		}
	}

	@Override
	public boolean delete(ProjectId projectId) {
		try {
			dao.deleteById(new ObjectId(projectId.toString()));
		} catch(Throwable t) {
			return false;
		}
		return true;
	}	
}
