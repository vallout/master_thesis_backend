package de.valentin.master.core.project;

import java.util.List;

import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

public class ProjectRootEntity {
	final ProjectId projectId;
	private ProjectInformation information;
	private List<UserId> members;


	public ProjectRootEntity(ProjectId projectId, ProjectInformation information, 
								List<UserId> members) {
		this.projectId = projectId;
		this.information = information;
		this.members = members;
	}

	public String getName() {
		return information.getName();
	}
	
	public String getDescription() {
		return information.getDescription();
	}

	public ProjectId getProjectId() {
		return projectId;
	}
	
	public List<UserId> getMembers() {
		return members;
	}
	
	public void setInformation(String name, String description) {
		this.information.setInformation(name, description);
	}
	
	public void addMember(UserId userId) {
		this.members.add(userId);
	}
	
	public void removeMember(UserId userId) {
		this.members.remove(userId);
	}
}
