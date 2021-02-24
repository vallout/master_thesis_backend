package de.valentin.master.core.user;

import java.util.ArrayList;
import java.util.List;

import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

public class UserAggregate {
	
	private UserRootEntity rootEntity;
	
	private UserAggregate(UserProfileBuilder builder) {
		this.rootEntity = new UserRootEntity(
					builder.userId, builder.projects, builder.title, builder.firstname, 
					builder.lastname, builder.primaryMail, builder.phone, builder.pictureId
				);
	}
	
	public UserRootEntity getProfile() {
		return rootEntity;
	}
	
	public static class UserProfileBuilder {
		
		private final UserId userId;
		private List<ProjectId> projects;
		private String title;
		private String firstname;
		private String lastname;
		private String primaryMail;
		private String phone;
		private String pictureId;
		
		public UserProfileBuilder(String userId) {
			this.userId = new UserId(userId);
			this.projects = new ArrayList<>();
		}
		
		public UserProfileBuilder title(String title) {
			this.title = title;
			return this;
		}
		
		public UserProfileBuilder firstname(String firstname) {
			this.firstname = firstname;
			return this;
		}
		
		public UserProfileBuilder lastname(String lastname) {
			this.lastname = lastname;
			return this;
		}
		
		public UserProfileBuilder primaryMail(String primaryMail) {
			this.primaryMail = primaryMail;
			return this;
		}
		
		public UserProfileBuilder phone(String phone) {
			this.phone = phone;
			return this;
		}
		
		public UserProfileBuilder pictureId(String pictureId) {
			this.pictureId = pictureId;
			return this;
		}
		
		public UserProfileBuilder addProject(String projectId) {
			this.projects.add(new ProjectId(projectId));
			return this;
		}
		
		public UserAggregate build() {
			return new UserAggregate(this);
		}
	}
}
