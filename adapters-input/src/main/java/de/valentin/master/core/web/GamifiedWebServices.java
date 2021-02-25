package de.valentin.master.core.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.valentin.master.core.appservices.GamingApplicationService;
import de.valentin.master.core.appservices.dto.AvatarData;
import de.valentin.master.core.appservices.dto.GroupChallengeData;
import de.valentin.master.core.appservices.dto.ItemData;
import de.valentin.master.core.shared_model.UserId;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class GamifiedWebServices {
	
	GamingApplicationService applicationService;
	
	@Autowired
	public GamifiedWebServices(GamingApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@GetMapping("/avatar/get/all")
	public ResponseEntity<List<AvatarData>> getAllAvatars() {
		List<AvatarData> avatars = applicationService.getAllAvatars();
		return ResponseEntity.ok(avatars);
	}
	
	@GetMapping("/avatar/get/{userId}")
	public ResponseEntity<AvatarData> getAvatar(@PathVariable String userId ) {
		AvatarData avatar = applicationService.getAvatar(userId);
		return ResponseEntity.ok(avatar);
	}
	
	@PostMapping("/avatar/change/{userId}")
	public ResponseEntity<AvatarData> changeAvatar(@RequestBody AvatarData avatar, @PathVariable String userId) {
		avatar.setUserId(userId);
		AvatarData output = applicationService.changeAvatar(avatar);
		return ResponseEntity.ok(output);
	}
	
	@PostMapping("/avatar/buy/{itemId}/as/{userId}")
	public ResponseEntity<Boolean> buyItem(@PathVariable String itemId, @PathVariable String userId) {
		boolean output = applicationService.buyItem(userId, itemId);
		return ResponseEntity.ok(output);
	}
	
	@PostMapping("/temporarypoints/add/{event}/to/{ipAddress}") 
	public ResponseEntity<?> setTemporaryPoints(@PathVariable String event, @PathVariable String ipAddress){
		applicationService.addTemporaryPoints(event, ipAddress);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/temporarypoints/get/{ipAddress}") 
	public ResponseEntity<Integer> getTemporaryPoints(@PathVariable String ipAddress){
		Integer output = 0;
		try {
			output = applicationService.getTemporaryPoints(ipAddress);
		} catch (NullPointerException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(output);
	}
	
	@GetMapping("/avatar/userItems/{userId}")
	public ResponseEntity<List<ItemData>> getUserItems(@PathVariable String userId) {
		List<ItemData> output = applicationService.getUserItems(userId);
		return ResponseEntity.ok(output);
	}
	
	@PostMapping("/avatar/equipItem/{itemId}/of/{userId}")
	public ResponseEntity<Boolean> equipAvatarItem(@PathVariable String itemId, @PathVariable String userId) {
		boolean output = applicationService.equipItem(userId, itemId);
		return ResponseEntity.ok(output);
	}
	
	@PostMapping("/avatar/unequipItem/{kind}/of/{userId}")
	public ResponseEntity<Boolean> unequipAvatarItem(@PathVariable String kind, @PathVariable String userId) {
		boolean output = applicationService.unequipItem(userId, kind.toUpperCase());
		return ResponseEntity.ok(output);
	}
	
	@GetMapping("/avatar/get/equipped/{userId}")
	public ResponseEntity<Map<String, ItemData>> getEquippedItems(@PathVariable String userId) {
		Map<String, ItemData> output = applicationService.getEquippedItems(userId);
		return ResponseEntity.ok(output);
	}
	
	@GetMapping("/items/buyable")
	public ResponseEntity<List<ItemData>> getBuyableItems() {
		List<ItemData> items = applicationService.getBuyableItems();
		return ResponseEntity.ok(items);
	}
	
	@GetMapping("/points/get/{userId}")
	public ResponseEntity<Integer> getUserPoints(@PathVariable String userId) {
		int output = applicationService.getUserPoints(new UserId(userId));
		return ResponseEntity.ok(output);
	}
	
	@GetMapping("/statistics/get/{projectId}")
	public ResponseEntity<Map<String, Object>> showStatisticsForProject(@PathVariable String projectId) {
		Map<String, Object> statistics = applicationService.getProjectStatistics(projectId);
		return ResponseEntity.ok(statistics);
	}
	
	@GetMapping("/groupChallenge/get/active")
	public ResponseEntity<List<GroupChallengeData>> getActiveGroupChallenges() {
		List<GroupChallengeData> output =  applicationService.getActiveGroupChallenges();
		return ResponseEntity.ok(output);
	}
}
