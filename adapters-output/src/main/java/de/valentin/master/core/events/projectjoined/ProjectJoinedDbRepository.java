package de.valentin.master.core.events.projectjoined;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.valentin.master.core.appservices.events.dto.ProjectJoinedData;
import de.valentin.master.core.appservices.events.repositories.ProjectJoinedRepository;
import de.valentin.master.core.appservices.internalevents.ProjectJoined;
import de.valentin.master.core.shared_model.UserId;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProjectJoinedDbRepository implements ProjectJoinedRepository{
	
	ProjectJoinedDAO dao;
	
	@Autowired
	public ProjectJoinedDbRepository(ProjectJoinedDAO dao) {
		this.dao = dao;
	}

	@Override
	public void save(ProjectJoined projectJoined) {
		ProjectJoinedProjection projection = new ProjectJoinedProjection();
		
		projection.setProjectId(new ObjectId(projectJoined.getProjectId().toString()));
		projection.setUserId(new ObjectId(projectJoined.getUserId().toString()));
		projection.setTimestamp(projectJoined.getTimestamp());
		
		dao.save(projection);
	}

	@Override
	public List<ProjectJoinedData> retrieve(UserId userId) {
		List<ProjectJoinedProjection> projections = dao.findByUserId(new ObjectId(userId.toString()));
		return retrieveGeneral(projections);
	}

	@Override
	public List<ProjectJoinedData> retrieve() {
		List<ProjectJoinedProjection> projections = dao.findAll();
		return retrieveGeneral(projections);
	}

	@Override
	public List<ProjectJoinedData> retrieve(UserId userId, long begin, long end) {
		List<ProjectJoinedProjection> projections = dao.findByUserIdAndTimestampBetween(new ObjectId(userId.toString()), begin, end);
		return retrieveGeneral(projections);
	}

	@Override
	public List<ProjectJoinedData> retrieve(long begin, long end) {
		List<ProjectJoinedProjection> projections = dao.findByTimestampBetween(begin, end);
		return retrieveGeneral(projections);
	}
	
	private List<ProjectJoinedData> retrieveGeneral(List<ProjectJoinedProjection> projections) {
		List<ProjectJoinedData> projectsJoined = new ArrayList<>();
		
		for (ProjectJoinedProjection projection : projections) {
			ProjectJoinedData projectJoined = new ProjectJoinedData(
											projection.getProjectId().toString(), projection.getUserId().toString(), 
											projection.getTimestamp());
			projectsJoined.add(projectJoined);
		}
		
		return projectsJoined;
	}
}
