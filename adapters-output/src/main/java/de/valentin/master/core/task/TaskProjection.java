package de.valentin.master.core.task;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class TaskProjection {
	
	@Id
	private ObjectId taskId;
	private ObjectId projectId;
	private ObjectId userId;
	private String state;
	private String name;
	private String description;
	private Date deadline;
	private List<ObjectId> likers;
	
	
	public ObjectId getTaskId() {
		return taskId;
	}
	public void setTaskId(ObjectId taskId) {
		this.taskId = taskId;
	}
	public ObjectId getProjectId() {
		return projectId;
	}
	public void setProjectId(ObjectId projectId) {
		this.projectId = projectId;
	}
	public ObjectId getUserId() {
		return userId;
	}
	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public List<ObjectId> getLikers() {
		return likers;
	}
	public void setLikers(List<ObjectId> likers) {
		this.likers = likers;
	}
}
