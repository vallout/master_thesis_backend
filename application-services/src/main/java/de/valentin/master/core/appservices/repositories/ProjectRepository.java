package de.valentin.master.core.appservices.repositories;

import java.util.List;

import de.valentin.master.core.project.ProjectAggregate;
import de.valentin.master.core.shared_model.ProjectId;

public interface ProjectRepository {
	
	void save(ProjectAggregate projectAggregate);
	
	ProjectAggregate retrieve(ProjectId projectId);
	List<ProjectAggregate> retrieve();
	
	boolean delete(ProjectId projectId);
}
