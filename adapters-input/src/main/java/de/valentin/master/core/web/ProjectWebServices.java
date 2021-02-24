package de.valentin.master.core.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.valentin.master.core.appservices.ProjectApplicationService;
import de.valentin.master.core.appservices.dto.ProjectData;
import de.valentin.master.core.appservices.dto.UserData;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class ProjectWebServices {
	
	ProjectApplicationService applicationService;
	
	@Autowired
	public ProjectWebServices(ProjectApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@GetMapping("/project/all")
	public ResponseEntity<List<ProjectData>> getProjects() {
		List<ProjectData> output = applicationService.getAllProjects();
		return ResponseEntity.ok(output);
	}

	@PutMapping("/project/create/{userId}")
	public ResponseEntity<ProjectData> createProject(@RequestBody ProjectData projectData, @PathVariable String userId) {

		ProjectData dto = applicationService.createProject(projectData, new UserId(userId));
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/project/change/{projectId}")
	public ResponseEntity<ProjectData> changeProject(@RequestBody ProjectData projectData, @PathVariable String projectId) {
		
		ProjectData dto = applicationService.changeProject(projectData, new ProjectId(projectId));
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/project/join/project/{projectId}/as/{userId}")
	public ResponseEntity<ProjectData> joinProject(@PathVariable String projectId, @PathVariable String userId) {

		ProjectData dto = applicationService.joinProject(new ProjectId(projectId), 
															new UserId(userId));
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/project/leave/project/{projectId}/as/{userId}")
	public ResponseEntity<String> leaveProject(@PathVariable String projectId, @PathVariable String userId) {
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("method is not implemented yet.");
	}
	
	@GetMapping("/project/members/{projectId}")
	public ResponseEntity<List<UserData>> showProjectMembers(@PathVariable String projectId) {
		List<UserData> output = applicationService.getAllMembers(new ProjectId(projectId));
		return ResponseEntity.ok(output);
	}
}