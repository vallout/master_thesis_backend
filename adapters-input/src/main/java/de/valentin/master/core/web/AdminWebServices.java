package de.valentin.master.core.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.valentin.master.core.appservices.EventApplicationService;
import de.valentin.master.core.appservices.GamingApplicationService;
import de.valentin.master.core.appservices.dto.GroupChallengeData;
import de.valentin.master.core.appservices.dto.ItemData;
import de.valentin.master.core.appservices.dto.UserChallengeData;
import de.valentin.master.core.shared_model.ItemId;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class AdminWebServices {
	
	EventApplicationService eventApplicationService;
	GamingApplicationService gamingApplicationService;
	
	@Autowired
	public AdminWebServices(EventApplicationService eventApplicationService, GamingApplicationService gamingApplicationService) {
		this.eventApplicationService = eventApplicationService;
		this.gamingApplicationService = gamingApplicationService;
	}
	
	@PutMapping("/userChallenge/set")
	public ResponseEntity<?> setUserChallenge(@RequestBody UserChallengeData userEvent) {
		gamingApplicationService.createUserEvent(userEvent);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/userChallenge/get/all")
	public ResponseEntity<List<UserChallengeData>> getUserChallenges() {
		List<UserChallengeData> userEvents = gamingApplicationService.getAllUserEvents();
		return ResponseEntity.ok(userEvents);
	}
	
	@PostMapping("/userChallenge/deactivate/{ruleId}")
	public ResponseEntity<Boolean> deactivateUserChallenge(@PathVariable String ruleId) {
		boolean output =  gamingApplicationService.deactivateUserChallenge(ruleId);
		return ResponseEntity.ok(output);
	}
	
	@PostMapping("/userChallenge/activate/{ruleId}")
	public ResponseEntity<Boolean> activateUserChallenge(@PathVariable String ruleId) {
		boolean output =  gamingApplicationService.activateUserChallenge(ruleId);
		return ResponseEntity.ok(output);
	}
	
	@GetMapping("/userChallenge/usersParticipated/{challengeId}")
	public ResponseEntity<List<String>> getUsersThatParticipated(@PathVariable String challengeId) {
		if (challengeId == "") {
			return ResponseEntity.badRequest().build();
		}
		List<String> participators = gamingApplicationService.getUsersThatParticipated(challengeId);
		return ResponseEntity.ok(participators);
	}
	
	@PutMapping("/groupChallenge/set")
	public ResponseEntity<String> setGroupChallenges(@RequestBody GroupChallengeData groupEvent) {
		String output = gamingApplicationService.createNewGroupEvent(groupEvent);
		return ResponseEntity.ok(output);
	}
	
	@GetMapping("/groupChallenge/get/all")
	public ResponseEntity<List<GroupChallengeData>> getGroupChallenges() {
		List<GroupChallengeData> output =  gamingApplicationService.getAllGroupChallenges();
		return ResponseEntity.ok(output);
	}
	
	@PostMapping("/groupChallenge/activate/{challengeId}")
	public ResponseEntity<?> activateGroupChallenge(@PathVariable String challengeId) {
		gamingApplicationService.activateGroupChallenge(challengeId);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/groupChallenge/deactivate/{challengeId}")
	public ResponseEntity<?> deactivateGroupChallenge(@PathVariable String challengeId) {
		gamingApplicationService.deactivateGroupChallenge(challengeId);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/item/add")
	public ResponseEntity<String> createNewItem(@RequestBody ItemData dto) {
		String itemId = gamingApplicationService.createNewItem(dto);
		return ResponseEntity.ok(itemId);
	}
	
	@DeleteMapping("/item/delete/{itemId}")
	public ResponseEntity<?> deleteItem(@PathVariable String itemId) {
		gamingApplicationService.deleteItem(new ItemId(itemId));
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/items/all")
	public ResponseEntity<List<ItemData>> getAllItems() {
		List<ItemData> items = gamingApplicationService.getAllItems();
		return ResponseEntity.ok(items);
	}
	
	@GetMapping("/items/get/by/{itemId}")
	public ResponseEntity<ItemData> getItemBy(@PathVariable String itemId) {
		ItemData item = gamingApplicationService.getItemBy(itemId);
		return ResponseEntity.ok(item);
	}
	
	@GetMapping("/statistics/get/all")
	public ResponseEntity<Map<String, Object>> showStatisticsForAll() {
		Map<String, Object> statistics = gamingApplicationService.getStatistics();
		return ResponseEntity.ok(statistics);
	}
	
	@GetMapping("/statistics/get/gamified")
	public ResponseEntity<Map<String, Map<Integer, Integer>>> showGamifiedStatistics() {
		Map<String, Map<Integer, Integer>> statistics = gamingApplicationService.getGamificationStatistics();
		return ResponseEntity.ok(statistics);
	}
	
	@GetMapping("/userChallenge/get/QueryKeywords/userLoggedIn")
	public ResponseEntity<List<String>> getQueryKeywordsForUserLoggedIn() {
		List<String> keywords = new ArrayList<>();
		keywords.add("byUser");
		keywords.add("byUserAndTime");
		return ResponseEntity.ok(keywords);
	}
	
	@GetMapping("/userChallenge/get/QueryKeywords/projectCreated")
	public ResponseEntity<List<String>> getQueryKeywordsForProjectCreated() {
		List<String> keywords = new ArrayList<>();
		keywords.add("byUser");
		keywords.add("byUserAndTime");
		return ResponseEntity.ok(keywords);
	}
	
	@GetMapping("/userChallenge/get/QueryKeywords/projectJoined")
	public ResponseEntity<List<String>> getQueryKeywordsForProjectJoined() {
		List<String> keywords = new ArrayList<>();
		keywords.add("byUser");
		keywords.add("byUserAndTime");
		return ResponseEntity.ok(keywords);
	}
	
	@GetMapping("/userChallenge/get/QueryKeywords/taskCreated")
	public ResponseEntity<List<String>> getQueryKeywordsForTaskCreated() {
		List<String> keywords = new ArrayList<>();
		keywords.add("byUser");
		keywords.add("byUserAndProject");
		keywords.add("byUserAndTime");
		keywords.add("byUserAndProjectAndTime");
		return ResponseEntity.ok(keywords);
	}
	
	@GetMapping("/userChallenge/get/QueryKeywords/taskFinished")
	public ResponseEntity<List<String>> getQueryKeywordsForTaskFinished() {
		List<String> keywords = new ArrayList<>();
		keywords.add("byUser");
		keywords.add("byUserAndProject");
		keywords.add("byUserAndTime");
		keywords.add("byUserAndProjectAndTime");
		return ResponseEntity.ok(keywords);
	}
	
	@GetMapping("/userChallenge/get/QueryKeywords/taskLiked")
	public ResponseEntity<List<String>> getQueryKeywordsForTaskLiked() {
		List<String> keywords = new ArrayList<>();
		keywords.add("byCreator");
		keywords.add("byCreatorAndProject");
		keywords.add("byCreatorAndTime");
		keywords.add("byCreatorAndProjectAndTime");
		keywords.add("byLiker");
		keywords.add("byLikerAndProject");
		keywords.add("byLikerAndTime");
		keywords.add("byLikerAndProjectAndTime");
		return ResponseEntity.ok(keywords);
	}
}
