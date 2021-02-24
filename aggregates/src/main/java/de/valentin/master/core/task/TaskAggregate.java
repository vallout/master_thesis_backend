package de.valentin.master.core.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

public class TaskAggregate {
	private TaskRootEntity rootEntity;
	
	private TaskAggregate(TodoBuilder builder) {
		this.rootEntity = new TaskRootEntity(builder.todoId, builder.projectId, builder.creator, 
							builder.state, builder.name, builder.description, builder.deadline);
		this.rootEntity.setLikers(builder.likers);
	}
	
	public TaskRootEntity getRootEntity() {
		return rootEntity;
	}
	
	public static class TodoBuilder {
		private final String todoId;
		private final ProjectId projectId;
		private final UserId creator;
		private State state;
		private String name;
		private String description;
		private Date deadline;
		private List<UserId> likers;
		
		public TodoBuilder(String todoId, String projectId, String creator) {
			this.todoId = todoId;
			this.projectId = new ProjectId(projectId);
			this.creator = new UserId(creator);
			this.likers = new ArrayList<UserId>();
		}
		
		public TodoBuilder state(String state) {
			this.state = State.valueOf(state.toUpperCase());
			return this;
		}
		
		public TodoBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public TodoBuilder description(String description) {
			this.description = description;
			return this;
		}
		
		public TodoBuilder withLikes(List<String> likers) {
			if (likers == null) return this;
			for (String liker : likers) {
				this.likers.add(new UserId(liker));
			}
			return this;
		}

		public TodoBuilder date(Date deadline) {
			this.deadline = deadline;
			return this;
		}
		
		public TaskAggregate build() {
			return new TaskAggregate(this);
		}
	}
}
