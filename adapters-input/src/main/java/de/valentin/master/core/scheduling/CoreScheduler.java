package de.valentin.master.core.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.valentin.master.core.appservices.GamingApplicationService;
import de.valentin.master.core.challengeexecutioner.GroupChallengeExecutioner;

@Component
public class CoreScheduler {
	
	GamingApplicationService applicationService;
	GroupChallengeExecutioner groupChallengeService;
	
	@Autowired
	public CoreScheduler(GamingApplicationService applicationService,
							GroupChallengeExecutioner groupChallengeService) {
		this.applicationService = applicationService;
		this.groupChallengeService = groupChallengeService;
	}
	
	@Scheduled(fixedRate = 60 * 60 * 8 * 1000)
	public void deleteOldTemporaryPoints() {
		applicationService.deleteTemporaryPoints();
	}
	
	@Scheduled(fixedRate = 60 * 60 * 8 * 1000)
	public void handleGroupEvents() {
		groupChallengeService.groupEventsHandler();
	}
}
