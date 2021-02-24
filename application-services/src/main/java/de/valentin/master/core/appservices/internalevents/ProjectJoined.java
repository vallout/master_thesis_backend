package de.valentin.master.core.appservices.internalevents;

import org.springframework.context.ApplicationEvent;

import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

@SuppressWarnings("serial")
public class ProjectJoined extends ApplicationEvent {
	
	private ProjectId projectId;
	private UserId userId;
	
	public ProjectJoined(Object source, UserId userId, ProjectId projectId) {
		super(source);
		
		this.projectId = projectId;
		this.userId = userId;
	}

	public ProjectId getProjectId() {
		return projectId;
	}

	public UserId getUserId() {
		return userId;
	}
}
