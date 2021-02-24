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
public class TodoWebServices {
	
	TaskApplicationService applicationService;
	
	@Autowired
	public TodoWebServices(TaskApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@GetMapping("/tasks/of/project/{projectId}")
	public ResponseEntity<List<TaskData>> showAllTodosInProject(@PathVariable String projectId) {
		List<TaskData> output = applicationService.getTodosBy(new ProjectId(projectId));
		return ResponseEntity.ok(output);
	}
	
	@GetMapping("/tasks/of/user/{userId}") 
	public ResponseEntity<List<TaskData>> showAllTodosOfUser(@PathVariable String userId) {
		List<TaskData> output = applicationService.getTodosBy(new UserId(userId));
		return ResponseEntity.ok(output);
	}
	
	@PutMapping("/task/create/from/{userId}/in/{projectId}")
	public ResponseEntity<TaskData> createTodo(@PathVariable String userId, @PathVariable String projectId, @RequestBody TaskData todoData) {
		TaskData task = applicationService.createTodo(userId, projectId, todoData);
		return ResponseEntity.ok(task);
	}
	
	@PostMapping("/task/update/{todoId}")
	public ResponseEntity<TaskData> updateTodo(@RequestBody TaskData todoData, @PathVariable String todoId) {
		TaskData output = applicationService.changeTodo(todoId, todoData);
		return ResponseEntity.ok(output);
	}
	
	@PostMapping("/task/like/{todoId}/liker/{userId}")
	public ResponseEntity<?> likeTodo(@PathVariable String todoId, @PathVariable String userId) {
		applicationService.likeTodo(todoId, userId);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/task/unlike/{todoId}/disliker/{userId}")
	public ResponseEntity<?> unlikeTodo(@PathVariable String todoId, @PathVariable String userId) {
		applicationService.unlikeTodo(todoId, userId);
		return ResponseEntity.status(HttpStatus.OK)
				.body("user " + userId + " unliked todo " + todoId + ".");
	}
	
	@DeleteMapping("/task/delete/{todoId}")
	public ResponseEntity<String> deleteTodo(@PathVariable String todoId) {
		applicationService.deleteTodo(todoId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/task/get/{todoId}")
	public ResponseEntity<TaskData> getTodoBy(@PathVariable String todoId) {
		TaskData output = applicationService.getTodoBy(todoId);
		return ResponseEntity.ok(output);
	}
}
