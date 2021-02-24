package de.valentin.master.core.appservices.internalevents;

import org.springframework.context.ApplicationEvent;

import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

@SuppressWarnings("serial")
public class TaskLiked extends ApplicationEvent{

	private UserId creator;
	private UserId liker;
	private ProjectId projectId;
	
	public TaskLiked(Object source, UserId creator, UserId liker, ProjectId projectId) {
		super(source);
		this.creator = creator;
		this.liker = liker;
		this.projectId = projectId;
	}
	
	public UserId getCreator() {
		return creator;
	}

	public UserId getLiker() {
		return liker;
	}

	public ProjectId getProjectId() {
		return projectId;
	}
}
