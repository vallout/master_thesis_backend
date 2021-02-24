package de.valentin.master.core.user;

import java.util.List;

import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

public class UserRootEntity {
	
	final UserId userId;
	private List<ProjectId> projects;
	private String title;
	private String firstname;
	private String lastname;
	private String primaryMail;
	private String phone;
	
	private String pictureId;
	
	public UserRootEntity(UserId userId, List<ProjectId> projects, String title, String firstname, String lastname, String primaryMail,
			String phone, String pictureId) {
		
		this.userId = userId;
		this.projects = projects;
		this.title = title;
		this.firstname = firstname;
		this.lastname = lastname;
		this.primaryMail = primaryMail;
		this.phone = phone;
		
		this.pictureId = pictureId;
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

	public void setProjects(List<ProjectId> projects) {
		this.projects = projects;
	}

	public UserId getUserId() {
		return userId;
	}
	
	public List<ProjectId> getProjects() {
		return projects;
	}
	
	public void addProject(ProjectId projectId) {
		this.projects.add(projectId);
	}
	
	public void removeProject(ProjectId projectId) {
		this.projects.remove(projectId);
	}
	
	public int calculateProfileCompletion() {
		int maximumNumberOfFields = 6;
		int actualNumberOfFields = 0;
		
		if (this.title != null && !this.title.equals("")) actualNumberOfFields++;
		if (this.firstname != null && !this.firstname.equals("")) actualNumberOfFields++;
		if (this.lastname != null && !this.lastname.equals("")) actualNumberOfFields++;
		if (this.phone != null && !this.phone.equals("")) actualNumberOfFields++;
		if (this.primaryMail != null && !this.primaryMail.equals("")) actualNumberOfFields++;
		if (this.pictureId != null && !this.pictureId.equals("")) actualNumberOfFields++;
		
		int resultInPercent = (100 * actualNumberOfFields) / maximumNumberOfFields;
		
		return resultInPercent;
	}
}
