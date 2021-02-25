package de.valentin.master.core.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.valentin.master.core.appservices.TaskApplicationService;
import de.valentin.master.core.appservices.dto.TaskData;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class TaskWebServices {
	
	TaskApplicationService applicationService;
	
	@Autowired
	public TaskWebServices(TaskApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@GetMapping("/tasks/of/project/{projectId}")
	public ResponseEntity<List<TaskData>> showAllTasksInProject(@PathVariable String projectId) {
		List<TaskData> output = applicationService.getTasksBy(new ProjectId(projectId));
		return ResponseEntity.ok(output);
	}
	
	@GetMapping("/tasks/of/user/{userId}") 
	public ResponseEntity<List<TaskData>> showAllTasksOfUser(@PathVariable String userId) {
		List<TaskData> output = applicationService.getTasksBy(new UserId(userId));
		return ResponseEntity.ok(output);
	}
	
	@PutMapping("/task/create/from/{userId}/in/{projectId}")
	public ResponseEntity<TaskData> createTask(@PathVariable String userId, @PathVariable String projectId, @RequestBody TaskData taskData) {
		TaskData task = applicationService.createTask(userId, projectId, taskData);
		return ResponseEntity.ok(task);
	}
	
	@PostMapping("/task/update/{taskId}")
	public ResponseEntity<TaskData> changeTask(@RequestBody TaskData taskData, @PathVariable String taskId) {
		TaskData output = applicationService.changeTask(taskId, taskData);
		return ResponseEntity.ok(output);
	}
	
	@PostMapping("/task/like/{taskId}/liker/{userId}")
	public ResponseEntity<?> likeTask(@PathVariable String taskId, @PathVariable String userId) {
		applicationService.likeTask(taskId, userId);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/task/unlike/{taskId}/disliker/{userId}")
	public ResponseEntity<?> unlikeTask(@PathVariable String taskId, @PathVariable String userId) {
		applicationService.unlikeTask(taskId, userId);
		return ResponseEntity.status(HttpStatus.OK)
				.body("user " + userId + " unliked todo " + taskId + ".");
	}
	
	@DeleteMapping("/task/delete/{taskId}")
	public ResponseEntity<String> deleteTask(@PathVariable String taskId) {
		applicationService.deleteTask(taskId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/task/get/{todoId}")
	public ResponseEntity<TaskData> getTaskBy(@PathVariable String taskId) {
		TaskData output = applicationService.getTaskBy(taskId);
		return ResponseEntity.ok(output);
	}
}
