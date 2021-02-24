package de.valentin.master.core.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.valentin.master.core.appservices.UserApplicationService;
import de.valentin.master.core.appservices.dto.UserData;
import de.valentin.master.core.shared_model.ProjectId;
import de.valentin.master.core.shared_model.UserId;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class UserWebServices {
	
	UserApplicationService applicationService;
	
	public UserWebServices(UserApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@PutMapping("/user/register/{ipAddress}")
	public ResponseEntity<UserData> registerUser(@RequestBody UserData userProfile, @PathVariable String ipAddress) {
		UserData profile = null;
		try {
			profile = applicationService.registerUser(userProfile, ipAddress);
		} catch(IllegalArgumentException e) {
			ResponseEntity.badRequest().build();
		} catch(NullPointerException e) {
			ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(profile);
	}
	
	@GetMapping("/user/get/{userId}")
	public ResponseEntity<UserData> getUserBy(@PathVariable String userId) {
		UserData profile = applicationService.getUserById(new UserId(userId));
		return ResponseEntity.ok(profile);
	}
	
	@GetMapping("/user/login/{email}")
	public ResponseEntity<UserData> loginUser(@PathVariable String email) {
		UserData output = applicationService.loginUser(email);
		return ResponseEntity.ok(output);
	}
	
	@PostMapping("/user/profile/update/{userId}")
	public ResponseEntity<UserData> UpdateProfile(@RequestBody UserData userProfile, @PathVariable String userId) {
		UserId userIdObject = new UserId(userId);
		System.out.println(userProfile.getPrimaryMail());
		System.out.println(userProfile.getPhone());
		UserData output = applicationService.changeProfile(userIdObject, userProfile);
		return ResponseEntity.ok(output);
	}
	
	@GetMapping("/user/all")
	public ResponseEntity<List<UserData>> getAllUsers() {
		List<UserData> users = applicationService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/user/projectMembers/{projectId}")
	public ResponseEntity<List<UserData>> getAllProjectMembers(@PathVariable String projectId) {
		List<UserData> users = applicationService.getAllUsersOf(new ProjectId(projectId));
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/user/profile/progress/{userId}")
	public ResponseEntity<Integer>getProfileProgress(@PathVariable String userId) {
		int progress = applicationService.getProfileProgress(new UserId(userId));
		return ResponseEntity.ok(progress);
	}
}
