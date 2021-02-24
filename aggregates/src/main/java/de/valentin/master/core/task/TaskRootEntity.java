package de.valentin.master.core.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

public class TaskRootEntity {

	private final String taskId;
	private final ProjectId projectId;
	private final UserId creator;
	private State state;
	private String name;
	private String description;
	private Date deadline;
	private List<UserId> likers;
	
	public TaskRootEntity(String taskId, ProjectId projectId, UserId creator, State state, String name, String description, Date deadline) {
		
		this.taskId = taskId;
		this.projectId = projectId;
		this.creator = creator;
		this.state = state;
		this.name = name;
		this.description = description;
		this.deadline = deadline;
		this.likers = new ArrayList<UserId>();
	}
	
	public String getId() {
		return taskId;
	}
	public ProjectId getProjectId() {
		return projectId;
	}
	public UserId getCreator() {
		return creator;
	}
	public State getState() {
		return state;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Date getDeadline() {
		return deadline;
	}
	public List<UserId> getLikers() {
		return likers;
	}
	public void setLikers(List<UserId> likers) {
		this.likers = likers;
	}
	
	public void addLiker(UserId userId) {
		if (!this.likers.contains(userId)) {
			this.likers.add(userId);
		}
	}
	
	public void removeLiker(UserId userId) {
		this.likers.remove(userId);
	}
}
