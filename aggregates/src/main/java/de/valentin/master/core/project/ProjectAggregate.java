package de.valentin.master.core.project;

import java.util.ArrayList;
import java.util.List;

import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

public class ProjectAggregate {
	
	private ProjectRootEntity rootEntity;
	
	private ProjectAggregate(ProjectBuilder builder) {
		ProjectInformation information = new ProjectInformation(
					builder.name, builder.description
				);
		this.rootEntity = new ProjectRootEntity(builder.projectId, information, builder.members);
	}
	
	public ProjectRootEntity getProjectRootEntity() {
		return rootEntity;
	}
	
	public static class ProjectBuilder {
		
		private final ProjectId projectId;
		private String name;
		private String description;
		private List<UserId> members;
		
		public ProjectBuilder(ProjectId projectId) {
			this.projectId = projectId;
			this.members = new ArrayList<>();
		}
		
		public ProjectBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public ProjectBuilder description(String description) {
			this.description = description;
			return this;
		}
		
		public ProjectBuilder addMember(String userId) {
			members.add(new UserId(userId));
			return this;
		}
		
		public ProjectAggregate build() {
			return new ProjectAggregate(this);
		}
	}
}
