package de.valentin.master.core.events.projectcreated;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.events.dto.ProjectCreatedData;
import de.valentin.master.core.appservices.events.repositories.ProjectCreatedRepository;
import de.valentin.master.core.appservices.internalevents.ProjectCreated;
import de.valentin.master.core.shared_model.UserId;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProjectCreatedMongoRepository implements ProjectCreatedRepository{
	
	ProjectCreatedDAO dao;
	
	@Autowired
	public ProjectCreatedMongoRepository(ProjectCreatedDAO dao) {
		this.dao = dao;
	}

	@Override
	public void save(ProjectCreated projectCreated) {
		ProjectCreatedProjection projection = new ProjectCreatedProjection();
		
		projection.setProjectId(new ObjectId(projectCreated.getProjectId().toString()));
		projection.setUserId(new ObjectId(projectCreated.getUserId().toString()));
		projection.setTimestamp(projectCreated.getTimestamp());
		
		dao.save(projection);
	}

	@Override
	public List<ProjectCreatedData> retrieve(UserId userId) {
		List<ProjectCreatedProjection> projections = dao.findByUserId(new ObjectId(userId.toString()));
		return retrieveGeneral(projections);
	}

	@Override
	public List<ProjectCreatedData> retrieve() {
		List<ProjectCreatedProjection> projections = dao.findAll();
		return retrieveGeneral(projections);
	}

	@Override
	public List<ProjectCreatedData> retrieve(UserId userId, long begin, long end) {
		List<ProjectCreatedProjection> projections = dao.findByUserIdAndTimestampBetween(new ObjectId(userId.toString()), begin, end);
		return retrieveGeneral(projections);
	}

	@Override
	public List<ProjectCreatedData> retrieve(long begin, long end) {
		List<ProjectCreatedProjection> projections = dao.findByTimestampBetween(begin, end);
		return retrieveGeneral(projections);
	}
	
	private List<ProjectCreatedData> retrieveGeneral(List<ProjectCreatedProjection> projections) {
		List<ProjectCreatedData> projectsCreated = new ArrayList<ProjectCreatedData>();
		
		for (ProjectCreatedProjection projection : projections) {
			ProjectCreatedData projectCreated = new ProjectCreatedData(
											projection.getProjectId().toString(), projection.getUserId().toString(), 
											projection.getTimestamp());
			projectsCreated.add(projectCreated);
		}
		
		return projectsCreated;
	}
}
