package de.valentin.master.core.project;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class ProjectProjection {
	@Id
	private ObjectId projectId;
	
	private String name;
	private String description;
	
	private List<ObjectId> members;
	
	public ProjectProjection() {
		this.members = new ArrayList<>();
	}
	
	public ObjectId getProjectId() {
		return projectId;
	}
	public void setProjectId(ObjectId projectId) {
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
	public List<ObjectId> getMembers() {
		return members;
	}
	public void setMembers(List<ObjectId> members) {
		this.members = members;
	}
	
	public void addMember(ObjectId member) {
		this.members.add(member);
	}
}
