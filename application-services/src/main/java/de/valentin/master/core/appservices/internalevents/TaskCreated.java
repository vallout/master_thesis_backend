package de.valentin.master.core.appservices.internalevents;

import org.springframework.context.ApplicationEvent;

import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

@SuppressWarnings("serial")
public class TaskCreated extends ApplicationEvent {

	private UserId userId;
	private ProjectId projectId;
	
	public TaskCreated(Object source, UserId userId, ProjectId projectId) {
		super(source);
		this.userId = userId;
		this.projectId = projectId;
	}
	public UserId getUserId() {
		return userId;
	}
	public ProjectId getProjectId() {
		return projectId;
	}
}
