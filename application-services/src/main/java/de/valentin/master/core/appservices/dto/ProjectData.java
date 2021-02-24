package de.valentin.master.core.appservices.dto;

import java.util.ArrayList;
import java.util.List;

public class ProjectData {
	private String projectId;
	private String name;
	private String description;
	private List<String> users;
	
	public ProjectData() {
		this.users = new ArrayList<>();
	}
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public List<String> getUsers() {
		return users;
	}
	public void setUsers(List<String> users) {
		this.users = users;
	}
}
