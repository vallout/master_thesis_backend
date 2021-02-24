package de.valentin.master.core.project;

public class ProjectInformation {
	
	private String name;
	private String description;
	
	public ProjectInformation(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	public void setInformation(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
