package de.valentin.master.core.appservices.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskData {
	private String taskId;
	private String projectId;
	private String userId;
	private String state;
	private String name;
	private String description;
	private Date deadline;
	private List<String> likers;
	
	public TaskData() {
		this.likers = new ArrayList<>();
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
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
	public List<String> getLikers() {
		return likers;
	}
	public void addLiker(String liker) {
		this.likers.add(liker);
	}
}
