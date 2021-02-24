package de.valentin.master.core.user;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class UserProjection {
	
	@Id
	private ObjectId userId;
	
	private String title;
	private String firstname;
	private String lastname;
	private String primaryMail;
	private String phone;
	
	private String pictureId;
	
	private List<String> projects;
	
	public UserProjection() {
		this.projects = new ArrayList<>();
	}
	
	public ObjectId getUserId() {
		return userId;
	}
	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPrimaryMail() {
		return primaryMail;
	}
	public void setPrimaryMail(String primaryMail) {
		this.primaryMail = primaryMail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public List<String> getProjects() {
		return projects;
	}

	public void addProject(String projectId) {
		this.projects.add(projectId);
	}
	
}
