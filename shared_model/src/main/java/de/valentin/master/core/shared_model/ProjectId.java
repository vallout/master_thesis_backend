package de.valentin.master.core.shared_model;

public class ProjectId {
	
	private String projectId;

	public ProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectId() {
		return projectId;
	}

	@Override
	public String toString() {
		return projectId;
	}
	
}
