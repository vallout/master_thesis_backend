package de.valentin.master.core.appservices.dto;

import java.util.ArrayList;
import java.util.List;

public class UserData {
	private String userId;
	private String title;
	private String firstname;
	private String lastname;
	private String primaryMail;
	private String phone;
	
	private String pictureId;
	
	private List<String> projects;
	
	public UserData() {
		this.projects = new ArrayList<>();
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getProjects() {
		return projects;
	}
	public void addProject(String projectId) {
		this.projects.add(projectId);
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
}
